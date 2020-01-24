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

import androidx.ui.graphics.vector.VectorAsset
import com.ivianuu.essentials.material.icons.Icons
import com.ivianuu.essentials.material.icons.lazyMaterialIcon
import com.ivianuu.essentials.material.icons.path

val Icons.Rounded.PhoneEnabled: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(4.78f, 15.27f)
        lineToRelative(2.54f, -0.29f)
        curveToRelative(0.61f, -0.07f, 1.21f, 0.14f, 1.64f, 0.57f)
        lineToRelative(1.84f, 1.84f)
        curveToRelative(2.83f, -1.44f, 5.15f, -3.75f, 6.59f, -6.59f)
        lineToRelative(-1.85f, -1.85f)
        curveToRelative(-0.43f, -0.43f, -0.64f, -1.03f, -0.57f, -1.64f)
        lineToRelative(0.29f, -2.52f)
        curveToRelative(0.12f, -1.01f, 0.97f, -1.77f, 1.99f, -1.77f)
        horizontalLineToRelative(1.73f)
        curveToRelative(1.13f, 0.0f, 2.07f, 0.94f, 2.0f, 2.07f)
        curveToRelative(-0.53f, 8.54f, -7.36f, 15.36f, -15.89f, 15.89f)
        curveToRelative(-1.13f, 0.07f, -2.07f, -0.87f, -2.07f, -2.0f)
        verticalLineToRelative(-1.73f)
        curveTo(3.01f, 16.24f, 3.77f, 15.39f, 4.78f, 15.27f)
        close()
    }
}
