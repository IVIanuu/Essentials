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
import androidx.compose.State
import androidx.compose.state
import androidx.lifecycle.ViewModel
import com.ivianuu.essentials.ui.viewmodel.viewModel
import com.ivianuu.essentials.util.sourceLocation

@Composable
inline fun <T> retained(noinline init: () -> T): T = retained(
    key = sourceLocation(),
    init = init
)

@Composable
fun <T> retained(
    key: Any,
    init: () -> T
): T = retainedState(key, init).value

@Composable
inline fun <T> retainedState(
    noinline init: () -> T
): State<T> = retainedState(
    key = sourceLocation(),
    init = init
)

@Composable
fun <T> retainedState(
    key: Any,
    init: () -> T
): State<T> {
    val viewModel = viewModel<RetainedStateViewModel>()

    val state = state {
        if (viewModel.values.containsKey(key)) {
            viewModel.values[key] as T
        } else {
            init()
        }
    }

    onFinalDispose { viewModel.values -= key }

    viewModel.values[key] = state.value

    return state
}

@PublishedApi
internal class RetainedStateViewModel : ViewModel() {
    val values = mutableMapOf<Any, Any?>()
}