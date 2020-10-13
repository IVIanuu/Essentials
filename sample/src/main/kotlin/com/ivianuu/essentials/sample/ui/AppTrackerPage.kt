package com.ivianuu.essentials.sample.ui

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.compose.foundation.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationCompat
import com.ivianuu.essentials.accessibility.DefaultAccessibilityService
import com.ivianuu.essentials.foreground.ForegroundManager
import com.ivianuu.essentials.permission.Desc
import com.ivianuu.essentials.permission.Permission
import com.ivianuu.essentials.permission.Title
import com.ivianuu.essentials.permission.accessibility.AccessibilityServicePermission
import com.ivianuu.essentials.permission.requestPermissions
import com.ivianuu.essentials.permission.withValue
import com.ivianuu.essentials.recentapps.currentApp
import com.ivianuu.essentials.sample.R
import com.ivianuu.essentials.ui.core.rememberState
import com.ivianuu.essentials.ui.layout.center
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.util.SystemBuildInfo
import com.ivianuu.essentials.util.showToast
import com.ivianuu.injekt.Assisted
import com.ivianuu.injekt.FunBinding
import com.ivianuu.injekt.android.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@FunBinding
@Composable
fun AppTrackerPage(
    createAppTrackerNotification: createAppTrackerNotification,
    currentApp: currentApp,
    foregroundManager: ForegroundManager,
    requestPermissions: requestPermissions,
    showToast: showToast,
) {
    var trackingEnabled by rememberState { false }
    onCommit(trackingEnabled) {
        val foregroundJob = if (!trackingEnabled) null else {
            val foregroundJob = foregroundManager.startJob(createAppTrackerNotification(null))
            currentApp()
                .onEach {
                    showToast("App changed $it")
                    createAppTrackerNotification(it)
                }
                .launchIn(foregroundJob.scope)
            foregroundJob
        }
        onDispose { foregroundJob?.stop() }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("App tracker") }) }
    ) {
        val scope = rememberCoroutineScope()
        Button(
            modifier = Modifier.center(),
            onClick = {
                scope.launch {
                    if (requestPermissions(
                            listOf(
                                AccessibilityServicePermission(
                                    DefaultAccessibilityService::class,
                                    Permission.Title withValue "Accessibility",
                                    Permission.Desc withValue "App tracking requires the accessibility permission"
                                )
                            )
                        )
                    ) {
                        trackingEnabled = !trackingEnabled
                    }
                }
            }
        ) {
            Text("Toggle tracking")
        }
    }
}

@SuppressLint("NewApi")
@FunBinding
fun createAppTrackerNotification(
    applicationContext: ApplicationContext,
    notificationManager: NotificationManager,
    systemBuildInfo: SystemBuildInfo,
    currentApp: @Assisted String?,
): Notification {
    if (systemBuildInfo.sdk >= 26) {
        notificationManager.createNotificationChannel(
            NotificationChannel(
                "app_tracker",
                "App tracking",
                NotificationManager.IMPORTANCE_LOW
            )
        )
    }

    return NotificationCompat.Builder(applicationContext, "app_tracker")
        .apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle("Current app: $currentApp")
        }
        .build()
}