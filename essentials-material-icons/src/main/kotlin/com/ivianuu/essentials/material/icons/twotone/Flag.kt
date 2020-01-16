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

val Icons.TwoTone.Flag: VectorAsset by lazyMaterialIcon {
    path(fillAlpha = 0.3f) {
        moveTo(12.36f, 6.0f)
        horizontalLineTo(7.0f)
        verticalLineToRelative(6.0f)
        horizontalLineToRelative(7.24f)
        lineToRelative(0.4f, 2.0f)
        horizontalLineTo(18.0f)
        verticalLineTo(8.0f)
        horizontalLineToRelative(-5.24f)
        close()
    }
    path {
        moveTo(14.4f, 6.0f)
        lineTo(14.0f, 4.0f)
        lineTo(5.0f, 4.0f)
        verticalLineToRelative(17.0f)
        horizontalLineToRelative(2.0f)
        verticalLineToRelative(-7.0f)
        horizontalLineToRelative(5.6f)
        lineToRelative(0.4f, 2.0f)
        horizontalLineToRelative(7.0f)
        lineTo(20.0f, 6.0f)
        horizontalLineToRelative(-5.6f)
        close()
        moveTo(18.0f, 14.0f)
        horizontalLineToRelative(-3.36f)
        lineToRelative(-0.4f, -2.0f)
        lineTo(7.0f, 12.0f)
        lineTo(7.0f, 6.0f)
        horizontalLineToRelative(5.36f)
        lineToRelative(0.4f, 2.0f)
        lineTo(18.0f, 8.0f)
        verticalLineToRelative(6.0f)
        close()
    }
}
