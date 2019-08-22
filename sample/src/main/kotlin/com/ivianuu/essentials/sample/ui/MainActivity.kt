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

package com.ivianuu.essentials.sample.ui

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.lifecycle.lifecycleScope
import com.github.ajalt.timberkt.d
import com.ivianuu.compose.ComponentComposition
import com.ivianuu.compose.View
import com.ivianuu.compose.common.Navigator
import com.ivianuu.compose.init
import com.ivianuu.compose.internal.loggingEnabled
import com.ivianuu.essentials.gestures.unlock.ScreenUnlocker
import com.ivianuu.essentials.messaging.BroadcastFactory
import com.ivianuu.essentials.ui.base.EsActivity
import com.ivianuu.injekt.get
import com.ivianuu.kprefs.KPrefs
import com.ivianuu.kprefs.boolean
import com.ivianuu.kprefs.coroutines.asFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : EsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loggingEnabled = false

        get<KPrefs>().boolean("tile_state").asFlow()
            .onEach { d { "tile state changed $it" } }
            .launchIn(lifecycleScope)

        get<BroadcastFactory>().create(Intent.ACTION_SCREEN_OFF)
            .onEach {
                lifecycleScope.launch {
                    delay(2000)
                    d { "unlock screen ${get<ScreenUnlocker>().unlockScreen()}" }
                }
            }
            .launchIn(lifecycleScope)
    }

    override fun ComponentComposition.compose() {
        View<FrameLayout> {
            init {
                layoutParams = layoutParams.apply {
                    width = MATCH_PARENT
                    height = MATCH_PARENT
                }
            }

            // todo lol
            with(composition) {
                Navigator { HomeRoute() }
            }
        }
    }

}