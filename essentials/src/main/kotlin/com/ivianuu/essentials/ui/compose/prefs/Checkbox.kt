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
import com.ivianuu.essentials.store.Box
import com.ivianuu.essentials.ui.compose.common.AbsorbPointer
import com.ivianuu.essentials.ui.compose.core.composableWithKey
import com.ivianuu.essentials.ui.compose.material.EsCheckbox

@Composable
fun CheckboxPreference(
    box: Box<Boolean>,
    enabled: Boolean = true,
    dependencies: List<Dependency<*>>? = null,
    title: @Composable() () -> Unit,
    summary: @Composable() (() -> Unit)? = null,
    leading: @Composable() (() -> Unit)? = null,
    onChange: ((Boolean) -> Boolean)? = null
) = composableWithKey("CheckboxPreference:${box}") {
    PreferenceWrapper(
        box = box,
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
                    EsCheckbox(
                        checked = context.currentValue,
                        onCheckedChange = if (context.shouldBeEnabled) { newValue: Boolean -> } else null
                    )
                }
            },
            onClick = { context.setIfOk(!context.currentValue) }
        )
    }
}