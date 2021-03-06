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

package com.ivianuu.essentials.sample.ui

import android.app.*
import android.service.notification.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import androidx.core.graphics.drawable.*
import com.github.michaelbull.result.*
import com.ivianuu.essentials.*
import com.ivianuu.essentials.coroutines.*
import com.ivianuu.essentials.notificationlistener.*
import com.ivianuu.essentials.optics.*
import com.ivianuu.essentials.permission.*
import com.ivianuu.essentials.permission.notificationlistener.*
import com.ivianuu.essentials.resource.*
import com.ivianuu.essentials.sample.R
import com.ivianuu.essentials.store.*
import com.ivianuu.essentials.ui.image.*
import com.ivianuu.essentials.ui.layout.*
import com.ivianuu.essentials.ui.material.*
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.*
import com.ivianuu.essentials.ui.resource.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.android.*
import com.ivianuu.injekt.common.*
import com.ivianuu.injekt.coroutines.*
import com.ivianuu.injekt.scope.*
import kotlinx.coroutines.flow.*
import kotlin.reflect.*

@Provide val notificationsHomeItem = HomeItem("Notifications") { NotificationsKey }

object NotificationsKey : Key<Nothing>

@Provide val notificationsUi: ModelKeyUi<NotificationsKey, NotificationsModel> = {
  Scaffold(topBar = { TopAppBar(title = { Text("Notifications") }) }) {
    ResourceBox(model.hasPermissions) { hasPermission ->
      if (hasPermission) {
        NotificationsList(
          notifications = model.notifications,
          onNotificationClick = { model.openNotification(it) },
          onDismissNotificationClick = { model.dismissNotification(it) }
        )
      } else {
        NotificationPermissions(model.requestPermissions)
      }
    }
  }
}

@Composable private fun NotificationsList(
  onNotificationClick: (UiNotification) -> Unit,
  onDismissNotificationClick: (UiNotification) -> Unit,
  notifications: Resource<List<UiNotification>>
) {
  ResourceLazyColumnFor(
    resource = notifications,
    successEmpty = {
      Text(
        text = "No notifications",
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.center()
      )
    },
    successItemContent = { notification ->
      ListItem(
        title = { Text(notification.title) },
        subtitle = { Text(notification.text) },
        onClick = { onNotificationClick(notification) },
        leading = {
          Box(
            modifier = Modifier.size(40.dp)
              .background(
                color = notification.color,
                shape = CircleShape
              )
              .padding(all = 8.dp)
          ) {
            notification.icon()
          }
        },
        trailing = if (notification.isClearable) {
          {
            IconButton(onClick = { onDismissNotificationClick(notification) }) {
              Icon(painterResource(R.drawable.es_ic_clear), null)
            }
          }
        } else null
      )
    }
  )
}

@Composable private fun NotificationPermissions(
  onRequestPermissionsClick: () -> Unit
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Permissions required",
      style = MaterialTheme.typography.subtitle1
    )
    Spacer(Modifier.height(8.dp))
    Button(onClick = onRequestPermissionsClick) {
      Text("Request")
    }
  }
}

@Optics data class NotificationsModel(
  val hasPermissions: Resource<Boolean> = Idle,
  val notifications: Resource<List<UiNotification>> = Idle,
  val requestPermissions: () -> Unit = {},
  val openNotification: (UiNotification) -> Unit = {},
  val dismissNotification: (UiNotification) -> Unit = {}
)

data class UiNotification(
  val title: String,
  val text: String,
  val icon: @Composable () -> Unit,
  val color: Color,
  val isClearable: Boolean,
  val sbn: StatusBarNotification
)

@Provide fun notificationsModel(
  context: AppContext,
  dismissNotification: DismissNotificationUseCase,
  notifications: Flow<Notifications>,
  openNotification: OpenNotificationUseCase,
  permissionState: Flow<PermissionState<SampleNotificationsPermission>>,
  permissionRequester: PermissionRequester,
  scope: InjektCoroutineScope<KeyUiScope>
): @Scoped<KeyUiScope> StateFlow<NotificationsModel> = scope.state(NotificationsModel()) {
  notifications
    .map { notifications ->
      notifications
        .parMap { it.toUiNotification(context) }
    }
    .flowAsResource()
    .update { copy(notifications = it) }
  permissionState
    .flowAsResource()
    .update { copy(hasPermissions = it) }
  action(NotificationsModel.requestPermissions()) {
    permissionRequester(listOf(typeKeyOf<SampleNotificationsPermission>()))
  }
  action(NotificationsModel.openNotification()) { notification ->
    openNotification(notification.sbn.notification)
  }
  action(NotificationsModel.dismissNotification()) { notification ->
    dismissNotification(notification.sbn.key)
  }
}

private fun StatusBarNotification.toUiNotification(context: AppContext) = UiNotification(
  title = notification.extras.getCharSequence(Notification.EXTRA_TITLE)
    ?.toString() ?: "",
  text = notification.extras.getCharSequence(Notification.EXTRA_TEXT)
    ?.toString() ?: "",
  icon = catch {
    notification.smallIcon
      .loadDrawable(context)
  }.orElse {
    catch {
      notification.getLargeIcon()
        .loadDrawable(context)
    }
  }
    .map { it.toBitmap().toImageBitmap() }
    .fold(
      success = { bitmap ->
        {
          Image(
            modifier = Modifier.size(24.dp),
            bitmap = bitmap,
            contentDescription = null
          )
        }
      },
      failure = {
        { Icon(painterResource(R.drawable.es_ic_error), null) }
      }
    ),
  color = Color(notification.color),
  isClearable = isClearable,
  sbn = this
)

@Provide object SampleNotificationsPermission : NotificationListenerPermission {
  override val serviceClass: KClass<out NotificationListenerService>
    get() = EsNotificationListenerService::class
  override val title: String = "Notifications"
  override val icon: @Composable (() -> Unit)?
    get() = null
}
