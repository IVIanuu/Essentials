package com.ivianuu.essentials.gestures.action.actions

import androidx.compose.Composable
import com.ivianuu.essentials.accessibility.AccessibilityServices
import com.ivianuu.essentials.gestures.action.Action
import com.ivianuu.essentials.gestures.action.ActionExecutor
import com.ivianuu.essentials.gestures.action.bindAction
import com.ivianuu.essentials.gestures.action.bindActionFactory
import com.ivianuu.essentials.gestures.action.permissions
import com.ivianuu.essentials.util.Resources
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.Reader
import com.ivianuu.injekt.given

@Reader
internal fun bindAccessibilityAction(
    key: String,
    accessibilityAction: Int,
    titleRes: Int,
    icon: @Composable () -> Unit
) = bindAction {
    Action(
        key = key,
        title = Resources.getString(titleRes),
        iconProvider = SingleActionIconProvider(icon),
        permissions = permissions { listOf(accessibility) },
        executor = given<(Int) -> AccessibilityActionExecutor>()(accessibilityAction)
    )
}

@Given
@Reader
internal class AccessibilityActionExecutor(
    private val accessibilityAction: Int
) : ActionExecutor {
    override suspend fun invoke() {
        given<AccessibilityServices>().performGlobalAction(accessibilityAction)
    }
}
