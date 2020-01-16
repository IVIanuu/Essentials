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

package com.ivianuu.essentials.material.icons.outlined

import com.ivianuu.essentials.composehelpers.vector.VectorAsset
import com.ivianuu.essentials.material.icons.path
import com.ivianuu.essentials.material.icons.group
import com.ivianuu.essentials.material.icons.Icons

import com.ivianuu.essentials.material.icons.lazyMaterialIcon

val Icons.Outlined.Help: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(12.0f, 2.0f)
        curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
        reflectiveCurveToRelative(4.48f, 10.0f, 10.0f, 10.0f)
        reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
        reflectiveCurveTo(17.52f, 2.0f, 12.0f, 2.0f)
        close()
        moveTo(13.0f, 19.0f)
        horizontalLineToRelative(-2.0f)
        verticalLineToRelative(-2.0f)
        horizontalLineToRelative(2.0f)
        verticalLineToRelative(2.0f)
        close()
        moveTo(15.07f, 11.25f)
        lineToRelative(-0.9f, 0.92f)
        curveTo(13.45f, 12.9f, 13.0f, 13.5f, 13.0f, 15.0f)
        horizontalLineToRelative(-2.0f)
        verticalLineToRelative(-0.5f)
        curveToRelative(0.0f, -1.1f, 0.45f, -2.1f, 1.17f, -2.83f)
        lineToRelative(1.24f, -1.26f)
        curveToRelative(0.37f, -0.36f, 0.59f, -0.86f, 0.59f, -1.41f)
        curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
        reflectiveCurveToRelative(-2.0f, 0.9f, -2.0f, 2.0f)
        lineTo(8.0f, 9.0f)
        curveToRelative(0.0f, -2.21f, 1.79f, -4.0f, 4.0f, -4.0f)
        reflectiveCurveToRelative(4.0f, 1.79f, 4.0f, 4.0f)
        curveToRelative(0.0f, 0.88f, -0.36f, 1.68f, -0.93f, 2.25f)
        close()
    }
}
