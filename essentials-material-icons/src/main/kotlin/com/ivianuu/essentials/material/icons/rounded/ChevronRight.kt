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

val Icons.Rounded.ChevronRight: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(9.29f, 6.71f)
        curveToRelative(-0.39f, 0.39f, -0.39f, 1.02f, 0.0f, 1.41f)
        lineTo(13.17f, 12.0f)
        lineToRelative(-3.88f, 3.88f)
        curveToRelative(-0.39f, 0.39f, -0.39f, 1.02f, 0.0f, 1.41f)
        curveToRelative(0.39f, 0.39f, 1.02f, 0.39f, 1.41f, 0.0f)
        lineToRelative(4.59f, -4.59f)
        curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f)
        lineTo(10.7f, 6.7f)
        curveToRelative(-0.38f, -0.38f, -1.02f, -0.38f, -1.41f, 0.01f)
        close()
    }
}
