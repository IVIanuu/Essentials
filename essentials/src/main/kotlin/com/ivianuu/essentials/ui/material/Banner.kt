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
import androidx.compose.ambient
import androidx.ui.core.CurrentTextStyleProvider
import androidx.ui.core.IntPx
import androidx.ui.core.Layout
import androidx.ui.core.dp
import androidx.ui.core.ipx
import androidx.ui.layout.Container
import androidx.ui.layout.LayoutExpandedWidth
import androidx.ui.material.Divider
import androidx.ui.material.EmphasisAmbient
import androidx.ui.material.MaterialTheme
import com.ivianuu.essentials.ui.layout.Column
import com.ivianuu.essentials.ui.layout.CrossAxisAlignment
import com.ivianuu.essentials.ui.layout.HeightSpacer
import com.ivianuu.essentials.ui.layout.Row
import com.ivianuu.essentials.ui.layout.WidthSpacer

@Composable
fun Banner(
    leading: (@Composable() () -> Unit)? = null,
    content: @Composable() () -> Unit,
    actions: @Composable() () -> Unit,
    showBottomDivider: Boolean = true
) {
    val styledContent: @Composable() () -> Unit = {
        CurrentTextStyleProvider(value = MaterialTheme.typography().body2) {
            EmphasisProvider(emphasis = ambient(EmphasisAmbient).high, children = content)
        }
    }
    val styledLeading: (@Composable() () -> Unit)? = if (leading == null) null else ({
        EmphasisProvider(emphasis = ambient(EmphasisAmbient).high, children = leading)
    })

    Column {
        Container(
            modifier = LayoutExpandedWidth
        ) {
            Column {
                HeightSpacer(24.dp)

                Row(crossAxisAlignment = CrossAxisAlignment.Center) {
                    WidthSpacer(16.dp)

                    if (styledLeading != null) {
                        styledLeading()
                        WidthSpacer(16.dp)
                    }

                    styledContent()

                    WidthSpacer(16.dp)
                }

                HeightSpacer(20.dp)

                BannerActions(actions = actions)

                HeightSpacer(8.dp)
            }
        }

        if (showBottomDivider) {
            Divider(color = MaterialTheme.colors().onSurface.copy(alpha = 0.12f))
        }
    }
}

@Composable
private fun BannerActions(actions: @Composable() () -> Unit) {
    Layout(children = actions) { measureables, constraints ->
        var childConstraints = constraints.copy(
            maxWidth = constraints.maxWidth - (8.dp.toIntPx() * 2)
        )
        val placeables = measureables.map { measureable ->
            val placeable = measureable.measure(childConstraints)
            childConstraints = childConstraints.copy(
                maxWidth = childConstraints.maxWidth - placeable.width - 8.dp.toIntPx()
            )
            placeable
        }

        val height = placeables.maxBy { it.height.value }?.height ?: IntPx.Zero
        val width = constraints.maxWidth

        layout(width = width, height = height) {
            var offsetX =
                constraints.maxWidth - placeables.sumBy { it.width.value }.ipx - 8.dp.toIntPx() - (8.dp * (placeables.size - 1)).toIntPx()
            placeables.forEach { placeable ->
                val y = height / 2 - placeable.height / 2
                placeable.place(offsetX, y)
                offsetX += placeable.width + 8.dp.toIntPx()
            }
        }
    }
}