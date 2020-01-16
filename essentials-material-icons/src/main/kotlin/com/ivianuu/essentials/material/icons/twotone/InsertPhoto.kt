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

import com.ivianuu.essentials.composehelpers.vector.VectorAsset
import com.ivianuu.essentials.material.icons.path
import com.ivianuu.essentials.material.icons.group
import com.ivianuu.essentials.material.icons.Icons

import com.ivianuu.essentials.material.icons.lazyMaterialIcon

val Icons.TwoTone.InsertPhoto: VectorAsset by lazyMaterialIcon {
    path(fillAlpha = 0.3f) {
        moveTo(5.0f, 19.0f)
        horizontalLineToRelative(14.0f)
        lineTo(19.0f, 5.0f)
        lineTo(5.0f, 5.0f)
        verticalLineToRelative(14.0f)
        close()
        moveTo(9.0f, 13.14f)
        lineToRelative(2.14f, 2.58f)
        lineToRelative(3.0f, -3.87f)
        lineTo(18.0f, 17.0f)
        lineTo(6.0f, 17.0f)
        lineToRelative(3.0f, -3.86f)
        close()
    }
    path {
        moveTo(3.0f, 5.0f)
        verticalLineToRelative(14.0f)
        curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
        horizontalLineToRelative(14.0f)
        curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
        lineTo(21.0f, 5.0f)
        curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
        lineTo(5.0f, 3.0f)
        curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
        close()
        moveTo(19.0f, 19.0f)
        lineTo(5.0f, 19.0f)
        lineTo(5.0f, 5.0f)
        horizontalLineToRelative(14.0f)
        verticalLineToRelative(14.0f)
        close()
        moveTo(14.14f, 11.86f)
        lineToRelative(-3.0f, 3.86f)
        lineTo(9.0f, 13.14f)
        lineTo(6.0f, 17.0f)
        horizontalLineToRelative(12.0f)
        close()
    }
}
