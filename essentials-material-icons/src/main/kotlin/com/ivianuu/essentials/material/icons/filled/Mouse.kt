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

import com.ivianuu.essentials.ui.vector.VectorAsset
import com.ivianuu.essentials.material.icons.Icons
import com.ivianuu.essentials.material.icons.lazyMaterialIcon
import com.ivianuu.essentials.material.icons.path

val Icons.Filled.Mouse: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(13.0f, 1.07f)
        lineTo(13.0f, 9.0f)
        horizontalLineToRelative(7.0f)
        curveToRelative(0.0f, -4.08f, -3.05f, -7.44f, -7.0f, -7.93f)
        close()
        moveTo(4.0f, 15.0f)
        curveToRelative(0.0f, 4.42f, 3.58f, 8.0f, 8.0f, 8.0f)
        reflectiveCurveToRelative(8.0f, -3.58f, 8.0f, -8.0f)
        verticalLineToRelative(-4.0f)
        lineTo(4.0f, 11.0f)
        verticalLineToRelative(4.0f)
        close()
        moveTo(11.0f, 1.07f)
        curveTo(7.05f, 1.56f, 4.0f, 4.92f, 4.0f, 9.0f)
        horizontalLineToRelative(7.0f)
        lineTo(11.0f, 1.07f)
        close()
    }
}
