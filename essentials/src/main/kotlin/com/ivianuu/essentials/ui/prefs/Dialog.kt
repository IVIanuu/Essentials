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
import com.ivianuu.essentials.ui.dialog.DialogRoute
import com.ivianuu.essentials.ui.navigation.navigator

@Composable
fun <T> DialogPreference(
    valueController: ValueController<T>,
    enabled: Boolean = true,
    dependencies: List<Dependency<*>>? = null,
    title: @Composable() ((PreferenceContext<T>) -> Unit)? = null,
    summary: @Composable() ((PreferenceContext<T>) -> Unit)? = null,
    leading: @Composable() ((PreferenceContext<T>) -> Unit)? = null,
    trailing: @Composable() ((PreferenceContext<T>) -> Unit)? = null,
    dialog: @Composable() (PreferenceContext<T>, () -> Unit) -> Unit
) {
    val navigator = navigator
    PreferenceWrapper(
        valueController = valueController,
        enabled = enabled,
        dependencies = dependencies
    ) { context ->
        PreferenceLayout(
            title = title?.let { { title(context) } },
            summary = summary?.let { { summary(context) } },
            leading = leading?.let { { leading(context) } },
            trailing = trailing?.let { { trailing(context) } },
            onClick = if (!context.shouldBeEnabled) null else {
                {
                    navigator.push(DialogRoute {
                        dialog(context) { navigator.pop() }
                    })
                }
            }
        )
    }
}
