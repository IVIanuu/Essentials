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

package com.ivianuu.essentials.ui.material

import androidx.compose.Ambient
import androidx.compose.Composable
import androidx.compose.ambient
import androidx.ui.core.Modifier
import androidx.ui.material.ButtonStyle
import androidx.ui.material.ContainedButtonStyle

val ButtonStyleAmbient = Ambient.of<ButtonStyle?>()

@Composable
fun Button(
    modifier: Modifier = Modifier.None,
    onClick: (() -> Unit)? = null,
    style: ButtonStyle = ambient(ButtonStyleAmbient) ?: ContainedButtonStyle(),
    children: @Composable() () -> Unit
) {
    androidx.ui.material.Button(
        modifier = modifier, onClick = onClick, style = style, children = children
    )
}

@Composable
fun Button(
    text: String,
    modifier: Modifier = Modifier.None,
    onClick: (() -> Unit)? = null,
    style: ButtonStyle = ambient(ButtonStyleAmbient) ?: ContainedButtonStyle()
) {
    androidx.ui.material.Button(
        modifier = modifier, onClick = onClick, style = style, text = text
    )
}
