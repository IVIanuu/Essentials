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
import androidx.compose.Immutable
import androidx.compose.ambient
import androidx.ui.core.Size
import androidx.ui.core.dp
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.DrawImage
import androidx.ui.graphics.Color
import androidx.ui.graphics.Image
import androidx.ui.layout.Container
import androidx.ui.material.ripple.Ripple
import com.ivianuu.essentials.ui.common.MergeProvider
import com.ivianuu.essentials.ui.common.Mergeable

@Composable
fun Icon(
    image: Image,
    style: IconStyle = currentIconStyle(),
    size: Size = style.size,
    color: Color? = style.color
) {
    Container(
        width = size.width,
        height = size.height
    ) {
        DrawImage(image = image, tint = color)
    }
}

@Immutable
data class IconStyle(
    val size: Size = Size(DefaultIconSize, DefaultIconSize),
    val color: Color? = null
) : Mergeable<IconStyle> {
    override fun merge(other: IconStyle): IconStyle = IconStyle(
        size = other.size,
        color = other.color ?: this.color
    )
}

private val DefaultIconSize = 24.dp

private val CurrentIconStyleAmbient = Ambient.of { IconStyle() }

@Composable
fun CurrentIconStyleProvider(
    value: IconStyle,
    children: @Composable() () -> Unit
) {
    CurrentIconStyleAmbient.MergeProvider(value = value, children = children)
}

@Composable
fun currentIconStyle(): IconStyle = ambient(CurrentIconStyleAmbient)

fun AvatarIconStyle() = IconStyle(size = Size(AvatarSize, AvatarSize), color = null)

private val AvatarSize = 40.dp

@Composable
fun IconButton(
    image: Image,
    onClick: (() -> Unit)? = null
) {
    IconButton(onClick = onClick) {
        Icon(image = image)
    }
}

@Composable
fun IconButton(
    onClick: (() -> Unit)? = null,
    icon: @Composable() () -> Unit
) {
    Ripple(
        bounded = false,
        enabled = onClick != null
    ) {
        Clickable(onClick = onClick) {
            Container {
                icon()
            }
        }
    }
}