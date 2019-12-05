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

package com.ivianuu.essentials.ui.compose.prefs

import androidx.compose.Composable
import androidx.ui.graphics.Image
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Switch
import com.ivianuu.essentials.store.Box
import com.ivianuu.essentials.ui.compose.common.AbsorbPointer
import com.ivianuu.essentials.ui.compose.common.asIconComposable
import com.ivianuu.essentials.ui.compose.common.asTextComposable
import com.ivianuu.essentials.ui.compose.core.composable
import com.ivianuu.essentials.ui.compose.core.composableWithKey
import com.ivianuu.essentials.ui.compose.core.invoke

@Composable
fun SwitchPreference(
    box: Box<Boolean>,
    onChange: ((Boolean) -> Boolean)? = null,
    enabled: Boolean = true,
    dependencies: List<Dependency<*>>? = null,
    title: String? = null,
    summary: String? = null,
    image: Image? = null
) = composableWithKey("SwitchPreference:$box") {
    SwitchPreference(
        valueController = ValueController(box),
        onChange = onChange,
        enabled = enabled,
        dependencies = dependencies,
        title = title.asTextComposable(),
        summary = summary.asTextComposable(),
        leading = image.asIconComposable()
    )
}

@Composable
fun SwitchPreference(
    valueController: ValueController<Boolean>,
    onChange: ((Boolean) -> Boolean)? = null,
    enabled: Boolean = true,
    dependencies: List<Dependency<*>>? = null,
    title: (@Composable() () -> Unit)? = null,
    summary: (@Composable() () -> Unit)? = null,
    leading: (@Composable() () -> Unit)? = null
) = composable {
    PreferenceWrapper(
        valueController = valueController,
        onChange = onChange,
        enabled = enabled,
        dependencies = dependencies
    ) { context ->
        PreferenceLayout(
            title = title,
            summary = summary,
            leading = leading,
            trailing = {
                AbsorbPointer {
                    Switch(
                        color = MaterialTheme.colors()().secondary,
                        checked = context.currentValue,
                        onCheckedChange = if (context.shouldBeEnabled) { newValue: Boolean -> } else null
                    )
                }
            },
            onClick = if (!context.shouldBeEnabled) null else {
                { context.setIfOk(!context.currentValue); Unit }
            }
        )
    }
}
