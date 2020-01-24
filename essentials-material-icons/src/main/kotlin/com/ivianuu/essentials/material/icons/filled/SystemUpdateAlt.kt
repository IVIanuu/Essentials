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

import androidx.ui.graphics.vector.VectorAsset
import com.ivianuu.essentials.material.icons.Icons
import com.ivianuu.essentials.material.icons.lazyMaterialIcon
import com.ivianuu.essentials.material.icons.path

val Icons.Filled.SystemUpdateAlt: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(12.0f, 16.5f)
        lineToRelative(4.0f, -4.0f)
        horizontalLineToRelative(-3.0f)
        verticalLineToRelative(-9.0f)
        horizontalLineToRelative(-2.0f)
        verticalLineToRelative(9.0f)
        lineTo(8.0f, 12.5f)
        lineToRelative(4.0f, 4.0f)
        close()
        moveTo(21.0f, 3.5f)
        horizontalLineToRelative(-6.0f)
        verticalLineToRelative(1.99f)
        horizontalLineToRelative(6.0f)
        verticalLineToRelative(14.03f)
        lineTo(3.0f, 19.52f)
        lineTo(3.0f, 5.49f)
        horizontalLineToRelative(6.0f)
        lineTo(9.0f, 3.5f)
        lineTo(3.0f, 3.5f)
        curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
        verticalLineToRelative(14.0f)
        curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
        horizontalLineToRelative(18.0f)
        curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
        verticalLineToRelative(-14.0f)
        curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
        close()
    }
}
