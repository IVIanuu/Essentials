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

package com.ivianuu.essentials.sample.ui

import android.accessibilityservice.AccessibilityService
import android.service.notification.NotificationListenerService
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.ivianuu.essentials.accessibility.EsAccessibilityService
import com.ivianuu.essentials.coroutines.ScopeCoroutineScope
import com.ivianuu.essentials.notificationlistener.EsNotificationListenerService
import com.ivianuu.essentials.permission.PermissionRequester
import com.ivianuu.essentials.permission.accessibility.AccessibilityServicePermission
import com.ivianuu.essentials.permission.notificationlistener.NotificationListenerPermission
import com.ivianuu.essentials.permission.runtime.RuntimePermission
import com.ivianuu.essentials.permission.systemoverlay.SystemOverlayPermission
import com.ivianuu.essentials.permission.writesecuresettings.WriteSecureSettingsPermission
import com.ivianuu.essentials.permission.writesettings.WriteSettingsPermission
import com.ivianuu.essentials.ui.layout.center
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.Key
import com.ivianuu.essentials.ui.navigation.KeyUi
import com.ivianuu.essentials.ui.navigation.KeyUiGivenScope
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.common.typeKeyOf
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

@Given
val permissionsHomeItem: HomeItem = HomeItem("Permissions") { PermissionsKey() }

class PermissionsKey : Key<Nothing>

@Given
fun permissionUi(
    @Given permissionRequester: PermissionRequester,
    @Given scope: ScopeCoroutineScope<KeyUiGivenScope>
): KeyUi<PermissionsKey> = {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Permissions") }) }
    ) {
        Button(
            modifier = Modifier.center(),
            onClick = {
                scope.launch {
                    permissionRequester(
                        listOf(
                            typeKeyOf<SampleCameraPermission>(),
                            typeKeyOf<SamplePhonePermission>(),
                            typeKeyOf<SampleAccessibilityPermission>(),
                            typeKeyOf<SampleNotificationListenerPermission>(),
                            typeKeyOf<SampleSystemOverlayPermission>(),
                            typeKeyOf<SampleWriteSecureSettingsPermission>(),
                            typeKeyOf<SampleWriteSettingsPermission>()
                        )
                    )
                }
            }
        ) {
            Text("Request")
        }
    }
}

@Given
object SampleCameraPermission : RuntimePermission {
    override val permissionName: String
        get() = android.Manifest.permission.CAMERA
    override val title: String = "Camera"
    override val desc: String = "This is a desc"
    override val icon: @Composable () -> Unit = { Icon(Icons.Default.Menu, null) }
}

@Given
object SamplePhonePermission : RuntimePermission {
    override val permissionName: String
        get() = android.Manifest.permission.CALL_PHONE
    override val title: String = "Call phone"
    override val desc: String = "This is a desc"
    override val icon: @Composable () -> Unit = { Icon(Icons.Default.Menu, null) }
}

@Given
object SampleAccessibilityPermission : AccessibilityServicePermission {
    override val serviceClass: KClass<out AccessibilityService>
        get() = EsAccessibilityService::class
    override val title: String = "Accessibility"
    override val desc: String = "This is a desc"
    override val icon: @Composable () -> Unit = { Icon(Icons.Default.Menu, null) }
}

@Given
object SampleNotificationListenerPermission : NotificationListenerPermission {
    override val serviceClass: KClass<out NotificationListenerService>
        get() = EsNotificationListenerService::class
    override val title: String = "Notification listener"
    override val desc: String = "This is a desc"
    override val icon: @Composable () -> Unit = { Icon(Icons.Default.Menu, null) }
}

@Given
object SampleSystemOverlayPermission : SystemOverlayPermission {
    override val title: String = "System overlay"
    override val desc: String = "This is a desc"
    override val icon: @Composable () -> Unit = { Icon(Icons.Default.Menu, null) }
}

@Given
object SampleWriteSecureSettingsPermission : WriteSecureSettingsPermission {
    override val title: String = "Write secure settings"
    override val desc: String = "This is a desc"
    override val icon: @Composable () -> Unit = { Icon(Icons.Default.Menu, null) }
}

@Given
object SampleWriteSettingsPermission : WriteSettingsPermission {
    override val title: String = "Write settings"
    override val desc: String = "This is a desc"
    override val icon: @Composable () -> Unit = { Icon(Icons.Default.Menu, null) }
}
