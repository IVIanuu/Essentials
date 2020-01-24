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

val Icons.Filled.Loupe: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(13.0f, 7.0f)
        horizontalLineToRelative(-2.0f)
        verticalLineToRelative(4.0f)
        lineTo(7.0f, 11.0f)
        verticalLineToRelative(2.0f)
        horizontalLineToRelative(4.0f)
        verticalLineToRelative(4.0f)
        horizontalLineToRelative(2.0f)
        verticalLineToRelative(-4.0f)
        horizontalLineToRelative(4.0f)
        verticalLineToRelative(-2.0f)
        horizontalLineToRelative(-4.0f)
        lineTo(13.0f, 7.0f)
        close()
        moveTo(12.0f, 2.0f)
        curveTo(6.49f, 2.0f, 2.0f, 6.49f, 2.0f, 12.0f)
        reflectiveCurveToRelative(4.49f, 10.0f, 10.0f, 10.0f)
        horizontalLineToRelative(8.0f)
        curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
        verticalLineToRelative(-8.0f)
        curveToRelative(0.0f, -5.51f, -4.49f, -10.0f, -10.0f, -10.0f)
        close()
        moveTo(12.0f, 20.0f)
        curveToRelative(-4.41f, 0.0f, -8.0f, -3.59f, -8.0f, -8.0f)
        reflectiveCurveToRelative(3.59f, -8.0f, 8.0f, -8.0f)
        reflectiveCurveToRelative(8.0f, 3.59f, 8.0f, 8.0f)
        reflectiveCurveToRelative(-3.59f, 8.0f, -8.0f, 8.0f)
        close()
    }
}
