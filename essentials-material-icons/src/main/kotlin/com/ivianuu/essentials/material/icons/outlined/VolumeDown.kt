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

package com.ivianuu.essentials.material.icons.outlined

import androidx.ui.graphics.vector.VectorAsset
import com.ivianuu.essentials.material.icons.Icons
import com.ivianuu.essentials.material.icons.lazyMaterialIcon
import com.ivianuu.essentials.material.icons.path

val Icons.Outlined.VolumeDown: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(16.0f, 7.97f)
        verticalLineToRelative(8.05f)
        curveToRelative(1.48f, -0.73f, 2.5f, -2.25f, 2.5f, -4.02f)
        curveToRelative(0.0f, -1.77f, -1.02f, -3.29f, -2.5f, -4.03f)
        close()
        moveTo(5.0f, 9.0f)
        verticalLineToRelative(6.0f)
        horizontalLineToRelative(4.0f)
        lineToRelative(5.0f, 5.0f)
        lineTo(14.0f, 4.0f)
        lineTo(9.0f, 9.0f)
        lineTo(5.0f, 9.0f)
        close()
        moveTo(12.0f, 8.83f)
        verticalLineToRelative(6.34f)
        lineTo(9.83f, 13.0f)
        lineTo(7.0f, 13.0f)
        verticalLineToRelative(-2.0f)
        horizontalLineToRelative(2.83f)
        lineTo(12.0f, 8.83f)
        close()
    }
}
