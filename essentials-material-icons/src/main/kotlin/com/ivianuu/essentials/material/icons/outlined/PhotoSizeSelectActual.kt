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

import com.ivianuu.essentials.composehelpers.vector.VectorAsset
import com.ivianuu.essentials.material.icons.path
import com.ivianuu.essentials.material.icons.group
import com.ivianuu.essentials.material.icons.Icons

import com.ivianuu.essentials.material.icons.lazyMaterialIcon

val Icons.Outlined.PhotoSizeSelectActual: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(21.0f, 3.0f)
        lineTo(3.0f, 3.0f)
        curveTo(2.0f, 3.0f, 1.0f, 4.0f, 1.0f, 5.0f)
        verticalLineToRelative(14.0f)
        curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
        horizontalLineToRelative(18.0f)
        curveToRelative(1.0f, 0.0f, 2.0f, -1.0f, 2.0f, -2.0f)
        lineTo(23.0f, 5.0f)
        curveToRelative(0.0f, -1.0f, -1.0f, -2.0f, -2.0f, -2.0f)
        close()
        moveTo(21.0f, 18.92f)
        curveToRelative(-0.02f, 0.03f, -0.06f, 0.06f, -0.08f, 0.08f)
        lineTo(3.0f, 19.0f)
        lineTo(3.0f, 5.08f)
        lineTo(3.08f, 5.0f)
        horizontalLineToRelative(17.83f)
        curveToRelative(0.03f, 0.02f, 0.06f, 0.06f, 0.08f, 0.08f)
        verticalLineToRelative(13.84f)
        close()
        moveTo(11.0f, 15.51f)
        lineTo(8.5f, 12.5f)
        lineTo(5.0f, 17.0f)
        horizontalLineToRelative(14.0f)
        lineToRelative(-4.5f, -6.0f)
        close()
    }
}
