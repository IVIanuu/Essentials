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

val Icons.Filled.TableChart: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(10.0f, 10.02f)
        horizontalLineToRelative(5.0f)
        lineTo(15.0f, 21.0f)
        horizontalLineToRelative(-5.0f)
        close()
        moveTo(17.0f, 21.0f)
        horizontalLineToRelative(3.0f)
        curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
        verticalLineToRelative(-9.0f)
        horizontalLineToRelative(-5.0f)
        verticalLineToRelative(11.0f)
        close()
        moveTo(20.0f, 3.0f)
        lineTo(5.0f, 3.0f)
        curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
        verticalLineToRelative(3.0f)
        horizontalLineToRelative(19.0f)
        lineTo(22.0f, 5.0f)
        curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
        close()
        moveTo(3.0f, 19.0f)
        curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
        horizontalLineToRelative(3.0f)
        lineTo(8.0f, 10.0f)
        lineTo(3.0f, 10.0f)
        verticalLineToRelative(9.0f)
        close()
    }
}
