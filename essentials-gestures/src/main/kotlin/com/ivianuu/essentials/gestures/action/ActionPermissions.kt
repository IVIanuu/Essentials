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

package com.ivianuu.essentials.gestures.action

import android.accessibilityservice.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.*
import com.ivianuu.essentials.*
import com.ivianuu.essentials.accessibility.*
import com.ivianuu.essentials.gestures.R
import com.ivianuu.essentials.permission.accessibility.*
import com.ivianuu.essentials.permission.root.*
import com.ivianuu.essentials.permission.writesecuresettings.*
import com.ivianuu.essentials.permission.writesettings.*
import com.ivianuu.injekt.*
import kotlin.reflect.*

@Provide class ActionAccessibilityPermission(
  private val rp: ResourceProvider
) : AccessibilityServicePermission {
  override val serviceClass: KClass<out AccessibilityService>
    get() = EsAccessibilityService::class
  override val title: String
    get() = loadResource(R.string.es_action_accessibility_permission_title)
  override val desc: String
    get() = loadResource(R.string.es_action_accessibility_permission_title)
  override val icon: @Composable () -> Unit
    get() = {
      Icon(painterResource(R.drawable.es_ic_accessibility), null)
    }
}

@Provide class ActionRootPermission(
  private val rp: ResourceProvider
) : RootPermission {
  override val title: String
    get() = loadResource(R.string.es_action_root_permission_title)
  override val icon: @Composable () -> Unit
    get() = { Icon(painterResource(R.drawable.es_ic_adb), null) }
}

@Provide class ActionWriteSecureSettingsPermission(
  private val rp: ResourceProvider
) : WriteSecureSettingsPermission {
  override val title: String
    get() = loadResource(R.string.es_action_write_secure_settings_permission_title)
  override val desc: String
    get() = loadResource(R.string.es_action_write_secure_settings_permission_desc)
  override val icon: @Composable () -> Unit
    get() = { Icon(painterResource(R.drawable.es_ic_settings), null) }
}

@Provide class ActionWriteSettingsPermission(
  private val rp: ResourceProvider
) : WriteSettingsPermission {
  override val title: String
    get() = loadResource(R.string.es_action_write_settings_permission_title)
  override val desc: String
    get() = loadResource(R.string.es_action_write_settings_permission_desc)
  override val icon: @Composable () -> Unit
    get() = { Icon(painterResource(R.drawable.es_ic_settings), null) }
}
