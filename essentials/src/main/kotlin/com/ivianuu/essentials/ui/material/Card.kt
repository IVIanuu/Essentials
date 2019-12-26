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
import androidx.ui.core.Dp
import androidx.ui.core.Modifier
import androidx.ui.core.dp
import androidx.ui.engine.geometry.Shape
import androidx.ui.foundation.shape.RectangleShape
import androidx.ui.foundation.shape.border.Border
import androidx.ui.graphics.Color
import androidx.ui.material.MaterialTheme

@Composable
data class CardStyle(
    val shape: Shape = RectangleShape,
    val color: Color,
    val contentColor: Color = guessingContentColorFor(color),
    val border: Border? = null,
    val elevation: Dp = 1.dp
)

val CardStyleAmbient = Ambient.of<CardStyle?>()

@Composable
fun DefaultCardStyle(
    shape: Shape = RectangleShape,
    color: Color = MaterialTheme.colors().surface,
    contentColor: Color = guessingContentColorFor(color),
    border: Border? = null,
    elevation: Dp = 1.dp
) = CardStyle(
    shape = shape,
    color = color,
    contentColor = contentColor,
    border = border,
    elevation = elevation
)

@Composable
fun Card(
    modifier: Modifier = Modifier.None,
    style: CardStyle = ambient(CardStyleAmbient) ?: DefaultCardStyle(),
    children: @Composable() () -> Unit
) {
    androidx.ui.material.surface.Card(
        modifier = modifier,
        shape = style.shape,
        color = style.color,
        contentColor = style.contentColor,
        border = style.border,
        elevation = style.elevation,
        children = children
    )
}