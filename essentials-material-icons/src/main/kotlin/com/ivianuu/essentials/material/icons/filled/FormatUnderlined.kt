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

import com.ivianuu.essentials.composehelpers.vector.VectorAsset
import com.ivianuu.essentials.material.icons.path
import com.ivianuu.essentials.material.icons.group
import com.ivianuu.essentials.material.icons.Icons

import com.ivianuu.essentials.material.icons.lazyMaterialIcon

val Icons.Filled.FormatUnderlined: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(12.0f, 17.0f)
        curveToRelative(3.31f, 0.0f, 6.0f, -2.69f, 6.0f, -6.0f)
        lineTo(18.0f, 3.0f)
        horizontalLineToRelative(-2.5f)
        verticalLineToRelative(8.0f)
        curveToRelative(0.0f, 1.93f, -1.57f, 3.5f, -3.5f, 3.5f)
        reflectiveCurveTo(8.5f, 12.93f, 8.5f, 11.0f)
        lineTo(8.5f, 3.0f)
        lineTo(6.0f, 3.0f)
        verticalLineToRelative(8.0f)
        curveToRelative(0.0f, 3.31f, 2.69f, 6.0f, 6.0f, 6.0f)
        close()
        moveTo(5.0f, 19.0f)
        verticalLineToRelative(2.0f)
        horizontalLineToRelative(14.0f)
        verticalLineToRelative(-2.0f)
        lineTo(5.0f, 19.0f)
        close()
    }
}
