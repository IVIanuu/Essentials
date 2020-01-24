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

package com.ivianuu.essentials.material.icons.sharp

import androidx.ui.graphics.vector.VectorAsset
import com.ivianuu.essentials.material.icons.Icons
import com.ivianuu.essentials.material.icons.lazyMaterialIcon
import com.ivianuu.essentials.material.icons.path

val Icons.Sharp.Security: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(12.0f, 1.0f)
        lineTo(3.0f, 5.0f)
        verticalLineToRelative(6.0f)
        curveToRelative(0.0f, 5.55f, 3.84f, 10.74f, 9.0f, 12.0f)
        curveToRelative(5.16f, -1.26f, 9.0f, -6.45f, 9.0f, -12.0f)
        lineTo(21.0f, 5.0f)
        lineToRelative(-9.0f, -4.0f)
        close()
        moveTo(12.0f, 11.99f)
        horizontalLineToRelative(7.0f)
        curveToRelative(-0.53f, 4.12f, -3.28f, 7.79f, -7.0f, 8.94f)
        lineTo(12.0f, 12.0f)
        lineTo(5.0f, 12.0f)
        lineTo(5.0f, 6.3f)
        lineToRelative(7.0f, -3.11f)
        verticalLineToRelative(8.8f)
        close()
    }
}
