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

package com.ivianuu.essentials.notificationlistener

import com.ivianuu.injekt.Given
import com.ivianuu.injekt.Qualifier
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

typealias NotificationWorker = suspend () -> Unit

@Qualifier
annotation class NotificationWorkerBinding

@Given
fun <@Given T : @NotificationWorkerBinding S, S : NotificationWorker> notificationWorkerBindingImpl(
    @Given instance: T): NotificationWorker = instance

typealias NotificationWorkerRunner = suspend () -> Unit

@Given
fun notificationWorkerRunner(@Given workers: Set<NotificationWorker>): NotificationWorkerRunner = {
    coroutineScope {
        workers.forEach { worker ->
            launch { worker() }
        }
    }
}
