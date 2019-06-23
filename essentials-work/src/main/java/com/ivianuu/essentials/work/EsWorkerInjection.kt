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

package com.ivianuu.essentials.work

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.ivianuu.essentials.service.InitializingService
import com.ivianuu.essentials.service.bindAppService
import com.ivianuu.injekt.Inject
import com.ivianuu.injekt.module

/**
 * Module for the [WorkerAppInitializer]
 */
val workerInitializerModule = module {
    bindAppService<WorkerAppInitializer>()
}

/**
 * Initializes the [WorkManager] with a injected [WorkerFactory]
 */
@Inject
internal class WorkerAppInitializer(
    private val context: Context,
    private val workerFactory: WorkerFactory
) : InitializingService() {
    override fun initialize() {
        WorkManager.initialize(
            context, Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        )
    }
}