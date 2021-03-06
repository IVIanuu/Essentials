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

import com.ivianuu.essentials.accessibility.*
import com.ivianuu.essentials.logging.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.coroutines.*
import com.ivianuu.injekt.scope.*
import kotlinx.coroutines.flow.*

typealias IsOnSecureScreen = Boolean

@Provide fun isOnSecureScreen(
  accessibilityEvents: Flow<AccessibilityEvent>,
  logger: Logger,
  scope: InjektCoroutineScope<AppScope>,
): @Scoped<AppScope> Flow<IsOnSecureScreen> = accessibilityEvents
  .filter { it.type == AndroidAccessibilityEvent.TYPE_WINDOW_STATE_CHANGED }
  .map { it.packageName to it.className }
  .filter { it.second != "android.inputmethodservice.SoftInputWindow" }
  .map { (packageName, className) ->
    var isOnSecureScreen = "packageinstaller" in packageName.orEmpty()
    if (!isOnSecureScreen) {
      isOnSecureScreen = packageName == "com.android.settings" &&
          className == "android.app.MaterialDialog"
    }

    isOnSecureScreen
  }
  .distinctUntilChanged()
  .onEach { d { "on secure screen changed: $it" } }
  .stateIn(scope, SharingStarted.WhileSubscribed(1000), false)

@Provide val isOnSecureScreenAccessibilityConfig = flow {
  emit(
    AccessibilityConfig(
      eventTypes = AndroidAccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
    )
  )
}
