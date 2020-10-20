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

import android.Manifest
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import com.ivianuu.essentials.accessibility.DefaultAccessibilityService
import com.ivianuu.essentials.notificationlistener.DefaultNotificationListenerService
import com.ivianuu.essentials.permission.Desc
import com.ivianuu.essentials.permission.Icon
import com.ivianuu.essentials.permission.Permission
import com.ivianuu.essentials.permission.Title
import com.ivianuu.essentials.permission.accessibility.AccessibilityServicePermission
import com.ivianuu.essentials.permission.notificationlistener.NotificationListenerPermission
import com.ivianuu.essentials.permission.requestPermissions
import com.ivianuu.essentials.permission.runtime.RuntimePermission
import com.ivianuu.essentials.permission.systemoverlay.SystemOverlayPermission
import com.ivianuu.essentials.permission.withValue
import com.ivianuu.essentials.permission.writesecuresettings.WriteSecureSettingsPermission
import com.ivianuu.essentials.permission.writesettings.WriteSettingsPermission
import com.ivianuu.essentials.ui.layout.center
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.injekt.FunBinding
import kotlinx.coroutines.launch

typealias PermissionsPage = @Composable () -> Unit

@FunBinding
@Composable
fun PermissionsPage(requestPermissions: requestPermissions) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Permissions") }) }
    ) {
        val camera = RuntimePermission(
            Manifest.permission.CAMERA,
            Permission.Title withValue "Camera",
            Permission.Desc withValue "This is a desc",
            Permission.Icon withValue { Icon(Icons.Default.Menu) }
        )

        val phone = RuntimePermission(
            Manifest.permission.CALL_PHONE,
            Permission.Title withValue "Call phone",
            Permission.Desc withValue "This is a desc",
            Permission.Icon withValue { Icon(Icons.Default.Menu) }
        )

        val accessibility = AccessibilityServicePermission(
            DefaultAccessibilityService::class,
            Permission.Title withValue "Accessibility",
            Permission.Desc withValue "This is a desc",
            Permission.Icon withValue { Icon(Icons.Default.Menu) }
        )

        val notificationListener = NotificationListenerPermission(
            DefaultNotificationListenerService::class,
            Permission.Title withValue "Notification listener",
            Permission.Desc withValue "This is a desc",
            Permission.Icon withValue { Icon(Icons.Default.Menu) }
        )

        val systemOverlay = SystemOverlayPermission(
            ContextAmbient.current,
            Permission.Title withValue "System overlay",
            Permission.Desc withValue "This is a desc",
            Permission.Icon withValue { Icon(Icons.Default.Menu) }
        )

        val writeSecureSettings = WriteSecureSettingsPermission(
            Permission.Title withValue "Write secure settings",
            Permission.Desc withValue "This is a desc",
            Permission.Icon withValue { Icon(Icons.Default.Menu) }
        )

        val writeSettings = WriteSettingsPermission(
            ContextAmbient.current,
            Permission.Title withValue "Write settings",
            Permission.Desc withValue "This is a desc",
            Permission.Icon withValue { Icon(Icons.Default.Menu) }
        )

        val scope = rememberCoroutineScope()
        Button(
            modifier = Modifier.center(),
            onClick = {
                scope.launch {
                    requestPermissions(
                        listOf(
                            camera,
                            phone,
                            accessibility,
                            notificationListener,
                            systemOverlay,
                            writeSecureSettings,
                            writeSettings
                        )
                    )
                }
            }
        ) {
            Text("Request")
        }
    }
}
