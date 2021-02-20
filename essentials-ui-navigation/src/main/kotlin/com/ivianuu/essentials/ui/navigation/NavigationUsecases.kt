/*
 * Copyright 2020 Manuel Wrage
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

package com.ivianuu.essentials.ui.navigation

import com.ivianuu.essentials.store.DispatchAction
import com.ivianuu.essentials.ui.navigation.NavigationAction.PopTop
import com.ivianuu.essentials.ui.navigation.NavigationAction.Push
import kotlinx.coroutines.CompletableDeferred

suspend fun <R> DispatchAction<NavigationAction>.pushForResult(key: Key): R? {
    val result = CompletableDeferred<R?>()
    @Suppress("UNCHECKED_CAST")
    this(Push(key, result as CompletableDeferred<Any?>))
    return result.await()
}

fun <R> DispatchAction<NavigationAction>.popWithResult(result: R?) {
    this(PopTop(result))
}
