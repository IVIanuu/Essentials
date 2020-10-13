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

package com.ivianuu.essentials.securesettings

import android.Manifest.permission.WRITE_SECURE_SETTINGS
import android.content.pm.PackageManager
import com.ivianuu.essentials.shell.runShellCommand
import com.ivianuu.essentials.util.BuildInfo
import com.ivianuu.injekt.FunBinding
import com.ivianuu.injekt.android.ApplicationContext

@FunBinding
suspend fun hasSecureSettingsPermission(
    applicationContext: ApplicationContext,
): Boolean = applicationContext.checkSelfPermission(WRITE_SECURE_SETTINGS) ==
        PackageManager.PERMISSION_GRANTED

@FunBinding
suspend fun grantSecureSettingsPermissionViaRoot(
    buildInfo: BuildInfo,
    hasSecureSettingsPermission: hasSecureSettingsPermission,
    runShellCommand: runShellCommand,
): Boolean {
    return try {
        runShellCommand("pm grant ${buildInfo.packageName} android.permission.WRITE_SECURE_SETTINGS")
        hasSecureSettingsPermission()
    } catch (t: Throwable) {
        false
    }
}