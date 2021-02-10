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
import android.content.Intent
import android.os.Build
import com.ivianuu.essentials.accessibility.performGlobalAction
import com.ivianuu.essentials.gestures.R
import com.ivianuu.essentials.gestures.action.*
import com.ivianuu.essentials.result.onFailure
import com.ivianuu.essentials.result.runKatching
import com.ivianuu.essentials.util.stringResource
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.GivenFun
import com.ivianuu.injekt.android.AppContext

@Given object HomeActionId : ActionId("home")

@ActionBinding<HomeActionId>
@Given
fun homeAction(
    @Given choosePermissions: choosePermissions,
    @Given stringResource: stringResource,
): Action = Action(
    id = HomeActionId,
    title = stringResource(R.string.es_action_home),
    permissions = choosePermissions {
        if (needsHomeIntentWorkaround) emptyList()
        else listOf(accessibility)
    },
    icon = singleActionIcon(R.drawable.es_ic_action_home)
)

@ActionExecutorBinding<HomeActionId>
@GivenFun
suspend fun openHomeScreen(
    @Given appContext: AppContext,
    @Given performGlobalAction: performGlobalAction,
    @Given sendIntent: sendIntent,
) {
    if (!needsHomeIntentWorkaround) {
        performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)
    } else {
        runKatching {
            val intent = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
            appContext.sendBroadcast(intent)
        }.onFailure { it.printStackTrace() }

        sendIntent(
            Intent(Intent.ACTION_MAIN).apply {
                addCategory(
                    Intent.CATEGORY_HOME
                )
            }
        )
    }
}

private val needsHomeIntentWorkaround = Build.MANUFACTURER != "OnePlus" || Build.MODEL == "GM1913"
