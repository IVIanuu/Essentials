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

import android.view.inputmethod.*
import com.ivianuu.essentials.*
import com.ivianuu.essentials.gestures.R
import com.ivianuu.essentials.gestures.action.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.android.*

@Provide object InputMethodActionId : ActionId("input_method")

@Provide fun inputMethodAction(rp: ResourceProvider): Action<InputMethodActionId> =
  Action(
    id = InputMethodActionId,
    title = loadResource(R.string.es_action_input_method),
    icon = singleActionIcon(R.drawable.es_ic_keyboard_hide)
  )

@Provide fun inputMethodActionExecutor(
  inputMethodManager: @SystemService InputMethodManager
): ActionExecutor<InputMethodActionId> = {
  inputMethodManager.showInputMethodPicker()
}
