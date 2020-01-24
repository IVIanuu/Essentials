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

val Icons.Filled.Web: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(20.0f, 4.0f)
        lineTo(4.0f, 4.0f)
        curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f)
        lineTo(2.0f, 18.0f)
        curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
        horizontalLineToRelative(16.0f)
        curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
        lineTo(22.0f, 6.0f)
        curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
        close()
        moveTo(15.0f, 18.0f)
        lineTo(4.0f, 18.0f)
        verticalLineToRelative(-4.0f)
        horizontalLineToRelative(11.0f)
        verticalLineToRelative(4.0f)
        close()
        moveTo(15.0f, 13.0f)
        lineTo(4.0f, 13.0f)
        lineTo(4.0f, 9.0f)
        horizontalLineToRelative(11.0f)
        verticalLineToRelative(4.0f)
        close()
        moveTo(20.0f, 18.0f)
        horizontalLineToRelative(-4.0f)
        lineTo(16.0f, 9.0f)
        horizontalLineToRelative(4.0f)
        verticalLineToRelative(9.0f)
        close()
    }
}
