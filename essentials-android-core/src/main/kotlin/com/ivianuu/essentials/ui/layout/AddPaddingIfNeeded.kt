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

package com.ivianuu.essentials.ui.layout

import androidx.compose.Composable
import androidx.ui.core.Layout
import androidx.ui.core.Modifier
import androidx.ui.layout.InnerPadding
import androidx.ui.unit.ipx
import androidx.ui.unit.max
import androidx.ui.unit.min

// todo remove

@Composable
fun AddPaddingIfNeededLayout(
    padding: InnerPadding,
    modifier: Modifier = Modifier,
    child: @Composable () -> Unit
) {
    Layout(children = child, modifier = modifier) { measureables, constraints, _ ->
        if (measureables.isEmpty()) return@Layout layout(0.ipx, 0.ipx) {}

        val placeable = measureables.single().measure(constraints.copy(
            minWidth = 0.ipx, minHeight = 0.ipx
        ))
        val width = max(
            constraints.minWidth,
            placeable.width + padding.start.toIntPx() + padding.end.toIntPx()
        )
        val height = max(
            constraints.minHeight,
            placeable.height + padding.top.toIntPx() + padding.bottom.toIntPx()
        )

        layout(width, height) {
            placeable.place(
                x = min(width / 2, padding.start.toIntPx()),
                y = min(height / 2, padding.top.toIntPx())
            )
        }
    }
}
