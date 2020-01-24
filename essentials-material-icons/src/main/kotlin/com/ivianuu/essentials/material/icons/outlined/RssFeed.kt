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

val Icons.Outlined.RssFeed: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(6.18f, 17.82f)
        moveToRelative(-2.18f, 0.0f)
        arcToRelative(2.18f, 2.18f, 0.0f, true, true, 4.36f, 0.0f)
        arcToRelative(2.18f, 2.18f, 0.0f, true, true, -4.36f, 0.0f)
    }
    path {
        moveTo(4.0f, 4.44f)
        verticalLineToRelative(2.83f)
        curveToRelative(7.03f, 0.0f, 12.73f, 5.7f, 12.73f, 12.73f)
        horizontalLineToRelative(2.83f)
        curveToRelative(0.0f, -8.59f, -6.97f, -15.56f, -15.56f, -15.56f)
        close()
        moveTo(4.0f, 10.1f)
        verticalLineToRelative(2.83f)
        curveToRelative(3.9f, 0.0f, 7.07f, 3.17f, 7.07f, 7.07f)
        horizontalLineToRelative(2.83f)
        curveToRelative(0.0f, -5.47f, -4.43f, -9.9f, -9.9f, -9.9f)
        close()
    }
}
