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

package com.ivianuu.essentials.gestures.action.actions

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import com.ivianuu.essentials.accessibility.GlobalActionExecutor
import com.ivianuu.essentials.gestures.R
import com.ivianuu.essentials.gestures.action.Action
import com.ivianuu.essentials.gestures.action.ActionAccessibilityPermission
import com.ivianuu.essentials.gestures.action.ActionBinding
import com.ivianuu.essentials.gestures.action.ActionExecutor
import com.ivianuu.essentials.gestures.action.ActionExecutorBinding
import com.ivianuu.essentials.gestures.action.ActionId
import com.ivianuu.essentials.gestures.action.ActionRootPermission
import com.ivianuu.essentials.util.LoadStringResourceUseCase
import com.ivianuu.essentials.util.SystemBuildInfo
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.common.typeKeyOf

@Given
object LockScreenActionId : ActionId("lock_screen")

@Given
fun lockScreenAction(
    @Given stringResource: LoadStringResourceUseCase,
    @Given systemBuildInfo: SystemBuildInfo,
): @ActionBinding<LockScreenActionId> Action = Action(
    id = LockScreenActionId,
    title = stringResource(R.string.es_action_lock_screen, emptyList()),
    icon = singleActionIcon(R.drawable.es_ic_power_settings),
    permissions = listOf(
        if (systemBuildInfo.sdk >= 28) typeKeyOf<ActionAccessibilityPermission>()
        else typeKeyOf<ActionRootPermission>()
    )
)

@SuppressLint("InlinedApi")
@Given
fun lockScreenActionExecutor(
    @Given actionRootCommandRunner: ActionRootCommandRunner,
    @Given globalActionExecutor: GlobalActionExecutor,
    @Given systemBuildInfo: SystemBuildInfo,
): @ActionExecutorBinding<LockScreenActionId> ActionExecutor = {
    if (systemBuildInfo.sdk >= 28) {
        globalActionExecutor(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN)
    } else {
        actionRootCommandRunner("input keyevent 26")
    }
}
