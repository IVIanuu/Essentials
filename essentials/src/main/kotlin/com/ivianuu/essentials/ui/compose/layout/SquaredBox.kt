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

package com.ivianuu.essentials.ui.compose.layout

import androidx.compose.Composable
import androidx.ui.core.PxPosition
import com.ivianuu.essentials.ui.compose.core.composable

@Composable
fun SquaredBox(
    fit: SquaredBoxFit,
    child: @Composable() () -> Unit
) = composable("SquaredBox") {
    SingleChildLayout(child = child) { measureable, constraints ->
        val size = when (fit) {
            SquaredBoxFit.MatchWidth -> constraints.maxWidth
            SquaredBoxFit.MatchHeight -> constraints.maxHeight
        }

        val placeable = if (measureable == null) {
            null
        } else {
            val childConstraints = constraints.copy(
                maxWidth = size,
                maxHeight = size
            )

            measureable.measure(childConstraints)
        }

        layout(width = size, height = size) {
            placeable?.place(PxPosition.Origin)
        }
    }
}

enum class SquaredBoxFit {
    MatchWidth, MatchHeight
}