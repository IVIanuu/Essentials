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

import com.ivianuu.essentials.composehelpers.vector.VectorAsset
import com.ivianuu.essentials.material.icons.path
import com.ivianuu.essentials.material.icons.group
import com.ivianuu.essentials.material.icons.Icons

import com.ivianuu.essentials.material.icons.lazyMaterialIcon

val Icons.Filled.BarChart: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(5.0f, 9.2f)
        horizontalLineToRelative(3.0f)
        lineTo(8.0f, 19.0f)
        lineTo(5.0f, 19.0f)
        close()
        moveTo(10.6f, 5.0f)
        horizontalLineToRelative(2.8f)
        verticalLineToRelative(14.0f)
        horizontalLineToRelative(-2.8f)
        close()
        moveTo(16.2f, 13.0f)
        lineTo(19.0f, 13.0f)
        verticalLineToRelative(6.0f)
        horizontalLineToRelative(-2.8f)
        close()
    }
}
