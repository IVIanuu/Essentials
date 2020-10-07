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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.ivianuu.essentials.ui.common.InsettingScrollableColumn
import com.ivianuu.essentials.ui.core.Text
import com.ivianuu.essentials.ui.material.ListItem
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.Navigator
import com.ivianuu.essentials.util.Toaster
import com.ivianuu.injekt.Assisted
import com.ivianuu.injekt.FunBinding
import kotlinx.coroutines.launch

@FunBinding
@Composable
fun SecureSettingsPage(
    navigator: Navigator,
    popNavigatorOnceSecureSettingsGranted: popNavigatorOnceSecureSettingsGranted,
    secureSettingsPcInstructionsPage: SecureSettingsPcInstructionsPage,
    secureSettings: SecureSettings,
    toaster: Toaster,
    showHideNavBarHint: @Assisted Boolean = false,
) {
    popNavigatorOnceSecureSettingsGranted(true)

    Scaffold(
        topBar = { TopAppBar(title = { Text(R.string.es_title_secure_settings) }) }
    ) {
        InsettingScrollableColumn {
            SecureSettingsHeader(
                stringResource(
                    if (showHideNavBarHint) {
                        R.string.es_pref_secure_settings_header_hide_nav_bar_summary
                    } else {
                        R.string.es_pref_secure_settings_header_summary
                    }
                )
            )

            ListItem(
                title = { Text(R.string.es_pref_use_pc) },
                subtitle = { Text(R.string.es_pref_use_pc_summary) },
                onClick = {
                    navigator.push {
                        secureSettingsPcInstructionsPage()
                    }
                }
            )

            val scope = rememberCoroutineScope()
            ListItem(
                title = { Text(R.string.es_pref_use_root) },
                subtitle = { Text(R.string.es_pref_use_root_summary) },
                onClick = {
                    scope.launch {
                        if (secureSettings.grantPermissionViaRoot()) {
                            toaster.toast(R.string.es_secure_settings_permission_granted)
                        } else {
                            toaster.toast(R.string.es_secure_settings_no_root)
                        }
                    }
                }
            )
        }
    }
}
