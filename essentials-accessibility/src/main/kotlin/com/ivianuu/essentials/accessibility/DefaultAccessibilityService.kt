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

package com.ivianuu.essentials.accessibility

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import com.ivianuu.essentials.util.Logger
import com.ivianuu.essentials.util.addFlag
import com.ivianuu.injekt.android.AndroidEntryPoint
import com.ivianuu.injekt.inject

@AndroidEntryPoint
class DefaultAccessibilityService : EsAccessibilityService() {

    private val services: AccessibilityServices by inject()
    private val logger: Logger by inject()

    override fun onServiceConnected() {
        super.onServiceConnected()

        logger.d("connected")
        services.onServiceConnected(this)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        logger.d("on accessibility event $event")
        services.onAccessibilityEvent(event)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        logger.d("on unbind")
        services.onServiceDisconnected()
        return super.onUnbind(intent)
    }

    fun updateConfig(configs: List<AccessibilityConfig>) {
        serviceInfo = serviceInfo.apply {
            eventTypes = configs
                .map { it.eventTypes }
                .fold(0) { acc, events -> acc.addFlag(events) }

            flags = configs
                .map { it.flags }
                .fold(0) { acc, flags -> acc.addFlag(flags) }

            configs.lastOrNull()?.feedbackType?.let { feedbackType = it } // todo
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC

            notificationTimeout = configs
                .map { it.notificationTimeout }
                .max() ?: 0L

            packageNames = null

            logger.d("update service info $this")
        }
    }
}