package com.ivianuu.essentials.gestures.action.actions

import android.content.Intent
import android.view.KeyEvent
import com.ivianuu.essentials.gestures.action.Action
import com.ivianuu.essentials.gestures.action.ActionIcon
import com.ivianuu.essentials.gestures.action.ActionPrefs
import com.ivianuu.essentials.util.Resources
import com.ivianuu.injekt.Assisted
import com.ivianuu.injekt.FunBinding
import com.ivianuu.injekt.android.ApplicationContext
import kotlinx.coroutines.flow.first

@FunBinding
fun mediaAction(
    doMediaAction: doMediaAction,
    resources: Resources,
    key: @Assisted String,
    keycode: @Assisted Int,
    titleRes: @Assisted Int,
    icon: @Assisted ActionIcon,
): Action = Action(
    key = key,
    title = resources.getString(titleRes),
    icon = icon,
    execute = { doMediaAction(keycode) }
)

@FunBinding
suspend fun doMediaAction(
    applicationContext: ApplicationContext,
    mediaIntent: mediaIntent,
    keycode: @Assisted Int,
) {
    applicationContext.sendOrderedBroadcast(mediaIntent(KeyEvent.ACTION_DOWN, keycode), null)
    applicationContext.sendOrderedBroadcast(mediaIntent(KeyEvent.ACTION_UP, keycode), null)
}

@FunBinding
suspend fun mediaIntent(
    prefs: ActionPrefs,
    keyEvent: @Assisted Int,
    keycode: @Assisted Int,
): Intent = Intent(Intent.ACTION_MEDIA_BUTTON).apply {
    putExtra(
        Intent.EXTRA_KEY_EVENT,
        KeyEvent(keyEvent, keycode)
    )

    val mediaApp = prefs.actionMediaApp.data.first()
    if (mediaApp != null) {
        `package` = mediaApp
    }
}

