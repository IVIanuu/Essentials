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

package com.ivianuu.essentials.store

import com.ivianuu.essentials.store.prefs.BoxValueHolder
import com.ivianuu.essentials.store.prefs.valueFor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.reflect.KClass

inline fun <reified T, S> Box<T>.unwrap(): Box<S> where T : Enum<T>, T : BoxValueHolder<S> =
    unwrap(type = T::class)

fun <T, S> Box<T>.unwrap(
    type: KClass<T>
): Box<S> where T : Enum<T>, T : BoxValueHolder<S> {
    val wrapped = this
    return object : Box<S> {
        override val isDisposed: Boolean
            get() = wrapped.isDisposed

        override val defaultValue: S
            get() = wrapped.defaultValue.value

        override suspend fun isSet(): Boolean = wrapped.isSet()

        override suspend fun get(): S = wrapped.get().value

        override suspend fun set(value: S) {
            wrapped.set(type.valueFor(value, wrapped.defaultValue))
        }

        override suspend fun delete() {
            wrapped.delete()
        }

        override fun asFlow(): Flow<S> = wrapped.asFlow().map { it.value }

        override fun dispose() {
            wrapped.dispose()
        }

    }
}