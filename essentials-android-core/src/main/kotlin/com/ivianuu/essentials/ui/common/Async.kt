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
import com.ivianuu.essentials.util.Async
import com.ivianuu.essentials.util.Fail
import com.ivianuu.essentials.util.Loading
import com.ivianuu.essentials.util.Success
import com.ivianuu.essentials.util.Uninitialized

@Composable
fun <T> RenderAsyncList(
    state: Async<List<T>>,
    fail: @Composable (Throwable) -> Unit = {},
    loading: @Composable () -> Unit = { FullScreenLoading() },
    uninitialized: @Composable () -> Unit = loading,
    successEmpty: @Composable () -> Unit = {},
    successItemCallback: @Composable (Int, T) -> Unit
) {
    RenderAsync(
        state = state,
        fail = fail,
        loading = loading,
        uninitialized = uninitialized,
        success = { items ->
            if (items.isNotEmpty()) {
                ScrollableList(items = items) { index, item ->
                    skippable(item) {
                        successItemCallback(index, item)
                    }
                }
            } else {
                successEmpty()
            }
        }
    )
}

@Composable
fun <T> RenderAsync(
    state: Async<T>,
    fail: @Composable (Throwable) -> Unit = {},
    loading: @Composable () -> Unit = { FullScreenLoading() },
    uninitialized: @Composable () -> Unit = loading,
    success: @Composable (T) -> Unit
) {
    when (state) {
        Uninitialized -> uninitialized()
        is Loading -> loading()
        is Success -> success(state.value)
        is Fail -> fail(state.error)
    }
}
