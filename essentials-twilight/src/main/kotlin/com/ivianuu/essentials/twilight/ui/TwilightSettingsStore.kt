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

import com.ivianuu.essentials.store.setStateIn
import com.ivianuu.essentials.store.storeProvider
import com.ivianuu.essentials.twilight.data.TwilightModePref
import com.ivianuu.essentials.twilight.data.UseBlackInDarkModePref
import com.ivianuu.essentials.twilight.ui.TwilightSettingsAction.*
import com.ivianuu.essentials.util.exhaustive
import com.ivianuu.injekt.Binding

@Binding
fun twilightSettingsStore(
    twilightModePref: TwilightModePref,
    useBlackInDarkModePref: UseBlackInDarkModePref
) = storeProvider<TwilightSettingsState, TwilightSettingsAction>(TwilightSettingsState()) {
    twilightModePref.data.setStateIn(this) { copy(twilightMode = it) }
    useBlackInDarkModePref.data.setStateIn(this) { copy(useBlackInDarkMode = useBlackInDarkMode) }
    for (action in this) {
        when (action) {
            is UpdateTwilightMode -> twilightModePref.updateData { action.value }
            is UpdateUseBlackInDarkMode -> useBlackInDarkModePref.updateData { action.value }
        }.exhaustive
    }
}