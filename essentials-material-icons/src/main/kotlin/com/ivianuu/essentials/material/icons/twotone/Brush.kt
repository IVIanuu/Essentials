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

val Icons.TwoTone.Brush: VectorAsset by lazyMaterialIcon {
    path(fillAlpha = 0.3f) {
        moveTo(8.0f, 17.0f)
        curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
        reflectiveCurveToRelative(-1.0f, 0.45f, -1.0f, 1.0f)
        curveToRelative(0.0f, 0.74f, -0.19f, 1.4f, -0.5f, 1.95f)
        curveToRelative(0.17f, 0.03f, 0.33f, 0.05f, 0.5f, 0.05f)
        curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
        close()
    }
    path {
        moveTo(11.75f, 15.0f)
        lineToRelative(8.96f, -8.96f)
        curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f)
        lineToRelative(-1.34f, -1.34f)
        curveToRelative(-0.2f, -0.2f, -0.45f, -0.29f, -0.7f, -0.29f)
        reflectiveCurveToRelative(-0.51f, 0.1f, -0.71f, 0.29f)
        lineTo(9.0f, 12.25f)
        lineTo(11.75f, 15.0f)
        close()
        moveTo(6.0f, 21.0f)
        curveToRelative(2.21f, 0.0f, 4.0f, -1.79f, 4.0f, -4.0f)
        curveToRelative(0.0f, -1.66f, -1.34f, -3.0f, -3.0f, -3.0f)
        reflectiveCurveToRelative(-3.0f, 1.34f, -3.0f, 3.0f)
        curveToRelative(0.0f, 1.31f, -1.16f, 2.0f, -2.0f, 2.0f)
        curveToRelative(0.92f, 1.22f, 2.49f, 2.0f, 4.0f, 2.0f)
        close()
        moveTo(6.0f, 17.0f)
        curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
        reflectiveCurveToRelative(1.0f, 0.45f, 1.0f, 1.0f)
        curveToRelative(0.0f, 1.1f, -0.9f, 2.0f, -2.0f, 2.0f)
        curveToRelative(-0.17f, 0.0f, -0.33f, -0.02f, -0.5f, -0.05f)
        curveToRelative(0.31f, -0.55f, 0.5f, -1.21f, 0.5f, -1.95f)
        close()
    }
}
