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

import android.content.Intent
import android.content.res.Configuration
import android.os.PowerManager
import com.ivianuu.essentials.broadcast.broadcasts
import com.ivianuu.essentials.screenstate.configChanges
import com.ivianuu.injekt.FunBinding
import com.ivianuu.injekt.android.ApplicationResources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@FunBinding
fun twilightState(
    batteryTwilightState: batteryTwilightState,
    systemTwilightState: systemTwilightState,
    timeTwilightState: timeTwilightState,
    twilightModePref: TwilightModePref,
    useBlackInDarkModePref: UseBlackInDarkModePref,
): Flow<TwilightState> {
    return twilightModePref.data
        .flatMapLatest { mode ->
            when (mode) {
                TwilightMode.System -> systemTwilightState()
                TwilightMode.Light -> flowOf(false)
                TwilightMode.Dark -> flowOf(true)
                TwilightMode.Battery -> batteryTwilightState()
                TwilightMode.Time -> timeTwilightState()
            }
        }
        .combine(useBlackInDarkModePref.data) { isDark, useBlack ->
            TwilightState(isDark, useBlack)
        }
        .distinctUntilChanged()
}

@FunBinding
fun batteryTwilightState(
    broadcasts: broadcasts,
    powerManager: PowerManager,
): Flow<Boolean> {
    return broadcasts(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED)
        .map { Unit }
        .onStart { emit(Unit) }
        .map { powerManager.isPowerSaveMode }
}

@FunBinding
fun systemTwilightState(
    configChanges: configChanges,
    applicationResources: ApplicationResources,
): Flow<Boolean> = configChanges()
    .onStart { emit(Unit) }
    .map {
        (applicationResources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration
            .UI_MODE_NIGHT_YES
    }

@FunBinding
fun timeTwilightState(
    broadcasts: broadcasts,
    clock: Clock,
): Flow<Boolean> = broadcasts(Intent.ACTION_TIME_TICK)
    .map { Unit }
    .onStart { emit(Unit) }
    .map {
        val now = clock.now()
        val date = now.toLocalDateTime(TimeZone.currentSystemDefault())
        val hour = date.hour
        hour < 6 || hour >= 22
    }

data class TwilightState(
    val isDark: Boolean,
    val useBlack: Boolean
)
