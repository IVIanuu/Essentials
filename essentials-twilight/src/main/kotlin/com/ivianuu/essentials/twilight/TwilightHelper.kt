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

package com.ivianuu.essentials.twilight

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.PowerManager
import com.ivianuu.essentials.app.AppService
import com.ivianuu.essentials.messaging.BroadcastFactory
import com.ivianuu.injekt.Single
import com.ivianuu.injekt.android.ApplicationScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import java.util.Calendar

@ApplicationScope
@Single
class TwilightHelper(
    private val app: Application,
    private val broadcastFactory: BroadcastFactory,
    private val resources: Resources,
    private val powerManager: PowerManager,
    prefs: TwilightPrefs
) : AppService {

    private val _isDark = ConflatedBroadcastChannel(false)
    val isDark: Flow<Boolean> get() = _isDark.asFlow()
    val currentIsDark: Boolean get() = _isDark.value

    init {
        prefs.twilightMode.asFlow()
            .flatMapLatest { mode ->
                when (mode) {
                    TwilightMode.System -> system()
                    TwilightMode.Light -> flowOf(false)
                    TwilightMode.Dark -> flowOf(true)
                    TwilightMode.Battery -> battery()
                    TwilightMode.Time -> time()
                }
            }
            .distinctUntilChanged()
            .onEach { _isDark.offer(it) }
            .launchIn(GlobalScope)
    }

    private fun battery() = broadcastFactory.create(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED)
        .map { Unit }
        .onStart { emit(Unit) }
        .map { powerManager.isPowerSaveMode }

    private fun system() = configChanges()
        .onStart { emit(Unit) }
        .map {
            (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration
                .UI_MODE_NIGHT_YES
        }

    private fun time() = broadcastFactory.create(Intent.ACTION_TIME_TICK)
        .map { Unit }
        .onStart { emit(Unit) }
        .map {
            val calendar = Calendar.getInstance()
            val hour = calendar[Calendar.HOUR_OF_DAY]
            hour < 6 || hour >= 22
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