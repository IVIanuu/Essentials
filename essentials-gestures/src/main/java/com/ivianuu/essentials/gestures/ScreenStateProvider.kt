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

package com.ivianuu.essentials.gestures

import android.app.KeyguardManager
import android.content.Intent
import android.os.PowerManager
import com.ivianuu.essentials.messaging.BroadcastFactory
import com.ivianuu.injekt.Inject
import io.reactivex.Observable

/**
 * Provides the current screen state
 */
@Inject
class ScreenStateProvider(
    private val broadcastFactory: BroadcastFactory,
    private val keyguardManager: KeyguardManager,
    private val powerManager: PowerManager
) {

    fun observeScreenState(): Observable<ScreenState> {
        return broadcastFactory.create(
            Intent.ACTION_SCREEN_OFF,
            Intent.ACTION_SCREEN_ON,
            Intent.ACTION_USER_PRESENT
        )
            .map { getCurrentScreenState() }
            .startWith(getCurrentScreenState())
    }

    private fun getCurrentScreenState(): ScreenState {
        return if (powerManager.isInteractive) {
            if (keyguardManager.isKeyguardLocked) {
                ScreenState.LOCKED
            } else {
                ScreenState.ON
            }
        } else {
            ScreenState.OFF
        }
    }

}

enum class ScreenState {
    OFF, LOCKED, ON
}