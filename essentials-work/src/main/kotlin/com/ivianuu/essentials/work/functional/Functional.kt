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

package com.ivianuu.essentials.work.functional

import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import com.ivianuu.injekt.MapEntries
import com.ivianuu.injekt.merge.ApplicationComponent
import com.ivianuu.injekt.merge.BindingModule
import java.util.UUID
import kotlin.time.Duration
import kotlin.time.toJavaDuration

typealias Worker = suspend WorkScope.() -> ListenableWorker.Result

interface WorkScope {
    val inputData: Data
    val id: UUID
    val tags: Set<String>
    val runAttemptCount: Int
    suspend fun setProgress(data: Data)
    suspend fun setForeground(foregroundInfo: ForegroundInfo)
}

@BindingModule(ApplicationComponent::class)
annotation class WorkerBinding {
    companion object {
        @MapEntries
        fun <T : WorkerWithId> intoWorkerMap(instance: T): Workers =
            mapOf(instance.id to instance.block)
    }
}

fun worker(
    id: String,
    block: Worker
): WorkerWithId = WorkerWithId(id, block)

data class WorkerWithId(val id: String, val block: Worker)

fun OneTimeWorkRequestBuilder(id: String): OneTimeWorkRequest.Builder {
    return OneTimeWorkRequestBuilder<FunctionalWorker>()
        .addTag(WORKER_ID_TAG_PREFIX + id)
}

fun PeriodicWorkRequestBuilder(
    id: String,
    repeatInterval: Duration,
    flexTimeInterval: Duration? = null
): PeriodicWorkRequest.Builder {
    return (if (flexTimeInterval != null) PeriodicWorkRequestBuilder<FunctionalWorker>(
        repeatInterval.toJavaDuration(), flexTimeInterval.toJavaDuration()
    ) else PeriodicWorkRequestBuilder<FunctionalWorker>(repeatInterval.toJavaDuration()))
        .addTag(WORKER_ID_TAG_PREFIX + id)
}