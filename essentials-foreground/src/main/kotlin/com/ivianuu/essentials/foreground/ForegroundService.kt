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

package com.ivianuu.essentials.foreground

import android.app.NotificationManager
import com.ivianuu.essentials.util.AppDispatchers
import com.ivianuu.essentials.util.coroutineScope
import com.ivianuu.essentials.work.EsService
import com.ivianuu.injekt.inject
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForegroundService : EsService() {

    private val dispatchers: AppDispatchers by inject()
    private val foregroundManager: ForegroundManager by inject()
    private var lastComponents = listOf<ForegroundComponent>()
    private var foregroundId: Int? = null
    private val notificationManager: NotificationManager by inject()

    override fun onCreate() {
        super.onCreate()

        foregroundManager.updates
            .onStart { emit(Unit) }
            .onEach { update() }
            .flowOn(dispatchers.default)
            .launchIn(scope.coroutineScope)

        foregroundManager.stopServiceRequests
            .onEach { stop() }
            .flowOn(dispatchers.default)
            .launchIn(scope.coroutineScope)

        scope.coroutineScope.launch {
            withContext(dispatchers.default) {
                update()
            }
        }
    }

    private fun update() {
        val newComponents = foregroundManager.components

        lastComponents
            .filter { it !in newComponents }
            .forEach { component ->
                notificationManager.cancel(component.id)
                if (component.id == foregroundId) {
                    foregroundId = null
                }
            }

        lastComponents = newComponents
        if (newComponents.isEmpty()) return

        newComponents.forEachIndexed { index, component ->
            val notification = component.notificationFactory.buildNotification()
            if (index == 0) {
                startForeground(component.id, notification)
            } else {
                notificationManager.notify(component.id, notification)
            }
        }
    }

    private fun stop() {
        lastComponents.forEach {
            notificationManager.cancel(it.id)
        }
        stopForeground(true)
        stopSelf()
    }
}