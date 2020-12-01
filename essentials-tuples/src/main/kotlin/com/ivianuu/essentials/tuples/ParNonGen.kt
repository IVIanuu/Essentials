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

package com.ivianuu.essentials.tuples

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Semaphore

suspend fun <T> par(
    vararg blocks: suspend () -> T,
    concurrency: Int = defaultConcurrency
): List<T> =
    blocks.asIterable().parMap(concurrency) { it() }

suspend fun <T, R> Iterable<T>.parMap(
    concurrency: Int = defaultConcurrency,
    transform: suspend (T) -> R
): List<R> = supervisorScope {
    val semaphore = Semaphore(concurrency)
    map { item ->
        async {
            semaphore.acquire()
            try {
                transform(item)
            } finally {
                semaphore.release()
            }
        }
    }.awaitAll()
}

suspend fun <T> Iterable<T>.parFilter(
    concurrency: Int = defaultConcurrency,
    predicate: suspend (T) -> Boolean
): List<T> = parMap(concurrency) { if (predicate(it)) it else null }.filterNotNull()

suspend fun <T> Iterable<T>.parForEach(
    concurrency: Int = defaultConcurrency,
    action: suspend (T) -> Unit
) {
    parMap(concurrency) { action(it); it }
}

internal val defaultConcurrency by lazy(LazyThreadSafetyMode.NONE) {
    Runtime.getRuntime().availableProcessors().coerceAtLeast(3)
}