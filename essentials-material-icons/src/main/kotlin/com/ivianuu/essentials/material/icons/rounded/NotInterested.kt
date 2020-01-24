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

val Icons.Rounded.NotInterested: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(12.0f, 2.0f)
        curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
        reflectiveCurveToRelative(4.48f, 10.0f, 10.0f, 10.0f)
        reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
        reflectiveCurveTo(17.52f, 2.0f, 12.0f, 2.0f)
        close()
        moveTo(12.0f, 20.0f)
        curveToRelative(-4.42f, 0.0f, -8.0f, -3.58f, -8.0f, -8.0f)
        curveToRelative(0.0f, -1.85f, 0.63f, -3.55f, 1.69f, -4.9f)
        lineTo(16.9f, 18.31f)
        curveTo(15.55f, 19.37f, 13.85f, 20.0f, 12.0f, 20.0f)
        close()
        moveTo(18.31f, 16.9f)
        lineTo(7.1f, 5.69f)
        curveTo(8.45f, 4.63f, 10.15f, 4.0f, 12.0f, 4.0f)
        curveToRelative(4.42f, 0.0f, 8.0f, 3.58f, 8.0f, 8.0f)
        curveToRelative(0.0f, 1.85f, -0.63f, 3.55f, -1.69f, 4.9f)
        close()
    }
}
