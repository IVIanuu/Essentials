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

package com.ivianuu.essentials.material.icons.filled

import androidx.ui.graphics.vector.VectorAsset
import com.ivianuu.essentials.material.icons.Icons
import com.ivianuu.essentials.material.icons.lazyMaterialIcon
import com.ivianuu.essentials.material.icons.path

val Icons.Filled.Pageview: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(11.5f, 9.0f)
        curveTo(10.12f, 9.0f, 9.0f, 10.12f, 9.0f, 11.5f)
        reflectiveCurveToRelative(1.12f, 2.5f, 2.5f, 2.5f)
        reflectiveCurveToRelative(2.5f, -1.12f, 2.5f, -2.5f)
        reflectiveCurveTo(12.88f, 9.0f, 11.5f, 9.0f)
        close()
        moveTo(20.0f, 4.0f)
        lineTo(4.0f, 4.0f)
        curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
        verticalLineToRelative(12.0f)
        curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
        horizontalLineToRelative(16.0f)
        curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
        lineTo(22.0f, 6.0f)
        curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
        close()
        moveTo(16.79f, 18.21f)
        lineToRelative(-2.91f, -2.91f)
        curveToRelative(-0.69f, 0.44f, -1.51f, 0.7f, -2.39f, 0.7f)
        curveTo(9.01f, 16.0f, 7.0f, 13.99f, 7.0f, 11.5f)
        reflectiveCurveTo(9.01f, 7.0f, 11.5f, 7.0f)
        reflectiveCurveTo(16.0f, 9.01f, 16.0f, 11.5f)
        curveToRelative(0.0f, 0.88f, -0.26f, 1.69f, -0.7f, 2.39f)
        lineToRelative(2.91f, 2.9f)
        lineToRelative(-1.42f, 1.42f)
        close()
    }
}
