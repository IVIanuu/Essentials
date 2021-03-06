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

package com.ivianuu.essentials.permission.runtime

import android.content.pm.*
import androidx.activity.result.contract.*
import com.ivianuu.essentials.permission.*
import com.ivianuu.essentials.ui.navigation.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.android.*

interface RuntimePermission : Permission {
  val permissionName: String
}

@Provide fun <P : RuntimePermission> runtimePermissionStateProvider(
  context: AppContext
): PermissionStateProvider<P> = { permission ->
  context.checkSelfPermission(permission.permissionName) == PackageManager.PERMISSION_GRANTED
}

@Provide fun <P : RuntimePermission> runtimePermissionRequestHandler(
  context: AppContext,
  navigator: Navigator
): PermissionRequestHandler<P> = { permission ->
  val contract = ActivityResultContracts.RequestPermission()
  navigator.push(contract.createIntent(context, permission.permissionName).toIntentKey())
}
