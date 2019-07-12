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

import android.view.accessibility.AccessibilityEvent
import com.github.ajalt.timberkt.d
import com.ivianuu.essentials.gestures.accessibility.AccessibilityComponent
import com.ivianuu.essentials.util.BehaviorSubject
import com.ivianuu.injekt.Inject
import com.ivianuu.injekt.android.ApplicationScope
import io.reactivex.Observable

@Inject
@ApplicationScope
class SecureScreenDetector : AccessibilityComponent() {

    private val _isOnSecureScreen = BehaviorSubject(false)
    val isOnSecureScreen: Observable<Boolean> get() = _isOnSecureScreen

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        // were only interested in window state changes
        if (event.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            return
        }

        // ignore keyboards
        if (event.className == "android.inputmethodservice.SoftInputWindow") {
            return
        }

        val packageName = event.packageName?.toString() ?: return
        val className = event.className?.toString() ?: return

        //val managePermissionsActivity = "com.android.packageinstaller.permission.ui.ManagePermissionsActivity"
        //val grantPermissionsActivity ="com.android.packageinstaller.permission.ui.GrantPermissionsActivity"

        var isOnSecureScreen = packageName.contains("packageinstaller")

        if (!isOnSecureScreen) {
            isOnSecureScreen = packageName == "com.android.settings"
                    && className == "android.app.AlertDialog"
        }

        // distinct
        if (_isOnSecureScreen.value != isOnSecureScreen) {
            d { "on secure screen changed: $isOnSecureScreen" }
            _isOnSecureScreen.onNext(isOnSecureScreen)
        }
    }

}