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

package com.ivianuu.essentials.securesettings

import androidx.compose.effectOf
import com.ivianuu.essentials.ui.compose.coroutines.launchOnActive
import com.ivianuu.essentials.ui.compose.injekt.inject
import com.ivianuu.essentials.ui.navigation.Navigator
import com.ivianuu.essentials.util.Toaster
import kotlinx.coroutines.delay

internal fun popNavigatorOnceSecureSettingsGranted() = effectOf<Unit> {
    val navigator = +inject<Navigator>()
    val secureSettingsHelper = +inject<SecureSettingsHelper>()
    val toaster = +inject<Toaster>()

    // we check the permission periodically to automatically pop this screen
    // once we got the permission
    +launchOnActive {
        while (true) {
            if (secureSettingsHelper.canWriteSecureSettings()) {
                toaster.toast(R.string.es_secure_settings_permission_granted)
                navigator.pop(true)
                break
            }
            delay(500)
        }
    }
}