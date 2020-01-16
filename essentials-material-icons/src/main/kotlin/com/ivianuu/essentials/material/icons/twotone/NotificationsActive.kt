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

val Icons.TwoTone.NotificationsActive: VectorAsset by lazyMaterialIcon {
    path(fillAlpha = 0.3f) {
        moveTo(12.0f, 6.5f)
        curveToRelative(-2.49f, 0.0f, -4.0f, 2.02f, -4.0f, 4.5f)
        verticalLineToRelative(6.0f)
        horizontalLineToRelative(8.0f)
        verticalLineToRelative(-6.0f)
        curveToRelative(0.0f, -2.48f, -1.51f, -4.5f, -4.0f, -4.5f)
        close()
    }
    path {
        moveTo(12.0f, 22.0f)
        curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
        horizontalLineToRelative(-4.0f)
        curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
        close()
        moveTo(18.0f, 11.0f)
        curveToRelative(0.0f, -3.07f, -1.63f, -5.64f, -4.5f, -6.32f)
        lineTo(13.5f, 4.0f)
        curveToRelative(0.0f, -0.83f, -0.67f, -1.5f, -1.5f, -1.5f)
        reflectiveCurveToRelative(-1.5f, 0.67f, -1.5f, 1.5f)
        verticalLineToRelative(0.68f)
        curveTo(7.64f, 5.36f, 6.0f, 7.92f, 6.0f, 11.0f)
        verticalLineToRelative(5.0f)
        lineToRelative(-2.0f, 2.0f)
        verticalLineToRelative(1.0f)
        horizontalLineToRelative(16.0f)
        verticalLineToRelative(-1.0f)
        lineToRelative(-2.0f, -2.0f)
        verticalLineToRelative(-5.0f)
        close()
        moveTo(16.0f, 17.0f)
        lineTo(8.0f, 17.0f)
        verticalLineToRelative(-6.0f)
        curveToRelative(0.0f, -2.48f, 1.51f, -4.5f, 4.0f, -4.5f)
        reflectiveCurveToRelative(4.0f, 2.02f, 4.0f, 4.5f)
        verticalLineToRelative(6.0f)
        close()
        moveTo(7.58f, 4.08f)
        lineTo(6.15f, 2.65f)
        curveTo(3.75f, 4.48f, 2.17f, 7.3f, 2.03f, 10.5f)
        horizontalLineToRelative(2.0f)
        curveToRelative(0.15f, -2.65f, 1.51f, -4.97f, 3.55f, -6.42f)
        close()
        moveTo(19.97f, 10.5f)
        horizontalLineToRelative(2.0f)
        curveToRelative(-0.15f, -3.2f, -1.73f, -6.02f, -4.12f, -7.85f)
        lineToRelative(-1.42f, 1.43f)
        curveToRelative(2.02f, 1.45f, 3.39f, 3.77f, 3.54f, 6.42f)
        close()
    }
}
