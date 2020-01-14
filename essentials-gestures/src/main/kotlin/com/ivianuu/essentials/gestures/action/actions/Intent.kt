package com.ivianuu.essentials.gestures.action.actions

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.ivianuu.essentials.gestures.R
import com.ivianuu.essentials.gestures.action.ActionExecutor
import com.ivianuu.essentials.util.Toaster
import com.ivianuu.injekt.Factory
import com.ivianuu.injekt.Param

@Factory
class IntentActionExecutor(
    @Param private val intent: Intent,
    private val context: Context,
    private val toaster: Toaster
) : ActionExecutor {
    override suspend fun invoke() {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            PendingIntent.getActivity(
                context, 99, intent, 0, null
            ).send()
        } catch (e: Exception) {
            e.printStackTrace()
            toaster.toast(R.string.es_activity_not_found)
        }
    }
}