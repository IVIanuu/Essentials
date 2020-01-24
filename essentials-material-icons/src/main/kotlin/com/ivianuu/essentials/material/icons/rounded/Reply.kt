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

import androidx.ui.graphics.vector.VectorAsset
import com.ivianuu.essentials.material.icons.Icons
import com.ivianuu.essentials.material.icons.lazyMaterialIcon
import com.ivianuu.essentials.material.icons.path

val Icons.Rounded.Reply: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(10.0f, 9.0f)
        verticalLineTo(7.41f)
        curveToRelative(0.0f, -0.89f, -1.08f, -1.34f, -1.71f, -0.71f)
        lineTo(3.7f, 11.29f)
        curveToRelative(-0.39f, 0.39f, -0.39f, 1.02f, 0.0f, 1.41f)
        lineToRelative(4.59f, 4.59f)
        curveToRelative(0.63f, 0.63f, 1.71f, 0.19f, 1.71f, -0.7f)
        verticalLineTo(14.9f)
        curveToRelative(5.0f, 0.0f, 8.5f, 1.6f, 11.0f, 5.1f)
        curveToRelative(-1.0f, -5.0f, -4.0f, -10.0f, -11.0f, -11.0f)
        close()
    }
}
