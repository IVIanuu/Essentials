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
import androidx.compose.Immutable
import androidx.compose.Pivotal
import androidx.ui.core.Text
import androidx.ui.graphics.Image
import com.ivianuu.essentials.R
import com.ivianuu.essentials.store.Box
import com.ivianuu.essentials.ui.compose.common.asIconComposable
import com.ivianuu.essentials.ui.compose.common.asTextComposable
import com.ivianuu.essentials.ui.compose.core.stateFor
import com.ivianuu.essentials.ui.compose.dialog.DialogButton
import com.ivianuu.essentials.ui.compose.dialog.DialogCloseButton
import com.ivianuu.essentials.ui.compose.dialog.MultiChoiceListDialog
import com.ivianuu.essentials.ui.compose.resources.stringResource

@Composable
fun <T> MultiChoiceListPreference(
    @Pivotal box: Box<Set<T>>,
    onChange: ((Set<T>) -> Boolean)? = null,
    enabled: Boolean = true,
    dependencies: List<Dependency<*>>? = null,
    title: String? = null,
    summary: String? = null,
    image: Image? = null,
    dialogTitle: String? = title,
    items: List<MultiChoiceListPreference.Item<T>>
) {
    MultiChoiceListPreference(
        valueController = ValueController(box),
        onChange = onChange,
        enabled = enabled,
        dependencies = dependencies,
        title = title.asTextComposable(),
        summary = summary.asTextComposable(),
        leading = image.asIconComposable(),
        dialogTitle = dialogTitle.asTextComposable(),
        items = items
    )
}

@Composable
fun <T> MultiChoiceListPreference(
    valueController: ValueController<Set<T>>,
    onChange: ((Set<T>) -> Boolean)? = null,
    enabled: Boolean = true,
    dependencies: List<Dependency<*>>? = null,
    title: (@Composable() () -> Unit)? = null,
    summary: (@Composable() () -> Unit)? = null,
    leading: (@Composable() () -> Unit)? = null,
    dialogTitle: (@Composable() () -> Unit)? = title,
    items: List<MultiChoiceListPreference.Item<T>>
) {
    DialogPreference(
        valueController = valueController,
        onChange = onChange,
        enabled = enabled,
        dependencies = dependencies,
        title = title,
        summary = summary,
        leading = leading,
        dialog = { context, dismiss ->
            val (selectedItems, setSelectedItems) = stateFor(context.currentValue) {
                context.currentValue
                    .map { value -> items.first { it.value == value } }
            }

            MultiChoiceListDialog(
                items = items,
                selectedItems = selectedItems,
                onSelectionsChanged = setSelectedItems,
                item = { Text(it.title) },
                title = dialogTitle,
                positiveButton = {
                    DialogButton(
                        text = stringResource(R.string.es_ok),
                        onClick = {
                            val newValue = selectedItems.map { it.value }.toSet()
                            context.setIfOk(newValue)
                        }
                    )
                },
                negativeButton = { DialogCloseButton(stringResource(R.string.es_cancel)) }
            )
        }
    )
}

object MultiChoiceListPreference {
    @Immutable
    data class Item<T>(
        val title: String,
        val value: T
    )
}
