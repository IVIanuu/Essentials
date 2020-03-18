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

import com.ivianuu.essentials.coroutines.EventFlow
import com.ivianuu.essentials.coroutines.shareIn
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import java.io.IOException

interface DiskBox<T> : Box<T> {
    interface Serializer<T> {
        fun deserialize(serialized: String): T
        fun serialize(value: T): String
    }
}

fun <T> DiskBox(
    path: String,
    serializer: DiskBox.Serializer<T>,
    defaultValue: T,
    coroutineScope: CoroutineScope
): DiskBox<T> =
    DiskBoxImpl(
        path = path,
        defaultValue = defaultValue,
        serializer = serializer,
        coroutineScope = coroutineScope
    )

internal class DiskBoxImpl<T>(
    private val path: String,
    override val defaultValue: T,
    private val serializer: DiskBox.Serializer<T>,
    coroutineScope: CoroutineScope
) : DiskBox<T> {

    private val changeNotifier = EventFlow<Unit>()
    private var cachedValue: Any? = this
    private val cachedValueMutex = Mutex()
    private val writeLock = WriteLock()

    private val file by lazy { File(path) }

    override val data: Flow<T> = changeNotifier
        .map { get() }
        .onStart { emit(get()) }
        .distinctUntilChanged()
        .shareIn(
            scope = coroutineScope,
            cacheSize = 1
        )

    init {
        coroutineScope.launch { check(!file.isDirectory) }
    }

    override suspend fun updateData(transform: suspend (T) -> T) {
        val currentValue = get()
        val newValue = transform(currentValue)
        if (newValue === currentValue) return

        try {
            writeLock.beginWrite()

            if (!file.exists()) {
                file.parentFile?.mkdirs()

                if (!file.createNewFile()) {
                    throw IOException("Couldn't create $path")
                }
            }

            val serialized = try {
                serializer.serialize(newValue)
            } catch (e: Exception) {
                throw RuntimeException(
                    "Couldn't serialize value '$newValue' for file $path",
                    e
                )
            }

            log { "$path -> set '$newValue serialized '$serialized'" }

            val tmpFile = File.createTempFile(
                "new", "tmp", file.parentFile
            )
            try {
                tmpFile.bufferedWriter().use {
                    it.write(serialized)
                }
                if (!tmpFile.renameTo(file)) {
                    throw IOException("Couldn't move tmp file to file $path")
                }
            } catch (e: Exception) {
                throw IOException("Couldn't write to file $path '$serialized'", e)
            } finally {
                tmpFile.delete()
            }

            cachedValueMutex.withLock { cachedValue = newValue }
            changeNotifier.offer(Unit)
        } finally {
            writeLock.endWrite()
        }
    }

    private suspend fun get(): T {
        writeLock.awaitWrite()
        val cached = cachedValueMutex.withLock { cachedValue }
        if (cached != this) return cached as T

        return if (file.exists()) {
            val serialized = try {
                file.bufferedReader().use {
                    it.readText()
                }
            } catch (e: Exception) {
                throw IOException("Couldn't read file at $path", e)
            }

            try {
                val deserialized = serializer.deserialize(serialized)
                log { "$path -> fetch '$serialized' deserialized '$deserialized'" }
                cachedValueMutex.withLock { cachedValue = deserialized }
                deserialized
            } catch (e: Exception) {
                throw RuntimeException("Couldn't deserialize '$serialized' for file $path", e)
            }
        } else {
            defaultValue
        }
    }
}

private class WriteLock {

    private var currentLock: CompletableDeferred<Unit>? = null
    private val mutex = Mutex()

    suspend fun awaitWrite() {
        val lock = mutex.withLock { currentLock }
        lock?.await()
    }

    suspend fun beginWrite() {
        awaitWrite()
        mutex.withLock { currentLock = CompletableDeferred() }
    }

    suspend fun endWrite() {
        val lock = mutex.withLock {
            val tmp = currentLock
            currentLock = null
            tmp
        }

        lock?.complete(Unit)
    }
}
