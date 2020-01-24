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

val Icons.Rounded.SubdirectoryArrowLeft: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(5.71f, 15.71f)
        lineToRelative(4.58f, 4.58f)
        curveToRelative(0.39f, 0.39f, 1.03f, 0.39f, 1.42f, 0.0f)
        curveToRelative(0.39f, -0.39f, 0.39f, -1.03f, 0.0f, -1.42f)
        lineTo(8.83f, 16.0f)
        horizontalLineTo(19.0f)
        curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
        verticalLineTo(5.0f)
        curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
        reflectiveCurveToRelative(-1.0f, 0.45f, -1.0f, 1.0f)
        verticalLineToRelative(9.0f)
        horizontalLineTo(8.83f)
        lineToRelative(2.88f, -2.87f)
        curveToRelative(0.39f, -0.39f, 0.39f, -1.03f, 0.0f, -1.42f)
        curveToRelative(-0.39f, -0.39f, -1.03f, -0.39f, -1.42f, 0.0f)
        lineToRelative(-4.58f, 4.58f)
        curveToRelative(-0.39f, 0.39f, -0.39f, 1.03f, 0.0f, 1.42f)
        close()
    }
}
