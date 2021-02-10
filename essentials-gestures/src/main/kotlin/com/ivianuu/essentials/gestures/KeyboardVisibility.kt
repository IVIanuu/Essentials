/*
 * Copyright 2020 Manuel Wrage
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

import android.view.accessibility.AccessibilityEvent
import android.view.inputmethod.InputMethodManager
import com.ivianuu.essentials.accessibility.AccessibilityConfig
import com.ivianuu.essentials.accessibility.AccessibilityConfigBinding
import com.ivianuu.essentials.accessibility.AccessibilityEvents
import com.ivianuu.essentials.accessibility.AndroidAccessibilityEvent
import com.ivianuu.essentials.accessibility.applyAccessibilityConfig
import com.ivianuu.essentials.coroutines.GlobalScope
import com.ivianuu.essentials.coroutines.flowOf
import com.ivianuu.essentials.result.getOrNull
import com.ivianuu.essentials.result.runKatching
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.GivenFun
import com.ivianuu.injekt.common.Scoped
import com.ivianuu.injekt.component.AppComponent
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext

typealias KeyboardVisible = Boolean

@Scoped<AppComponent>
@Given
fun keyboardVisible(
    @Given accessibilityEvents: AccessibilityEvents,
    @Given getKeyboardHeight: getKeyboardHeight,
    @Given globalScope: GlobalScope,
): Flow<KeyboardVisible> = accessibilityEvents
    .filter {
        it.isFullScreen &&
                it.className == "android.inputmethodservice.SoftInputWindow"
    }
    .map { Unit }
    .onStart { emit(Unit) }
    .transformLatest {
        emit(true)
        while ((getKeyboardHeight() ?: 0) > 0) {
            delay(100)
        }
        emit(false)
        awaitCancellation()
    }
    .distinctUntilChanged()
    .stateIn(globalScope, SharingStarted.WhileSubscribed(1000), false)

@AccessibilityConfigBinding
@Given
fun keyboardVisibilityAccessibilityConfig() = flowOf {
    AccessibilityConfig(
        eventTypes = AndroidAccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
    )
}

@GivenFun
fun getKeyboardHeight(@Given inputMethodManager: InputMethodManager): Int? {
    return runKatching {
        val method = inputMethodManager.javaClass.getMethod("getInputMethodWindowVisibleHeight")
        method.invoke(inputMethodManager) as Int
    }.getOrNull()
}
