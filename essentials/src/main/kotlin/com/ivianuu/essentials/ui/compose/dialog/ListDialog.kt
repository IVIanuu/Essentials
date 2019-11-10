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

package com.ivianuu.essentials.ui.compose.dialog

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.dp
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.CrossAxisAlignment
import androidx.ui.layout.EdgeInsets
import androidx.ui.layout.ExpandedWidth
import androidx.ui.layout.MainAxisAlignment
import androidx.ui.layout.Row
import androidx.ui.layout.WidthSpacer
import androidx.ui.material.ripple.Ripple
import com.ivianuu.essentials.ui.compose.core.composable

@Composable
fun ListDialog(
    buttonLayout: AlertDialogButtonLayout = AlertDialogButtonLayout.SideBySide,
    icon: @Composable() (() -> Unit)? = null,
    title: (@Composable() () -> Unit)? = null,
    listContent: @Composable() () -> Unit,
    positiveButton: (@Composable() () -> Unit)? = null,
    negativeButton: (@Composable() () -> Unit)? = null,
    neutralButton: (@Composable() () -> Unit)? = null
) = composable("ListDialog") {
    MaterialDialog(
        icon = icon,
        title = title,
        showDividers = true,
        applyContentPadding = false,
        buttonLayout = buttonLayout,
        content = {
            VerticalScroller {
                Column {
                    listContent()
                }
            }
        },
        positiveButton = positiveButton,
        negativeButton = negativeButton,
        neutralButton = neutralButton
    )
}

@Composable
fun SimpleDialogListItem(
    leading: (@Composable() () -> Unit)? = null,
    title: @Composable() () -> Unit,
    onClick: (() -> Unit)? = null
) = composable("SimpleDialogListItem") {
    Ripple(bounded = true) {
        Clickable(onClick = onClick) {
            Container(
                modifier = ExpandedWidth,
                height = 48.dp,
                padding = EdgeInsets(
                    left = 24.dp,
                    right = 24.dp
                ),
                alignment = Alignment.CenterLeft
            ) {
                Row(
                    mainAxisAlignment = MainAxisAlignment.End,
                    crossAxisAlignment = CrossAxisAlignment.Center
                ) {
                    if (leading != null) {
                        leading()

                        WidthSpacer(24.dp)
                    }

                    title()
                }
            }
        }
    }
}