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

import com.ivianuu.essentials.material.icons.Icons
import com.ivianuu.essentials.material.icons.lazyMaterialIcon
import com.ivianuu.essentials.composehelpers.vector.VectorAsset
import com.ivianuu.essentials.material.icons.path
import com.ivianuu.essentials.material.icons.group

val Icons.Outlined.Add: VectorAsset by lazyMaterialIcon {
    path {
        moveTo(19.0f, 13.0f)
        horizontalLineToRelative(-6.0f)
        verticalLineToRelative(6.0f)
        horizontalLineToRelative(-2.0f)
        verticalLineToRelative(-6.0f)
        horizontalLineTo(5.0f)
        verticalLineToRelative(-2.0f)
        horizontalLineToRelative(6.0f)
        verticalLineTo(5.0f)
        horizontalLineToRelative(2.0f)
        verticalLineToRelative(6.0f)
        horizontalLineToRelative(6.0f)
        verticalLineToRelative(2.0f)
        close()
    }
}
