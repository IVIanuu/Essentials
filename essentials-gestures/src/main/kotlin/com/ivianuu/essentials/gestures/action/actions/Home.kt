package com.ivianuu.essentials.gestures.action.actions

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.os.Build
import com.ivianuu.essentials.gestures.R
import com.ivianuu.essentials.gestures.action.ActionExecutor
import com.ivianuu.essentials.gestures.action.bindAction
import com.ivianuu.injekt.Factory
import com.ivianuu.injekt.Lazy
import com.ivianuu.injekt.Module
import com.ivianuu.injekt.get
import com.ivianuu.injekt.parametersOf

internal val EsHomeActionModule = Module {
    if (Build.MANUFACTURER != "OnePlus" || Build.MODEL == "GM1913") {
        bindAccessibilityAction(
            key = "home",
            accessibilityAction = AccessibilityService.GLOBAL_ACTION_HOME,
            titleRes = R.string.es_action_home,
            iconRes = R.drawable.es_ic_action_home
        )
    } else {
        bindAction(
            key = "home",
            title = { getStringResource(R.string.es_action_home) },
            iconProvider = { SingleActionIconProvider(R.drawable.es_ic_action_home) },
            executor = { get<IntentHomeActionExecutor>() }
        )
    }
}

@Factory
internal class IntentHomeActionExecutor(
    private val context: Context,
    private val lazyDelegate: Lazy<IntentActionExecutor>
) : ActionExecutor {
    override suspend fun invoke() {
        try {
            val intent = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
            context.sendBroadcast(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        lazyDelegate {
            parametersOf(Intent(Intent.ACTION_MAIN).apply { addCategory(Intent.CATEGORY_HOME) })
        }()
    }
}