/*
 * Copyright 2019 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.essentials.kotlin.compiler.compose.overload

import org.jetbrains.kotlin.name.FqName

val OverloadComposableMarkerAnnotation =
    FqName("com.ivianuu.essentials.ui.compose.overload.OverloadComposableMarker")
val OverloadNameAnnotation =
    FqName("com.ivianuu.essentials.ui.compose.overload.OverloadName")
val OverloadComposable =
    FqName("com.ivianuu.essentials.ui.compose.overload.OverloadComposable")