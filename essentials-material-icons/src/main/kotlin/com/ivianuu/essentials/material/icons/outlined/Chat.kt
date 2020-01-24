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

val Icons.Outlined.Chat: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(4.0f, 4.0f)
        horizontalLineToRelative(16.0f)
        verticalLineToRelative(12.0f)
        lineTo(5.17f, 16.0f)
        lineTo(4.0f, 17.17f)
        lineTo(4.0f, 4.0f)
        moveToRelative(0.0f, -2.0f)
        curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f)
        lineTo(2.0f, 22.0f)
        lineToRelative(4.0f, -4.0f)
        horizontalLineToRelative(14.0f)
        curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
        lineTo(22.0f, 4.0f)
        curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
        lineTo(4.0f, 2.0f)
        close()
        moveTo(6.0f, 14.0f)
        horizontalLineToRelative(8.0f)
        verticalLineToRelative(2.0f)
        lineTo(6.0f, 16.0f)
        verticalLineToRelative(-2.0f)
        close()
        moveTo(6.0f, 11.0f)
        horizontalLineToRelative(12.0f)
        verticalLineToRelative(2.0f)
        lineTo(6.0f, 13.0f)
        lineTo(6.0f, 9.0f)
        close()
        moveTo(6.0f, 8.0f)
        horizontalLineToRelative(12.0f)
        verticalLineToRelative(2.0f)
        lineTo(6.0f, 10.0f)
        lineTo(6.0f, 6.0f)
        close()
    }
}
