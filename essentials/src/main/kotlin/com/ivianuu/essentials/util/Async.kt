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

package com.ivianuu.essentials.util

import androidx.compose.Composable
import androidx.compose.Immutable
import androidx.compose.remember
import androidx.compose.stateFor
import com.ivianuu.essentials.ui.core.pointInComposition
import com.ivianuu.essentials.ui.coroutines.collect
import com.ivianuu.essentials.ui.coroutines.launchOnCommit
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

@Immutable
sealed class Async<out T>(val complete: Boolean, val shouldLoad: Boolean) {
    open operator fun invoke(): T? = null
}

@Immutable
object Uninitialized : Async<Nothing>(complete = false, shouldLoad = true)

@Immutable
class Loading<out T> : Async<T>(complete = false, shouldLoad = false) {
    override fun equals(other: Any?) = other is Loading<*>
    override fun hashCode() = "Loading".hashCode()
}

@Immutable
data class Success<out T>(val value: T) : Async<T>(complete = true, shouldLoad = false) {
    override operator fun invoke(): T = value
}

@Immutable
data class Fail<out T>(val error: Throwable) : Async<T>(complete = true, shouldLoad = true)

fun <T> Flow<T>.executeAsync(): Flow<Async<T>> {
    return this
        .map { Success(it) as Async<T> }
        .onStart { emit(Loading()) }
        .catch { Fail<T>(it) }
}

fun <T> Deferred<T>.executeAsync(): Flow<Async<T>> {
    return flow {
        emit(Loading<T>())
        try {
            emit(Success(await()))
        } catch (e: Throwable) {
            emit(Fail<T>(e))
        }
    }
}

fun <T> executeAsync(block: suspend () -> T): Flow<Async<T>> {
    return flow {
        emit(Loading<T>())
        try {
            emit(Success(block()))
        } catch (e: Throwable) {
            emit(Fail<T>(e))
        }
    }
}

inline fun <T, R> Async<T>.map(transform: (T) -> R) =
    if (this is Success) Success(transform(value)) else this as Async<R>

fun <T> Async<T>.valueOrThrow(): T {
    if (this is Success) return value
    else error("$this has no value")
}

@Composable
fun <T> collectAsync(flow: Flow<T>) = collect(
    flow = remember(flow) { flow.executeAsync() },
    placeholder = Uninitialized
)

@Composable
fun <T> loadAsync(block: suspend () -> T): Async<T> =
    loadAsync(
        key = pointInComposition(),
        block = block
    )

@Composable
fun <T> loadAsync(
    key: Any,
    block: suspend () -> T
): Async<T> {
    val state = stateFor<Async<T>>(key) { Uninitialized }

    launchOnCommit(key) {
        state.value = Loading()
        try {
            state.value = Success(block())
        } catch (e: Throwable) {
            state.value = Fail<T>(e)
        }
    }

    return state.value
}