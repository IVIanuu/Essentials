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

import com.ivianuu.essentials.composehelpers.vector.VectorAsset
import com.ivianuu.essentials.material.icons.path
import com.ivianuu.essentials.material.icons.group
import com.ivianuu.essentials.material.icons.Icons

import com.ivianuu.essentials.material.icons.lazyMaterialIcon

val Icons.Rounded.SkipPrevious: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(7.0f, 6.0f)
        curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
        verticalLineToRelative(10.0f)
        curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
        reflectiveCurveToRelative(-1.0f, -0.45f, -1.0f, -1.0f)
        lineTo(6.0f, 7.0f)
        curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
        close()
        moveTo(10.66f, 12.82f)
        lineToRelative(5.77f, 4.07f)
        curveToRelative(0.66f, 0.47f, 1.58f, -0.01f, 1.58f, -0.82f)
        lineTo(18.01f, 7.93f)
        curveToRelative(0.0f, -0.81f, -0.91f, -1.28f, -1.58f, -0.82f)
        lineToRelative(-5.77f, 4.07f)
        curveToRelative(-0.57f, 0.4f, -0.57f, 1.24f, 0.0f, 1.64f)
        close()
    }
}
