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

package com.ivianuu.essentials.ui.prefs

import androidx.compose.Composable
import androidx.compose.key
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import com.ivianuu.essentials.store.Box
import com.ivianuu.essentials.ui.common.absorbPointer
import com.ivianuu.essentials.ui.material.RadioButton

@Composable
inline fun RadioButtonPreference(
    box: Box<Boolean>,
    enabled: Boolean = true,
    noinline title: @Composable (() -> Unit)? = null,
    noinline summary: @Composable (() -> Unit)? = null,
    noinline leading: @Composable (() -> Unit)? = null,
    dependencies: List<Dependency<*>>? = null,
) {
    key(box) {
        RadioButtonPreference(
            valueController = ValueController(box),
            enabled = enabled,
            title = title,
            summary = summary,
            leading = leading,
            dependencies = dependencies
        )
    }
}

@Composable
fun RadioButtonPreference(
    valueController: ValueController<Boolean>,
    enabled: Boolean = true,
    title: @Composable (() -> Unit)? = null,
    summary: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    dependencies: List<Dependency<*>>? = null,
) {
    PreferenceWrapper(
        valueController = valueController,
        enabled = enabled,
        dependencies = dependencies
    ) { context ->
        PreferenceLayout(
            title = title,
            summary = summary,
            leading = leading,
            trailing = {
                Box(modifier = Modifier.absorbPointer()) {
                    RadioButton(
                        selected = context.currentValue,
                        onSelect = { context.setIfOk(!context.currentValue); Unit },
                        enabled = context.shouldBeEnabled
                    )
                }
            },
            enabled = context.shouldBeEnabled,
            onClick = { context.setIfOk(!context.currentValue); Unit }
        )
    }
}
