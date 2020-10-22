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

package com.ivianuu.essentials.ui.dialog

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Box
import androidx.compose.material.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import com.ivianuu.essentials.ui.common.absorbPointer
import com.ivianuu.essentials.ui.navigation.NavigatorAmbient
import com.ivianuu.essentials.ui.navigation.popTop

@Composable
fun <T> SingleChoiceListDialog(
    items: List<T>,
    selectedItem: T,
    onSelect: (T) -> Unit,
    item: @Composable (T) -> Unit,
    dismissOnSelection: Boolean = true,
    buttonLayout: AlertDialogButtonLayout = AlertDialogButtonLayout.SideBySide,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    positiveButton: @Composable (() -> Unit)? = null,
    negativeButton: @Composable (() -> Unit)? = null,
    neutralButton: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val navigator = NavigatorAmbient.current

    Dialog(
        modifier = modifier,
        icon = icon,
        title = title,
        buttonLayout = buttonLayout,
        applyContentPadding = false,
        content = {
            ScrollableColumn {
                items.forEachIndexed { index, item ->
                    key(index) {
                        SingleChoiceDialogListItem(
                            title = { item(item) },
                            selected = item == selectedItem,
                            onSelect = {
                                onSelect(item)
                                if (dismissOnSelection) navigator.popTop()
                            }
                        )
                    }
                }
            }
        },
        positiveButton = positiveButton,
        negativeButton = negativeButton,
        neutralButton = neutralButton
    )
}

@Composable
private fun SingleChoiceDialogListItem(
    selected: Boolean,
    onSelect: () -> Unit,
    title: @Composable () -> Unit
) {
    SimpleDialogListItem(
        leading = {
            Box(modifier = Modifier.absorbPointer()) {
                RadioButton(
                    selected = selected,
                    onClick = {}
                )
            }
        },
        title = title,
        onClick = onSelect
    )
}
