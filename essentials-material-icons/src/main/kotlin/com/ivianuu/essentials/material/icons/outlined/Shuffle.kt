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

import com.ivianuu.essentials.composehelpers.vector.VectorAsset
import com.ivianuu.essentials.material.icons.path
import com.ivianuu.essentials.material.icons.group
import com.ivianuu.essentials.material.icons.Icons

import com.ivianuu.essentials.material.icons.lazyMaterialIcon

val Icons.Outlined.Shuffle: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(10.59f, 9.17f)
        lineTo(5.41f, 4.0f)
        lineTo(4.0f, 5.41f)
        lineToRelative(5.17f, 5.17f)
        lineToRelative(1.42f, -1.41f)
        close()
        moveTo(14.5f, 4.0f)
        lineToRelative(2.04f, 2.04f)
        lineTo(4.0f, 18.59f)
        lineTo(5.41f, 20.0f)
        lineTo(17.96f, 7.46f)
        lineTo(20.0f, 9.5f)
        lineTo(20.0f, 4.0f)
        horizontalLineToRelative(-5.5f)
        close()
        moveTo(14.83f, 13.41f)
        lineToRelative(-1.41f, 1.41f)
        lineToRelative(3.13f, 3.13f)
        lineTo(14.5f, 20.0f)
        lineTo(20.0f, 20.0f)
        verticalLineToRelative(-5.5f)
        lineToRelative(-2.04f, 2.04f)
        lineToRelative(-3.13f, -3.13f)
        close()
    }
}
