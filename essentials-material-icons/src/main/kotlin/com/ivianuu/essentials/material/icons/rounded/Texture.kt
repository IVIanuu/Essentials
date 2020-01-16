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

package com.ivianuu.essentials.material.icons.rounded

import com.ivianuu.essentials.composehelpers.vector.VectorAsset
import com.ivianuu.essentials.material.icons.path
import com.ivianuu.essentials.material.icons.group
import com.ivianuu.essentials.material.icons.Icons

import com.ivianuu.essentials.material.icons.lazyMaterialIcon

val Icons.Rounded.Texture: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(19.58f, 3.08f)
        lineTo(3.15f, 19.51f)
        curveToRelative(0.09f, 0.34f, 0.27f, 0.65f, 0.51f, 0.9f)
        curveToRelative(0.25f, 0.24f, 0.56f, 0.42f, 0.9f, 0.51f)
        lineTo(21.0f, 4.49f)
        curveToRelative(-0.19f, -0.69f, -0.73f, -1.23f, -1.42f, -1.41f)
        close()
        moveTo(11.95f, 3.0f)
        lineToRelative(-8.88f, 8.88f)
        verticalLineToRelative(2.83f)
        lineTo(14.78f, 3.0f)
        horizontalLineToRelative(-2.83f)
        close()
        moveTo(5.07f, 3.0f)
        curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
        verticalLineToRelative(2.0f)
        lineToRelative(4.0f, -4.0f)
        horizontalLineToRelative(-2.0f)
        close()
        moveTo(19.07f, 21.0f)
        curveToRelative(0.55f, 0.0f, 1.05f, -0.22f, 1.41f, -0.59f)
        curveToRelative(0.37f, -0.36f, 0.59f, -0.86f, 0.59f, -1.41f)
        verticalLineToRelative(-2.0f)
        lineToRelative(-4.0f, 4.0f)
        horizontalLineToRelative(2.0f)
        close()
        moveTo(9.36f, 21.0f)
        horizontalLineToRelative(2.83f)
        lineToRelative(8.88f, -8.88f)
        lineTo(21.07f, 9.29f)
        lineTo(9.36f, 21.0f)
        close()
    }
}
