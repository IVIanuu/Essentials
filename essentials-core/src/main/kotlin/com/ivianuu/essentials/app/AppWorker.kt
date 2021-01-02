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

package com.ivianuu.essentials.app

import com.ivianuu.essentials.coroutines.GlobalScope
import com.ivianuu.essentials.util.Logger
import com.ivianuu.essentials.util.d
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.GivenFun
import com.ivianuu.injekt.GivenSetElement
import com.ivianuu.injekt.Macro
import com.ivianuu.injekt.Qualifier
import kotlinx.coroutines.launch

@Qualifier annotation class AppWorkerBinding
@Macro @GivenSetElement
fun <T : @AppWorkerBinding AppWorker> appWorkerBindingImpl(@Given instance: T): AppWorker =
    instance

typealias AppWorker = suspend () -> Unit

@GivenFun
fun runAppWorkers(
    @Given logger: Logger,
    @Given globalScope: GlobalScope,
    @Given workers: Set<AppWorker>
) {
    logger.d { "run app workers" }
    workers
        .forEach { worker ->
            globalScope.launch {
                worker()
            }
        }
}
