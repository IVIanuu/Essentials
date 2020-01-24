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

package com.ivianuu.essentials.material.icons.sharp

import com.ivianuu.essentials.material.icons.Icons
import com.ivianuu.essentials.material.icons.lazyMaterialIcon
import com.ivianuu.essentials.material.icons.path
import androidx.ui.graphics.vector.VectorAsset

val Icons.Sharp.AirlineSeatIndividualSuite: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(7.0f, 13.0f)
        curveToRelative(1.65f, 0.0f, 3.0f, -1.35f, 3.0f, -3.0f)
        reflectiveCurveTo(8.65f, 7.0f, 7.0f, 7.0f)
        reflectiveCurveToRelative(-3.0f, 1.35f, -3.0f, 3.0f)
        reflectiveCurveToRelative(1.35f, 3.0f, 3.0f, 3.0f)
        close()
        moveTo(23.0f, 7.0f)
        lineTo(11.0f, 7.0f)
        verticalLineToRelative(7.0f)
        lineTo(3.0f, 14.0f)
        lineTo(3.0f, 7.0f)
        lineTo(1.0f, 7.0f)
        verticalLineToRelative(10.0f)
        horizontalLineToRelative(22.0f)
        lineTo(23.0f, 7.0f)
        close()
    }
}
