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

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.onDispose
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ivianuu.essentials.hidenavbar.NavBarConfig
import com.ivianuu.essentials.hidenavbar.NavBarManager
import com.ivianuu.essentials.securesettings.SecureSettingsPage
import com.ivianuu.essentials.securesettings.hasSecureSettingsPermission
import com.ivianuu.essentials.ui.core.rememberState
import com.ivianuu.essentials.ui.layout.center
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.Navigator
import com.ivianuu.injekt.FunBinding
import kotlinx.coroutines.launch

@FunBinding
@Composable
fun NavBarPage(
    hasSecureSettingsPermission: hasSecureSettingsPermission,
    navBarManager: NavBarManager,
    navigator: Navigator,
    secureSettingsPage: SecureSettingsPage,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Nav bar settings") }) }
    ) {
        Column(
            modifier = Modifier.center(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val scope = rememberCoroutineScope()
            fun updateNavBarState(navBarHidden: Boolean) {
                scope.launch {
                    navBarManager.setNavBarConfig(
                        NavBarConfig(navBarHidden)
                    )
                }
            }

            val hideNavBar = rememberState { false }

            onCommit(hideNavBar.value) { updateNavBarState(hideNavBar.value) }

            // reshow nav bar when exiting the screen
            onDispose { updateNavBarState(false) }

            val hasPermission by produceState(false) { value = hasSecureSettingsPermission() }

            Box(
                modifier = Modifier.fillMaxWidth(),
                alignment = Alignment.Center
            ) {
                Text(
                    text = if (hasPermission) {
                        if (hideNavBar.value) {
                            "Nav bar hidden"
                        } else {
                            "Nav bar shown"
                        }
                    } else {
                        "Unknown nav bar state"
                    },
                    style = MaterialTheme.typography.h3
                )
            }

            Button(
                onClick = {
                    if (hasPermission) {
                        hideNavBar.value = !hideNavBar.value
                    } else {
                        navigator.push { secureSettingsPage(true) }
                    }
                }
            ) {
                Text("Toggle nav bar")
            }
        }
    }
}
