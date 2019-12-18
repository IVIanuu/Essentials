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

package com.ivianuu.essentials.ui.common

import androidx.compose.Composable
import androidx.compose.remember
import com.ivianuu.essentials.ui.core.Stable

@Composable
inline fun <T> ref(crossinline init: () -> T): Ref<T> = remember { Ref(init()) }

@Composable
inline fun <T, V1> refFor(v1: V1, crossinline init: () -> T): Ref<T> = remember(v1) { Ref(init()) }

@Composable
inline fun <T, V1, V2> refFor(
    v1: V1,
    v2: V2,
    crossinline init: () -> T
): Ref<T> = remember(v1, v2) { Ref(init()) }

@Composable
inline fun <T> refFor(vararg inputs: Any?, crossinline init: () -> T): Ref<T> = remember(*inputs) {
    Ref(init())
}

@Stable
data class Ref<T>(var value: T)