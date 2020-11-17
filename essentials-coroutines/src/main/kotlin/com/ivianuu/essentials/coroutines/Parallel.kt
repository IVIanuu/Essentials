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

package com.ivianuu.essentials.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Semaphore

private val defaultConcurrency by lazy(LazyThreadSafetyMode.NONE) {
    Runtime.getRuntime().availableProcessors().coerceAtLeast(3)
}

suspend fun <A, B> Collection<A>.parallelMap(
    concurrency: Int = defaultConcurrency,
    transform: suspend (A) -> B
): List<B> = supervisorScope {
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

suspend fun <A> Collection<A>.parallelForEach(
    concurrency: Int = defaultConcurrency,
    action: suspend (A) -> Unit
) {
    parallelMap(concurrency) { action(it) }
}
