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

package com.ivianuu.essentials.ui.dialog

import androidx.compose.Composable
import androidx.ui.core.Modifier
import com.ivianuu.essentials.ui.core.currentOrElse
import com.ivianuu.essentials.ui.material.Button
import com.ivianuu.essentials.ui.material.ButtonStyle
import com.ivianuu.essentials.ui.material.ButtonStyleAmbient
import com.ivianuu.essentials.ui.material.TextButtonStyle
import com.ivianuu.essentials.ui.navigation.NavigatorAmbient

@Composable
fun DialogButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.None,
    enabled: Boolean = true,
    dismissDialogOnClick: Boolean = true,
    style: ButtonStyle = ButtonStyleAmbient.currentOrElse { TextButtonStyle() },
    children: @Composable () -> Unit
) {
    val navigator = NavigatorAmbient.current
    Button(
        modifier = modifier,
        onClick = {
            onClick()
            if (dismissDialogOnClick) navigator.popTop()
        },
        enabled = enabled,
        style = style,
        children = children
    )
}

@Composable
fun DialogCloseButton(
    style: ButtonStyle = TextButtonStyle(),
    children: @Composable() () -> Unit
) {
    DialogButton(
        onClick = {},
        dismissDialogOnClick = true,
        style = style,
        children = children
    )
}
