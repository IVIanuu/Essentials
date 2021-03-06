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

import android.accessibilityservice.*
import com.ivianuu.essentials.*
import com.ivianuu.essentials.accessibility.*
import com.ivianuu.essentials.gestures.R
import com.ivianuu.essentials.gestures.action.*
import com.ivianuu.injekt.*

@Provide object SplitScreenActionId : ActionId("split_screen")

@Provide fun splitScreenAction(rp: ResourceProvider): Action<SplitScreenActionId> = Action(
  id = SplitScreenActionId,
  title = loadResource(R.string.es_action_split_screen),
  permissions = accessibilityActionPermissions,
  icon = singleActionIcon(R.drawable.es_ic_view_agenda)
)

@Provide fun splitScreenActionExecutor(
  globalActionExecutor: GlobalActionExecutor
): ActionExecutor<SplitScreenActionId> = {
  globalActionExecutor(AccessibilityService.GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN)
}
