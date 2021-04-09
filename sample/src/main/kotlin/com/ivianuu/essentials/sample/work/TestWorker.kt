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

package com.ivianuu.essentials.sample.work

import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.ivianuu.essentials.util.Logger
import com.ivianuu.essentials.util.d
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.android.AppContext
import com.ivianuu.injekt.android.work.WorkerBinding
import kotlinx.coroutines.delay

@WorkerBinding
@Given
class TestWorker(
    @Given appContext: AppContext,
    @Given params: WorkerParameters,
    @Given private val logger: Logger
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        logger.d { "start work" }
        delay(5000)
        logger.d { "finish work" }
        return Result.success()
    }
}

typealias TestWorkScheduler = () -> Unit

@Given
fun testWorkScheduler(@Given workManager: WorkManager): TestWorkScheduler = {
    workManager.enqueue(OneTimeWorkRequestBuilder<TestWorker>().build())
}
