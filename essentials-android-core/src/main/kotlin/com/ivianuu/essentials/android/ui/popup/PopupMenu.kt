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

package com.ivianuu.essentials.android.ui.popup

import androidx.compose.Composable
import androidx.compose.Immutable
import androidx.compose.key
import androidx.ui.core.Alignment
import androidx.ui.foundation.Clickable
import androidx.ui.layout.Container
import androidx.ui.layout.LayoutHeight
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.LayoutWidth
import androidx.ui.layout.Wrap
import androidx.ui.material.ripple.Ripple
import androidx.ui.unit.dp
import com.ivianuu.essentials.android.ui.core.Text
import com.ivianuu.essentials.android.ui.layout.Column
import com.ivianuu.essentials.android.ui.navigation.NavigatorAmbient

object PopupMenu {
    @Immutable
    data class Item(
        val onSelected: (() -> Unit)? = null,
        val content: @Composable () -> Unit
    ) {
        constructor(
            title: String,
            onSelected: (() -> Unit)? = null
        ) : this(onSelected = onSelected, content = {
            Text(title)
        })
    }
}

@Composable
fun PopupMenu(
    items: List<PopupMenu.Item>,
    style: PopupStyle = PopupStyleAmbient.current
) {
    Popup(style = style) {
        Column {
            val navigator = NavigatorAmbient.current
            items.forEach { item ->
                key(item) {
                    PopupMenuItem(
                        onSelected = {
                            navigator.popTop()
                            item.onSelected?.invoke()
                        },
                        children = item.content
                    )
                }
            }
        }
    }
}

@Composable
fun <T> PopupMenu(
    items: List<T>,
    selectedItem: T? = null,
    onSelected: (T) -> Unit,
    style: PopupStyle = PopupStyleAmbient.current,
    itemCallback: @Composable (T, Boolean) -> Unit
) {
    Popup(style = style) {
        Column {
            val navigator = NavigatorAmbient.current
            items.forEach { item ->
                key(item) {
                    PopupMenuItem(
                        onSelected = {
                            navigator.popTop()
                            onSelected(item)
                        }
                    ) {
                        itemCallback(item, item == selectedItem)
                    }
                }
            }
        }
    }
}

@Composable
private fun PopupMenuItem(
    onSelected: () -> Unit,
    children: @Composable () -> Unit
) {
    Ripple(bounded = true) {
        Clickable(
            onClick = onSelected,
            children = {
                Container(
                    modifier = LayoutWidth.Min(200.dp) + LayoutHeight(48.dp),
                    alignment = Alignment.CenterStart
                ) {
                    Wrap(Alignment.CenterStart) {
                        Container(
                            modifier = LayoutPadding(start = 16.dp, end = 16.dp),
                            children = children
                        )
                    }
                }
            }
        )
    }
}