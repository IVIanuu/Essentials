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

val Icons.TwoTone.Vibration: VectorAsset by lazyMaterialIcon {
    path(fillAlpha = 0.3f) {
        moveTo(8.0f, 5.0f)
        horizontalLineToRelative(8.0f)
        verticalLineToRelative(14.0f)
        horizontalLineTo(8.0f)
        close()
    }
    path {
        moveTo(19.0f, 7.0f)
        horizontalLineToRelative(2.0f)
        verticalLineToRelative(10.0f)
        horizontalLineToRelative(-2.0f)
        close()
        moveTo(22.0f, 9.0f)
        horizontalLineToRelative(2.0f)
        verticalLineToRelative(6.0f)
        horizontalLineToRelative(-2.0f)
        close()
        moveTo(0.0f, 9.0f)
        horizontalLineToRelative(2.0f)
        verticalLineToRelative(6.0f)
        lineTo(0.0f, 15.0f)
        close()
        moveTo(16.5f, 3.0f)
        horizontalLineToRelative(-9.0f)
        curveTo(6.67f, 3.0f, 6.0f, 3.67f, 6.0f, 4.5f)
        verticalLineToRelative(15.0f)
        curveToRelative(0.0f, 0.83f, 0.67f, 1.5f, 1.5f, 1.5f)
        horizontalLineToRelative(9.0f)
        curveToRelative(0.83f, 0.0f, 1.5f, -0.67f, 1.5f, -1.5f)
        verticalLineToRelative(-15.0f)
        curveToRelative(0.0f, -0.83f, -0.67f, -1.5f, -1.5f, -1.5f)
        close()
        moveTo(16.0f, 19.0f)
        lineTo(8.0f, 19.0f)
        lineTo(8.0f, 5.0f)
        horizontalLineToRelative(8.0f)
        verticalLineToRelative(14.0f)
        close()
        moveTo(3.0f, 7.0f)
        horizontalLineToRelative(2.0f)
        verticalLineToRelative(10.0f)
        lineTo(3.0f, 17.0f)
        close()
    }
}
