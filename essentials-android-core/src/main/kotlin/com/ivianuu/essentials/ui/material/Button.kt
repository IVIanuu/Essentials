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

import androidx.compose.Composable
import androidx.compose.Immutable
import androidx.compose.Providers
import androidx.compose.staticAmbientOf
import androidx.ui.core.Alignment
import androidx.ui.core.CurrentTextStyleProvider
import androidx.ui.core.Modifier
import androidx.ui.foundation.Border
import androidx.ui.foundation.Box
import androidx.ui.foundation.Clickable
import androidx.ui.graphics.Color
import androidx.ui.graphics.Shape
import androidx.ui.layout.EdgeInsets
import androidx.ui.layout.LayoutSize
import androidx.ui.material.MaterialTheme
import androidx.ui.material.contentColorFor
import androidx.ui.material.ripple.Ripple
import androidx.ui.semantics.Semantics
import androidx.ui.unit.Dp
import androidx.ui.unit.dp
import com.ivianuu.essentials.ui.core.DefaultTextComposableStyle
import com.ivianuu.essentials.ui.core.TextComposableStyleAmbient
import com.ivianuu.essentials.ui.core.currentOrElse

@Immutable
data class ButtonStyle(
    val backgroundColor: Color,
    val contentColor: Color,
    val shape: Shape,
    val border: Border? = null,
    val elevation: Dp = 0.dp,
    val innerPadding: EdgeInsets = EdgeInsets(
        left = 16.dp,
        top = 8.dp,
        right = 16.dp,
        bottom = 8.dp
    )
)

@Composable
fun ContainedButtonStyle(
    backgroundColor: Color = MaterialTheme.colors().primary,
    contentColor: Color = contentColorFor(backgroundColor),
    shape: Shape = MaterialTheme.shapes().button,
    elevation: Dp = 2.dp
) = ButtonStyle(
    backgroundColor = backgroundColor,
    shape = shape,
    elevation = elevation,
    contentColor = contentColor
)

@Composable
fun OutlinedButtonStyle(
    border: Border = Border(
        size = 1.dp,
        color = MaterialTheme.colors().onSurface.copy(alpha = 0.12f)
    ),
    backgroundColor: Color = MaterialTheme.colors().surface,
    contentColor: Color = MaterialTheme.colors().primary,
    shape: Shape = MaterialTheme.shapes().button,
    elevation: Dp = 0.dp
) = ButtonStyle(
    backgroundColor = backgroundColor,
    shape = shape,
    border = border,
    elevation = elevation,
    contentColor = contentColor
)

@Composable
fun TextButtonStyle(
    shape: Shape = MaterialTheme.shapes().button,
    contentColor: Color = MaterialTheme.colors().primary
) = ButtonStyle(
    backgroundColor = Color.Transparent,
    shape = shape,
    innerPadding = EdgeInsets(all = 8.dp),
    contentColor = contentColor
)

val ButtonStyleAmbient = staticAmbientOf<ButtonStyle>()

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.None,
    enabled: Boolean = true,
    style: ButtonStyle = ButtonStyleAmbient.currentOrElse { ContainedButtonStyle() },
    children: @Composable () -> Unit
) {
    /*androidx.ui.material.Button(
        modifier = modifier + drawOpacity(opacity = if (enabled) 1f else 0.5f),
        onClick = onClick,
        enabled = enabled,
        backgroundColor = style.backgroundColor,
        contentColor = style.contentColor,
        shape = style.shape,
        border = style.border,
        elevation = style.elevation,
        innerPadding = style.innerPadding,
        children = children
    )*/

    // todo remove once content gravity is fixed
    // Since we're adding layouts in between the clickable layer and the content, we need to
    // merge all descendants, or we'll get multiple nodes
    Semantics(container = true, mergeAllDescendants = true) {
        androidx.ui.material.Surface(
            shape = style.shape,
            color = style.backgroundColor,
            contentColor = style.contentColor,
            border = style.border,
            elevation = style.elevation,
            modifier = modifier
        ) {
            Ripple(bounded = true, enabled = enabled) {
                Clickable(onClick = onClick, enabled = enabled) {
                    Box(
                        LayoutSize.Min(64.dp, 36.dp),
                        paddingStart = style.innerPadding.left,
                        paddingTop = style.innerPadding.top,
                        paddingEnd = style.innerPadding.right,
                        paddingBottom = style.innerPadding.bottom,
                        gravity = Alignment.Center
                    ) {
                        Providers(
                            TextComposableStyleAmbient provides DefaultTextComposableStyle(
                                uppercase = true,
                                maxLines = 1
                            )
                        ) {
                            CurrentTextStyleProvider(
                                MaterialTheme.typography().button,
                                children = children
                            )
                        }
                    }
                }
            }
        }
    }
}