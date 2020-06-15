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
import androidx.compose.Immutable
import androidx.ui.core.Modifier
import com.ivianuu.essentials.R
import com.ivianuu.essentials.store.Box
import com.ivianuu.essentials.ui.box.asState
import com.ivianuu.essentials.ui.core.Text
import com.ivianuu.essentials.ui.dialog.DialogCloseButton
import com.ivianuu.essentials.ui.dialog.SingleChoiceListDialog

@Composable
fun <T> SingleChoiceDialogListItem(
    box: Box<T>,
    title: @Composable (() -> Unit)? = null,
    subtitle: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    dialogTitle: @Composable (() -> Unit)? = title,
    items: List<SingleChoiceDialogListItem.Item<T>>,
    modifier: Modifier = Modifier
) {
    val state = box.asState()
    SingleChoiceDialogListItem(
        value = state.value,
        onValueChange = { state.value = it },
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        leading = leading,
        dialogTitle = dialogTitle,
        items = items
    )
}

@Composable
fun <T> SingleChoiceDialogListItem(
    value: T,
    onValueChange: (T) -> Unit,
    title: @Composable (() -> Unit)? = null,
    subtitle: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    dialogTitle: @Composable (() -> Unit)? = title,
    items: List<SingleChoiceDialogListItem.Item<T>>,
    modifier: Modifier = Modifier
) {
    DialogListItem(
        modifier = modifier,
        title = title?.let { { title() } },
        subtitle = subtitle?.let { { subtitle() } },
        leading = leading?.let { { leading() } },
        dialog = { dismiss ->
            SingleChoiceListDialog(
                items = items,
                selectedItem = items.first { it.value == value },
                onSelect = { onValueChange(it.value) },
                item = { Text(it.title) },
                title = dialogTitle,
                negativeButton = {
                    DialogCloseButton {
                        Text(R.string.es_cancel)
                    }
                }
            )
        }
    )
}

object SingleChoiceDialogListItem {
    @Immutable
    data class Item<T>(
        val title: String,
        val value: T
    )
}