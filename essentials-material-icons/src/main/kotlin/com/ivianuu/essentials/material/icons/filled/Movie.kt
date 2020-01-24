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

val Icons.Filled.Movie: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(18.0f, 4.0f)
        lineToRelative(2.0f, 4.0f)
        horizontalLineToRelative(-3.0f)
        lineToRelative(-2.0f, -4.0f)
        horizontalLineToRelative(-2.0f)
        lineToRelative(2.0f, 4.0f)
        horizontalLineToRelative(-3.0f)
        lineToRelative(-2.0f, -4.0f)
        horizontalLineTo(8.0f)
        lineToRelative(2.0f, 4.0f)
        horizontalLineTo(7.0f)
        lineTo(5.0f, 4.0f)
        horizontalLineTo(4.0f)
        curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f)
        lineTo(2.0f, 18.0f)
        curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
        horizontalLineToRelative(16.0f)
        curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
        verticalLineTo(4.0f)
        horizontalLineToRelative(-4.0f)
        close()
    }
}
