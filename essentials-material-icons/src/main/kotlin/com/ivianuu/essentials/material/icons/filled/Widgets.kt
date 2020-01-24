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

val Icons.Filled.Widgets: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(13.0f, 13.0f)
        verticalLineToRelative(8.0f)
        horizontalLineToRelative(8.0f)
        verticalLineToRelative(-8.0f)
        horizontalLineToRelative(-8.0f)
        close()
        moveTo(3.0f, 21.0f)
        horizontalLineToRelative(8.0f)
        verticalLineToRelative(-8.0f)
        lineTo(3.0f, 13.0f)
        verticalLineToRelative(8.0f)
        close()
        moveTo(3.0f, 3.0f)
        verticalLineToRelative(8.0f)
        horizontalLineToRelative(8.0f)
        lineTo(11.0f, 3.0f)
        lineTo(3.0f, 3.0f)
        close()
        moveTo(16.66f, 1.69f)
        lineTo(11.0f, 7.34f)
        lineTo(16.66f, 13.0f)
        lineToRelative(5.66f, -5.66f)
        lineToRelative(-5.66f, -5.65f)
        close()
    }
}
