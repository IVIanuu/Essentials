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

package com.ivianuu.essentials.material.icons.rounded

import androidx.ui.graphics.vector.VectorAsset
import com.ivianuu.essentials.material.icons.Icons
import com.ivianuu.essentials.material.icons.lazyMaterialIcon
import com.ivianuu.essentials.material.icons.path

val Icons.Rounded.ControlPoint: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(12.0f, 7.0f)
        curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
        verticalLineToRelative(3.0f)
        lineTo(8.0f, 11.0f)
        curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
        reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
        horizontalLineToRelative(3.0f)
        verticalLineToRelative(3.0f)
        curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
        reflectiveCurveToRelative(1.0f, -0.45f, 1.0f, -1.0f)
        verticalLineToRelative(-3.0f)
        horizontalLineToRelative(3.0f)
        curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
        reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
        horizontalLineToRelative(-3.0f)
        lineTo(13.0f, 8.0f)
        curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
        close()
        moveTo(12.0f, 2.0f)
        curveTo(6.49f, 2.0f, 2.0f, 6.49f, 2.0f, 12.0f)
        reflectiveCurveToRelative(4.49f, 10.0f, 10.0f, 10.0f)
        reflectiveCurveToRelative(10.0f, -4.49f, 10.0f, -10.0f)
        reflectiveCurveTo(17.51f, 2.0f, 12.0f, 2.0f)
        close()
        moveTo(12.0f, 20.0f)
        curveToRelative(-4.41f, 0.0f, -8.0f, -3.59f, -8.0f, -8.0f)
        reflectiveCurveToRelative(3.59f, -8.0f, 8.0f, -8.0f)
        reflectiveCurveToRelative(8.0f, 3.59f, 8.0f, 8.0f)
        reflectiveCurveToRelative(-3.59f, 8.0f, -8.0f, 8.0f)
        close()
    }
}
