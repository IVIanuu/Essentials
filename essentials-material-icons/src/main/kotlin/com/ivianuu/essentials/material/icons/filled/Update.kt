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

val Icons.Filled.Update: VectorAsset by lazyMaterialIcon {
    group {
        path {
            moveTo(21.0f, 10.12f)
            horizontalLineToRelative(-6.78f)
            lineToRelative(2.74f, -2.82f)
            curveToRelative(-2.73f, -2.7f, -7.15f, -2.8f, -9.88f, -0.1f)
            curveToRelative(-2.73f, 2.71f, -2.73f, 7.08f, 0.0f, 9.79f)
            curveToRelative(2.73f, 2.71f, 7.15f, 2.71f, 9.88f, 0.0f)
            curveTo(18.32f, 15.65f, 19.0f, 14.08f, 19.0f, 12.1f)
            horizontalLineToRelative(2.0f)
            curveToRelative(0.0f, 1.98f, -0.88f, 4.55f, -2.64f, 6.29f)
            curveToRelative(-3.51f, 3.48f, -9.21f, 3.48f, -12.72f, 0.0f)
            curveToRelative(-3.5f, -3.47f, -3.53f, -9.11f, -0.02f, -12.58f)
            curveToRelative(3.51f, -3.47f, 9.14f, -3.47f, 12.65f, 0.0f)
            lineTo(21.0f, 3.0f)
            verticalLineToRelative(7.12f)
            close()
            moveTo(12.5f, 8.0f)
            verticalLineToRelative(4.25f)
            lineToRelative(3.5f, 2.08f)
            lineToRelative(-0.72f, 1.21f)
            lineTo(11.0f, 13.0f)
            verticalLineTo(8.0f)
            horizontalLineToRelative(1.5f)
            close()
        }
    }
}
