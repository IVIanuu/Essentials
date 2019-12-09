/*
 * Copyright 2019 Manuel Wrage
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

package com.ivianuu.essentials.twilight

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.material.RadioButton
import com.ivianuu.essentials.ui.compose.box.unfoldBox
import com.ivianuu.essentials.ui.compose.common.ListScreen
import com.ivianuu.essentials.ui.compose.core.composableWithKey
import com.ivianuu.essentials.ui.compose.es.ComposeControllerRoute
import com.ivianuu.essentials.ui.compose.injekt.inject
import com.ivianuu.essentials.ui.compose.material.SimpleListItem
import com.ivianuu.essentials.ui.compose.resources.stringResource
import com.ivianuu.essentials.ui.navigation.director.defaultControllerRouteOptionsOrNull

val twilightSettingsRoute =
    ComposeControllerRoute(
        options = defaultControllerRouteOptionsOrNull()
    ) {
        ListScreen(title = stringResource(R.string.es_title_twilight)) {
            val prefs = inject<TwilightPrefs>()
            val (value, setValue) = unfoldBox(prefs.twilightMode)

            TwilightMode.values().toList().forEach { mode ->
                TwilightModeItem(
                    mode = mode,
                    isSelected = value == mode,
                    onClick = { setValue(mode) }
                )
            }
        }
    }

@Composable
private fun TwilightModeItem(
    mode: TwilightMode,
    isSelected: Boolean,
    onClick: () -> Unit
) = composableWithKey(mode.value, isSelected) {
    SimpleListItem(
        title = { Text(stringResource(mode.titleRes)) },
        subtitle = { Text(stringResource(mode.descRes)) },
        trailing = {
            RadioButton(
                selected = isSelected,
                onSelect = onClick
            )
        },
        onClick = onClick
    )
}