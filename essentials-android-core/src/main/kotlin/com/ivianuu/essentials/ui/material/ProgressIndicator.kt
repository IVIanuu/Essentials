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
import androidx.compose.staticAmbientOf
import androidx.ui.core.Modifier
import androidx.ui.graphics.Color
import androidx.ui.material.MaterialTheme
import com.ivianuu.essentials.ui.core.currentOrElse

@Immutable
data class ProgressIndicatorStyle(
    val modifier: Modifier,
    val color: Color
)

val ProgressIndicatorStyleAmbient = staticAmbientOf<ProgressIndicatorStyle>()

@Composable
fun DefaultProgressIndicatorStyle(
    modifier: Modifier = Modifier.None,
    color: Color = MaterialTheme.colors().secondary
) = ProgressIndicatorStyle(modifier = modifier, color = color)

@Composable
fun LinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier.None,
    style: ProgressIndicatorStyle = ProgressIndicatorStyleAmbient.currentOrElse { DefaultProgressIndicatorStyle() }
) {
    androidx.ui.material.LinearProgressIndicator(
        progress = progress,
        modifier = style.modifier + modifier,
        color = style.color
    )
}

@Composable
fun LinearProgressIndicator(
    modifier: Modifier = Modifier.None,
    style: ProgressIndicatorStyle = ProgressIndicatorStyleAmbient.currentOrElse { DefaultProgressIndicatorStyle() }
) {
    androidx.ui.material.LinearProgressIndicator(
        modifier = style.modifier + modifier,
        color = style.color
    )
}

@Composable
fun CircularProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier.None,
    style: ProgressIndicatorStyle = ProgressIndicatorStyleAmbient.currentOrElse { DefaultProgressIndicatorStyle() }
) {
    androidx.ui.material.CircularProgressIndicator(
        progress = progress,
        modifier = style.modifier + modifier,
        color = style.color
    )
}

@Composable
fun CircularProgressIndicator(
    modifier: Modifier = Modifier.None,
    style: ProgressIndicatorStyle = ProgressIndicatorStyleAmbient.currentOrElse { DefaultProgressIndicatorStyle() }
) {
    androidx.ui.material.CircularProgressIndicator(
        modifier = style.modifier + modifier,
        color = style.color
    )
}
