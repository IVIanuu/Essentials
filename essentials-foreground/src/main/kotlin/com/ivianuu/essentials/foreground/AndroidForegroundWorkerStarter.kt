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

package com.ivianuu.essentials.foreground

import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.await
import com.ivianuu.essentials.app.ScopeWorker
import com.ivianuu.essentials.util.Logger
import com.ivianuu.essentials.util.d
import com.ivianuu.essentials.work.OneTimeWorkRequestBuilder
import com.ivianuu.essentials.work.toFunctionalWorkerTag
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.scope.AppGivenScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter

@Given
fun androidForegroundWorkerStarter(
    @Given logger: Logger,
    @Given state: Flow<InternalForegroundState>,
    @Given workManager: WorkManager
): ScopeWorker<AppGivenScope> = {
    state
        .filter { it.isForeground }
        .filter {
            workManager.getWorkInfosByTag(ForegroundWorkerId.toFunctionalWorkerTag())
                .await()
                .none { it.state == WorkInfo.State.RUNNING }
        }
        .collect {
            logger.d { "start foreground worker $it" }
            workManager.cancelAllWorkByTag(ForegroundWorkerId.toFunctionalWorkerTag())
            workManager.enqueue(
                OneTimeWorkRequestBuilder(ForegroundWorkerId)
                    .build()
            )
        }
}
