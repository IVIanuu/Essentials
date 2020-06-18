/*
 * Copyright 2019 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.essentials.screenstate

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import android.hardware.SensorManager
import android.view.OrientationEventListener
import android.view.Surface
import android.view.WindowManager
import com.ivianuu.essentials.coroutines.shareIn
import com.ivianuu.essentials.ui.core.DisplayRotation
import com.ivianuu.essentials.util.Logger
import com.ivianuu.injekt.ApplicationScoped
import com.ivianuu.injekt.ForApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlin.time.seconds

@ApplicationScoped
class DisplayRotationProvider(
    private val app: Application,
    private val scope: @ForApplication CoroutineScope,
    private val logger: Logger,
    private val screenStateProvider: ScreenStateProvider,
    private val windowManager: WindowManager
) {

    val displayRotation: Flow<DisplayRotation> = screenStateProvider.screenState
        .flatMapLatest { screenState ->
            if (screenState.isOn) {
                merge(
                    rotationChanges(),
                    configChanges()
                )
                    .onStart { logger.d("sub for rotation") }
                    .onCompletion { logger.d("dispose rotation") }
            } else {
                logger.d("do not observe rotation while screen is off")
                emptyFlow()
            }
        }
        .map { getCurrentDisplayRotation() }
        .distinctUntilChanged()
        .shareIn(scope = scope, cacheSize = 1, timeout = 1.seconds)

    private fun getCurrentDisplayRotation(): DisplayRotation =
        when (windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_0 -> DisplayRotation.PortraitUp
            Surface.ROTATION_90 -> DisplayRotation.LandscapeLeft
            Surface.ROTATION_180 -> DisplayRotation.PortraitDown
            Surface.ROTATION_270 -> DisplayRotation.LandscapeRight
            else -> error("unexpected rotation")
        }

    private fun rotationChanges() = callbackFlow {
        var currentRotation = getCurrentDisplayRotation()

        val listener = object : OrientationEventListener(
            app, SensorManager.SENSOR_DELAY_NORMAL
        ) {
            override fun onOrientationChanged(orientation: Int) {
                val rotation = getCurrentDisplayRotation()
                if (rotation != currentRotation) {
                    offer(rotation)
                    currentRotation = rotation
                }
            }
        }

        offer(currentRotation)
        listener.enable()

        awaitClose { listener.disable() }
    }

    private fun configChanges() = callbackFlow<Unit> {
        val callbacks = object : ComponentCallbacks2 {
            override fun onConfigurationChanged(newConfig: Configuration) {
                offer(Unit)
            }

            override fun onLowMemory() {
            }

            override fun onTrimMemory(level: Int) {
            }
        }
        app.registerComponentCallbacks(callbacks)
        awaitClose { app.unregisterComponentCallbacks(callbacks) }
    }
}
