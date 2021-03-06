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

package com.ivianuu.essentials.permission.writesettings

import android.content.*
import android.provider.*
import androidx.core.net.*
import com.ivianuu.essentials.*
import com.ivianuu.essentials.permission.*
import com.ivianuu.essentials.permission.intent.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.android.*

interface WriteSettingsPermission : Permission

@Provide fun <P : WriteSettingsPermission> writeSettingsPermissionStateProvider(
  context: AppContext
): PermissionStateProvider<P> = { Settings.System.canWrite(context) }


@Provide fun <P : WriteSettingsPermission> writeSettingsPermissionIntentFactory(
  buildInfo: BuildInfo
): PermissionIntentFactory<P> = {
  Intent(
    Settings.ACTION_MANAGE_WRITE_SETTINGS,
    "package:${buildInfo.packageName}".toUri()
  )
}
