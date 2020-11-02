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

package com.ivianuu.essentials.apps.ui.apppicker

import com.ivianuu.essentials.apps.getInstalledApps
import com.ivianuu.essentials.apps.ui.AppFilter
import com.ivianuu.essentials.apps.ui.apppicker.AppPickerAction.*
import com.ivianuu.essentials.store.storeProvider
import com.ivianuu.essentials.ui.navigation.Navigator
import com.ivianuu.essentials.ui.navigation.popTop
import com.ivianuu.essentials.ui.store.execute
import com.ivianuu.essentials.util.exhaustive
import com.ivianuu.injekt.Binding
import com.ivianuu.injekt.FunApi

@Binding
fun appPickerStore(
    navigator: Navigator,
    getInstalledApps: getInstalledApps,
    @FunApi appFilter: AppFilter
) = storeProvider<AppPickerState, AppPickerAction>(AppPickerState(appFilter = appFilter)) {
    execute(
        block = { getInstalledApps() },
        reducer = { copy(allApps = it) }
    )

    for (action in this) {
        when (action) {
            is PickApp -> navigator.popTop(result = action.app)
        }.exhaustive
    }
}