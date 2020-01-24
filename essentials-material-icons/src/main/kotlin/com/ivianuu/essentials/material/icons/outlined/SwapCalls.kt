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

import androidx.ui.graphics.vector.VectorAsset
import com.ivianuu.essentials.material.icons.Icons
import com.ivianuu.essentials.material.icons.lazyMaterialIcon
import com.ivianuu.essentials.material.icons.path

val Icons.Outlined.SwapCalls: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(18.0f, 4.0f)
        lineToRelative(-4.0f, 4.0f)
        horizontalLineToRelative(3.0f)
        verticalLineToRelative(7.0f)
        curveToRelative(0.0f, 1.1f, -0.9f, 2.0f, -2.0f, 2.0f)
        reflectiveCurveToRelative(-2.0f, -0.9f, -2.0f, -2.0f)
        verticalLineTo(8.0f)
        curveToRelative(0.0f, -2.21f, -1.79f, -4.0f, -4.0f, -4.0f)
        reflectiveCurveTo(5.0f, 5.79f, 5.0f, 8.0f)
        verticalLineToRelative(7.0f)
        horizontalLineTo(2.0f)
        lineToRelative(4.0f, 4.0f)
        lineToRelative(4.0f, -4.0f)
        horizontalLineTo(7.0f)
        verticalLineTo(8.0f)
        curveToRelative(0.0f, -1.1f, 0.9f, -2.0f, 2.0f, -2.0f)
        reflectiveCurveToRelative(2.0f, 0.9f, 2.0f, 2.0f)
        verticalLineToRelative(7.0f)
        curveToRelative(0.0f, 2.21f, 1.79f, 4.0f, 4.0f, 4.0f)
        reflectiveCurveToRelative(4.0f, -1.79f, 4.0f, -4.0f)
        verticalLineTo(8.0f)
        horizontalLineToRelative(3.0f)
        lineToRelative(-4.0f, -4.0f)
        close()
    }
}
