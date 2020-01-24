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

val Icons.TwoTone.PowerInput: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(2.0f, 9.0f)
        verticalLineToRelative(2.0f)
        horizontalLineToRelative(19.0f)
        lineTo(21.0f, 9.0f)
        lineTo(2.0f, 9.0f)
        close()
        moveTo(2.0f, 15.0f)
        horizontalLineToRelative(5.0f)
        verticalLineToRelative(-2.0f)
        lineTo(2.0f, 13.0f)
        verticalLineToRelative(2.0f)
        close()
        moveTo(9.0f, 15.0f)
        horizontalLineToRelative(5.0f)
        verticalLineToRelative(-2.0f)
        lineTo(9.0f, 13.0f)
        verticalLineToRelative(2.0f)
        close()
        moveTo(16.0f, 15.0f)
        horizontalLineToRelative(5.0f)
        verticalLineToRelative(-2.0f)
        horizontalLineToRelative(-5.0f)
        verticalLineToRelative(2.0f)
        close()
    }
}
