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

val Icons.Rounded.AmpStories: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(16.0f, 4.0f)
        horizontalLineTo(8.0f)
        curveTo(7.45f, 4.0f, 7.0f, 4.45f, 7.0f, 5.0f)
        verticalLineToRelative(13.0f)
        curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
        horizontalLineToRelative(8.0f)
        curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
        verticalLineTo(5.0f)
        curveTo(17.0f, 4.45f, 16.55f, 4.0f, 16.0f, 4.0f)
        close()
    }
    path {
        moveTo(4.0f, 6.0f)
        curveTo(3.45f, 6.0f, 3.0f, 6.45f, 3.0f, 7.0f)
        verticalLineToRelative(9.0f)
        curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
        reflectiveCurveToRelative(1.0f, -0.45f, 1.0f, -1.0f)
        verticalLineTo(7.0f)
        curveTo(5.0f, 6.45f, 4.55f, 6.0f, 4.0f, 6.0f)
        close()
    }
    path {
        moveTo(20.0f, 6.0f)
        curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
        verticalLineToRelative(9.0f)
        curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
        reflectiveCurveToRelative(1.0f, -0.45f, 1.0f, -1.0f)
        verticalLineTo(7.0f)
        curveTo(21.0f, 6.45f, 20.55f, 6.0f, 20.0f, 6.0f)
        close()
    }
}
