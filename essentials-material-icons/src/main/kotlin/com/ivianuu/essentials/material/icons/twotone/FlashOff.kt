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

val Icons.TwoTone.FlashOff: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(17.0f, 10.0f)
        horizontalLineToRelative(-3.61f)
        lineToRelative(2.28f, 2.28f)
        close()
        moveTo(17.0f, 2.0f)
        lineTo(7.0f, 2.0f)
        verticalLineToRelative(1.61f)
        lineToRelative(6.13f, 6.13f)
        close()
        moveTo(3.41f, 2.86f)
        lineTo(2.0f, 4.27f)
        lineToRelative(5.0f, 5.0f)
        lineTo(7.0f, 13.0f)
        horizontalLineToRelative(3.0f)
        verticalLineToRelative(9.0f)
        lineToRelative(3.58f, -6.15f)
        lineTo(17.73f, 20.0f)
        lineToRelative(1.41f, -1.41f)
        close()
    }
}
