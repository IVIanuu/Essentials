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

val Icons.Sharp.Undo: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(12.5f, 8.0f)
        curveToRelative(-2.65f, 0.0f, -5.05f, 0.99f, -6.9f, 2.6f)
        lineTo(2.0f, 7.0f)
        verticalLineToRelative(9.0f)
        horizontalLineToRelative(9.0f)
        lineToRelative(-3.62f, -3.62f)
        curveToRelative(1.39f, -1.16f, 3.16f, -1.88f, 5.12f, -1.88f)
        curveToRelative(3.54f, 0.0f, 6.55f, 2.31f, 7.6f, 5.5f)
        lineToRelative(2.37f, -0.78f)
        curveTo(21.08f, 11.03f, 17.15f, 8.0f, 12.5f, 8.0f)
        close()
    }
}
