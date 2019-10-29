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

package com.ivianuu.essentials.ui.compose.common

import android.app.Activity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.compose.ambient
import androidx.compose.onActive
import androidx.compose.unaryPlus
import com.github.ajalt.timberkt.d
import com.ivianuu.essentials.ui.base.EsActivity
import com.ivianuu.essentials.ui.compose.core.ActivityAmbient

fun handleBack(
    activity: Activity = +ambient(ActivityAmbient),
    callback: () -> Unit
) = onActive {
    d { "handle back on active" }
    val backPressedDispatcher =
        (activity as OnBackPressedDispatcherOwner).onBackPressedDispatcher

    (activity as? EsActivity)?.handleBack = false

    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            d { "on back" }
            callback()
        }
    }

    backPressedDispatcher.addCallback(onBackPressedCallback)

    onDispose {
        d { "back on dispose" }
        onBackPressedCallback.remove()
        (activity as? EsActivity)?.handleBack = true
    }
}