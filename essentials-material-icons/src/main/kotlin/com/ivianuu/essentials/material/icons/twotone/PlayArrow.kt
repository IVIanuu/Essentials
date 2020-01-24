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

import androidx.ui.graphics.vector.VectorAsset
import com.ivianuu.essentials.material.icons.Icons
import com.ivianuu.essentials.material.icons.lazyMaterialIcon
import com.ivianuu.essentials.material.icons.path

val Icons.TwoTone.PlayArrow: VectorAsset by lazyMaterialIcon {
    path(fillAlpha = 0.3f) {
        moveTo(10.0f, 8.64f)
        verticalLineToRelative(6.72f)
        lineTo(15.27f, 12.0f)
        close()
    }
    path {
        moveTo(8.0f, 19.0f)
        lineToRelative(11.0f, -7.0f)
        lineTo(8.0f, 5.0f)
        verticalLineToRelative(14.0f)
        close()
        moveTo(10.0f, 8.64f)
        lineTo(15.27f, 12.0f)
        lineTo(10.0f, 15.36f)
        lineTo(10.0f, 8.64f)
        close()
    }
}
