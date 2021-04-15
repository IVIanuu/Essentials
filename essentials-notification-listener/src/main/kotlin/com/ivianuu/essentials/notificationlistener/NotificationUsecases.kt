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

import android.app.Notification
import android.service.notification.StatusBarNotification
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.runCatching
import com.ivianuu.injekt.Given
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

typealias Notifications = List<StatusBarNotification>

@Given
fun notifications(@Given ref: Flow<EsNotificationListenerService?>): Flow<Notifications> = ref
    .flatMapLatest { it?.notifications ?: flowOf(emptyList()) }

typealias OpenNotificationUseCase = suspend (Notification) -> Result<Unit, Throwable>

@Given
val openNotificationUseCase: OpenNotificationUseCase = { notification ->
    runCatching { notification.contentIntent.send() }
}

typealias DismissNotificationUseCase = suspend (String) -> Result<Unit, Throwable>

@Given
fun dismissNotificationUseCase(
    @Given ref: Flow<EsNotificationListenerService?>
): DismissNotificationUseCase = { key ->
    runCatching { ref.first()!!.cancelNotification(key) }
}

typealias DismissAllNotificationsUseCase = suspend () -> Unit

@Given
fun dismissAllNotificationsUseCase(
    @Given ref: Flow<EsNotificationListenerService?>
): DismissAllNotificationsUseCase = {
    runCatching { ref.first()!!.cancelAllNotifications() }
}