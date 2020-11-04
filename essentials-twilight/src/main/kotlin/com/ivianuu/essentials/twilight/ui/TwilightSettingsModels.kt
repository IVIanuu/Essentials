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

package com.ivianuu.essentials.twilight.ui

import com.ivianuu.essentials.twilight.data.TwilightMode
import com.ivianuu.essentials.ui.store.StoreAction
import com.ivianuu.essentials.ui.store.StoreState

data class TwilightSettingsState(
    val twilightMode: TwilightMode = TwilightMode.System,
    val useBlackInDarkMode: Boolean = false
) : StoreState

sealed class TwilightSettingsAction : StoreAction {
    data class UpdateTwilightMode(val value: TwilightMode) : TwilightSettingsAction()
    data class UpdateUseBlackInDarkMode(val value: Boolean) : TwilightSettingsAction()
}
