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

val Icons.Rounded.NearMe: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(18.75f, 3.94f)
        lineTo(4.07f, 10.08f)
        curveToRelative(-0.83f, 0.35f, -0.81f, 1.53f, 0.02f, 1.85f)
        lineTo(9.43f, 14.0f)
        curveToRelative(0.26f, 0.1f, 0.47f, 0.31f, 0.57f, 0.57f)
        lineToRelative(2.06f, 5.33f)
        curveToRelative(0.32f, 0.84f, 1.51f, 0.86f, 1.86f, 0.03f)
        lineToRelative(6.15f, -14.67f)
        curveToRelative(0.33f, -0.83f, -0.5f, -1.66f, -1.32f, -1.32f)
        close()
    }
}
