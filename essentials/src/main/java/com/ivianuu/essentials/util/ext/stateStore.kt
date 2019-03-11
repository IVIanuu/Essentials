/*
 * Copyright 2018 Manuel Wrage
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

package com.ivianuu.essentials.util.ext

import com.ivianuu.closeable.Closeable
import com.ivianuu.scopes.Scope
import com.ivianuu.statestore.StateListener
import com.ivianuu.statestore.StateStore

fun <T> StateStore<T>.closeBy(scope: Scope): StateStore<T> =
    apply { scope.addListener(this::close) }

fun <T> StateStore<T>.addCloseableListener(listener: StateListener<T>): Closeable {
    val closeable = Closeable { removeStateListener(listener) }
    addStateListener(listener)
    return closeable
}