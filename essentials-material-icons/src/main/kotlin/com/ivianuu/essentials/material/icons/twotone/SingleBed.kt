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

val Icons.TwoTone.SingleBed: VectorAsset by lazyMaterialIcon {
    path(fillAlpha = 0.3f, strokeAlpha = 0.3f) {
        moveTo(6.0f, 12.0f)
        horizontalLineToRelative(12.0f)
        verticalLineToRelative(3.0f)
        horizontalLineToRelative(-12.0f)
        close()
    }
    path {
        moveTo(18.0f, 10.0f)
        verticalLineTo(7.0f)
        curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
        horizontalLineTo(8.0f)
        curveTo(6.9f, 5.0f, 6.0f, 5.9f, 6.0f, 7.0f)
        verticalLineToRelative(3.0f)
        curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
        verticalLineToRelative(5.0f)
        horizontalLineToRelative(1.33f)
        lineTo(6.0f, 19.0f)
        horizontalLineToRelative(1.0f)
        lineToRelative(0.67f, -2.0f)
        horizontalLineToRelative(8.67f)
        lineTo(17.0f, 19.0f)
        horizontalLineToRelative(1.0f)
        lineToRelative(0.67f, -2.0f)
        horizontalLineTo(20.0f)
        verticalLineToRelative(-5.0f)
        curveTo(20.0f, 10.9f, 19.1f, 10.0f, 18.0f, 10.0f)
        close()
        moveTo(13.0f, 7.0f)
        horizontalLineToRelative(3.0f)
        verticalLineToRelative(3.0f)
        horizontalLineToRelative(-3.0f)
        verticalLineTo(7.0f)
        close()
        moveTo(8.0f, 7.0f)
        horizontalLineToRelative(3.0f)
        verticalLineToRelative(3.0f)
        horizontalLineTo(8.0f)
        verticalLineTo(7.0f)
        close()
        moveTo(18.0f, 15.0f)
        horizontalLineTo(6.0f)
        verticalLineToRelative(-3.0f)
        horizontalLineToRelative(12.0f)
        verticalLineTo(15.0f)
        close()
    }
}
