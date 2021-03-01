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
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationCompat
import com.ivianuu.essentials.accessibility.EsAccessibilityService
import com.ivianuu.essentials.foreground.ForegroundState
import com.ivianuu.essentials.foreground.ForegroundState.Background
import com.ivianuu.essentials.foreground.ForegroundState.Foreground
import com.ivianuu.essentials.foreground.ForegroundStateBinding
import com.ivianuu.essentials.permission.PermissionBinding
import com.ivianuu.essentials.permission.PermissionRequester
import com.ivianuu.essentials.permission.accessibility.AccessibilityServicePermission
import com.ivianuu.essentials.recentapps.CurrentApp
import com.ivianuu.essentials.sample.R
import com.ivianuu.essentials.ui.layout.center
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.Key
import com.ivianuu.essentials.ui.navigation.KeyModule
import com.ivianuu.essentials.ui.navigation.KeyUi
import com.ivianuu.essentials.util.SystemBuildInfo
import com.ivianuu.essentials.util.Toaster
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.Module
import com.ivianuu.injekt.android.AppContext
import com.ivianuu.injekt.common.Scoped
import com.ivianuu.injekt.common.typeKeyOf
import com.ivianuu.injekt.component.AppComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

@HomeItemBinding
@Given
val appTrackerHomeItem = HomeItem("App tracker") { AppTrackerKey() }

class AppTrackerKey : Key<Nothing>

@Module
val appTrackerKeyModule = KeyModule<AppTrackerKey>()

@Given
fun appTrackerUi(
    @Given currentApp: Flow<CurrentApp>,
    @Given foregroundState: AppTrackerForegroundState,
    @Given notificationFactory: AppTrackerNotificationFactory,
    @Given permissionRequester: PermissionRequester,
    @Given toaster: Toaster,
): KeyUi<AppTrackerKey> = {
    val currentForegroundState by foregroundState.collectAsState()

    if (currentForegroundState is Foreground) {
        LaunchedEffect(true) {
            currentApp
                .onEach {
                    toaster.showToast("App changed $it")
                    foregroundState.value = Foreground(notificationFactory(it))
                }
                .collect()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("App tracker") }) }
    ) {
        val scope = rememberCoroutineScope()
        Button(
            modifier = Modifier.center(),
            onClick = {
                scope.launch {
                    if (permissionRequester(listOf(typeKeyOf<SampleNotificationsPermission>()))) {
                        foregroundState.value = if (currentForegroundState is Foreground) Background
                        else Foreground(notificationFactory(null))
                    }
                }
            }
        ) {
            Text("Toggle tracking")
        }
    }
}

typealias AppTrackerForegroundState = MutableStateFlow<ForegroundState>

@Scoped<AppComponent>
@Given
fun appTrackerForegroundState(): AppTrackerForegroundState = MutableStateFlow(Background)

@ForegroundStateBinding
@Given
inline val @Given AppTrackerForegroundState.bindAppTrackerForegroundState: Flow<ForegroundState>
    get() = this

typealias AppTrackerNotificationFactory = (String?) -> Notification

@SuppressLint("NewApi")
@Given
fun appTrackerNotificationFactory(
    @Given appContext: AppContext,
    @Given notificationManager: NotificationManager,
    @Given systemBuildInfo: SystemBuildInfo
): AppTrackerNotificationFactory = { currentApp ->
    if (systemBuildInfo.sdk >= 26) {
        notificationManager.createNotificationChannel(
            NotificationChannel(
                "app_tracker",
                "App tracking",
                NotificationManager.IMPORTANCE_LOW
            )
        )
    }
    NotificationCompat.Builder(appContext, "app_tracker")
        .apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle("Current app: $currentApp")
        }
        .build()
}

@PermissionBinding
@Given
object AppTrackerAccessibilityPermission : AccessibilityServicePermission {
    override val serviceClass: KClass<out AccessibilityService>
        get() = EsAccessibilityService::class
    override val title: String = "Accessibility"
    override val desc: String = "Needs the permission to track the current app"
    override val icon: @Composable (() -> Unit)?
        get() = null
}
