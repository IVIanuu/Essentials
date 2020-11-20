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

package com.ivianuu.essentials.securesettings

import com.ivianuu.essentials.coroutines.collectIn
import com.ivianuu.essentials.securesettings.SecureSettingsAction.GrantPermissionsViaRoot
import com.ivianuu.essentials.securesettings.SecureSettingsAction.OpenPcInstructions
import com.ivianuu.essentials.store.Actions
import com.ivianuu.essentials.store.state
import com.ivianuu.essentials.ui.navigation.Navigator
import com.ivianuu.essentials.ui.navigation.push
import com.ivianuu.essentials.ui.store.Initial
import com.ivianuu.essentials.ui.store.UiStateBinding
import com.ivianuu.essentials.util.showToastRes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@UiStateBinding
fun SecureSettingsStore(
    scope: CoroutineScope,
    initial: @Initial SecureSettingsState = SecureSettingsState,
    actions: Actions<SecureSettingsAction>,
    grantSecureSettingsPermissionViaRoot: grantSecureSettingsPermissionViaRoot,
    navigator: Navigator,
    popNavigatorOnceSecureSettingsGranted: popNavigatorOnceSecureSettingsGranted,
    secureSettingsPcInstructionsPage: SecureSettingsPcInstructionsPage,
    showToastRes: showToastRes
) = scope.state(initial) {
    launch { popNavigatorOnceSecureSettingsGranted(true) }
    actions
        .collectIn(this) { action ->
            when (action) {
                OpenPcInstructions -> navigator.push { secureSettingsPcInstructionsPage() }
                GrantPermissionsViaRoot -> if (grantSecureSettingsPermissionViaRoot()) {
                    showToastRes(R.string.es_secure_settings_permission_granted)
                } else {
                    showToastRes(R.string.es_secure_settings_no_root)
                }
            }
        }
}
