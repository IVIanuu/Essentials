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

package com.ivianuu.essentials.systemoverlay

import android.view.inputmethod.*
import com.ivianuu.essentials.*
import com.ivianuu.essentials.accessibility.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.android.*
import com.ivianuu.injekt.coroutines.*
import com.ivianuu.injekt.scope.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

typealias KeyboardVisible = Boolean

@Provide fun keyboardVisible(
  accessibilityEvents: Flow<AccessibilityEvent>,
  keyboardHeightProvider: KeyboardHeightProvider,
  scope: InjektCoroutineScope<AppScope>
): @Scoped<AppScope> Flow<KeyboardVisible> = accessibilityEvents
  .filter {
    it.isFullScreen &&
        it.className == "android.inputmethodservice.SoftInputWindow"
  }
  .map { Unit }
  .onStart { emit(Unit) }
  .transformLatest {
    emit(true)
    while ((keyboardHeightProvider() ?: 0) > 0) {
      delay(100)
    }
    emit(false)
    awaitCancellation()
  }
  .distinctUntilChanged()
  .stateIn(scope, SharingStarted.WhileSubscribed(1000), false)

@Provide val keyboardVisibilityAccessibilityConfig = flow {
  emit(
    AccessibilityConfig(
      eventTypes = AndroidAccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
    )
  )
}

private typealias KeyboardHeightProvider = () -> Int?

@Provide fun keyboardHeightProvider(
  inputMethodManager: @SystemService InputMethodManager
): KeyboardHeightProvider = {
  catch {
    val method = inputMethodManager.javaClass.getMethod("getInputMethodWindowVisibleHeight")
    method.invoke(inputMethodManager) as Int
  }.getOrNull()
}
