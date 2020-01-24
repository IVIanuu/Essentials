/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.essentials.material.icons.twotone

import androidx.ui.graphics.vector.VectorAsset
import com.ivianuu.essentials.material.icons.Icons
import com.ivianuu.essentials.material.icons.lazyMaterialIcon
import com.ivianuu.essentials.material.icons.path

val Icons.TwoTone.LocalDrink: VectorAsset by lazyMaterialIcon {
    path(fillAlpha = 0.3f) {
        moveTo(7.0f, 20.01f)
        lineTo(17.0f, 20.0f)
        lineToRelative(1.1f, -10.0f)
        lineTo(5.89f, 10.0f)
        lineTo(7.0f, 20.01f)
        close()
        moveTo(12.0f, 10.6f)
        reflectiveCurveToRelative(3.0f, 3.4f, 3.0f, 5.4f)
        curveToRelative(0.0f, 1.66f, -1.34f, 3.0f, -3.0f, 3.0f)
        reflectiveCurveToRelative(-3.0f, -1.34f, -3.0f, -3.0f)
        curveToRelative(0.0f, -2.0f, 3.0f, -5.4f, 3.0f, -5.4f)
        close()
    }
    path {
        moveTo(5.01f, 20.23f)
        curveTo(5.13f, 21.23f, 5.97f, 22.0f, 7.0f, 22.0f)
        horizontalLineToRelative(10.0f)
        curveToRelative(1.03f, 0.0f, 1.87f, -0.77f, 1.99f, -1.77f)
        lineTo(21.0f, 2.0f)
        lineTo(3.0f, 2.0f)
        lineToRelative(2.01f, 18.23f)
        close()
        moveTo(17.0f, 20.0f)
        lineToRelative(-10.0f, 0.01f)
        lineTo(5.89f, 10.0f)
        lineTo(18.1f, 10.0f)
        lineTo(17.0f, 20.0f)
        close()
        moveTo(18.76f, 4.0f)
        lineToRelative(-0.43f, 4.0f)
        lineTo(5.67f, 8.0f)
        lineToRelative(-0.44f, -4.0f)
        horizontalLineToRelative(13.53f)
        close()
        moveTo(12.0f, 19.0f)
        curveToRelative(1.66f, 0.0f, 3.0f, -1.34f, 3.0f, -3.0f)
        curveToRelative(0.0f, -2.0f, -3.0f, -5.4f, -3.0f, -5.4f)
        reflectiveCurveTo(9.0f, 14.0f, 9.0f, 16.0f)
        curveToRelative(0.0f, 1.66f, 1.34f, 3.0f, 3.0f, 3.0f)
        close()
        moveTo(12.0f, 13.91f)
        curveToRelative(0.59f, 0.91f, 1.0f, 1.73f, 1.0f, 2.09f)
        curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
        reflectiveCurveToRelative(-1.0f, -0.45f, -1.0f, -1.0f)
        curveToRelative(0.0f, -0.37f, 0.41f, -1.19f, 1.0f, -2.09f)
        close()
    }
}
