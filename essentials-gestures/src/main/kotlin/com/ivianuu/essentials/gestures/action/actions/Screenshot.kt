package com.ivianuu.essentials.gestures.action.actions

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import com.ivianuu.essentials.gestures.R
import com.ivianuu.essentials.gestures.action.bindAction
import com.ivianuu.essentials.util.SystemBuildInfo
import com.ivianuu.injekt.Module
import com.ivianuu.injekt.get
import com.ivianuu.injekt.parametersOf
import kotlinx.coroutines.delay

@SuppressLint("InlinedApi")
internal val EsScreenshotActionModule = Module {
    bindAction(
        key = "screenshot",
        title = { getStringResource(R.string.es_action_screenshot) },
        iconProvider = { SingleActionIconProvider(R.drawable.es_ic_photo_album) },
        executor = {
            val executor = if (get<SystemBuildInfo>().sdk >= 28) {
                get<AccessibilityActionExecutor> {
                    parametersOf(AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT)
                }
            } else {
                get<RootActionExecutor> {
                    parametersOf("input keyevent 26")
                }
            }

            return@bindAction executor.beforeAction { delay(500) }
        }
    )
}
