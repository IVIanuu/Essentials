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

import com.ivianuu.essentials.accessibility.performGlobalAction
import com.ivianuu.essentials.gestures.action.Action
import com.ivianuu.essentials.gestures.action.ActionIcon
import com.ivianuu.essentials.gestures.action.choosePermissions
import com.ivianuu.essentials.util.stringResource
import com.ivianuu.injekt.FunApi
import com.ivianuu.injekt.FunBinding

@FunBinding
fun accessibilityAction(
    choosePermissions: choosePermissions,
    performGlobalAction: performGlobalAction,
    stringResource: stringResource,
    @FunApi key: String,
    @FunApi accessibilityAction: Int,
    @FunApi titleRes: Int,
    @FunApi icon: ActionIcon
) = Action(
    key = key,
    title = stringResource(titleRes),
    icon = icon,
    permissions = choosePermissions { listOf(accessibility) },
    execute = {
        performGlobalAction(accessibilityAction)
    }
)
