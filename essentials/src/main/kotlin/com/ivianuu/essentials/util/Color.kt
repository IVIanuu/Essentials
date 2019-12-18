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

package com.ivianuu.essentials.util

import androidx.ui.graphics.Color
import androidx.ui.graphics.toArgb

val Color.isDark: Boolean
    get() = !isLight

val Color.isLight: Boolean
    get() {
        val darkness =
            1 - (0.299 * red +
                    0.587 * green +
                    0.114 * blue) / 255
        return darkness < 0.4
    }

fun Color.shifted(by: Float): Color {
    if (by == 1f) return this
    val alpha = (alpha * 255).toInt()
    val hsv = FloatArray(3)
    android.graphics.Color.colorToHSV(toArgb(), hsv)
    hsv[2] *= by
    return Color((alpha shl 24) + (0x00ffffff and android.graphics.Color.HSVToColor(hsv)))
}

fun Color.darken(): Color = shifted(0.9f)

fun Color.lighten(): Color = shifted(1.1f)

fun Color.inverted(): Color = Color(
    alpha = alpha,
    red = 255 - red,
    green = 255 - green,
    blue = 255 - blue
)