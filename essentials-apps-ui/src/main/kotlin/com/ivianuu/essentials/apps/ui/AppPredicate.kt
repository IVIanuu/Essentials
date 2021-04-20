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

package com.ivianuu.essentials.apps.ui

import android.content.*
import android.content.pm.*
import com.ivianuu.essentials.apps.*
import com.ivianuu.injekt.*

typealias AppPredicate = (AppInfo) -> Boolean

val DefaultAppPredicate: AppPredicate = { true }

typealias LaunchableAppPredicate = AppPredicate

@Given
fun launchableAppPredicate(
    @Given packageManager: PackageManager
): LaunchableAppPredicate {
    val cache = mutableMapOf<String, Boolean>()
    return { app ->
        cache.getOrPut(app.packageName) {
            packageManager.getLaunchIntentForPackage(app.packageName) != null
        }
    }
}

typealias IntentAppPredicate = AppPredicate

@Given
fun intentAppPredicate(
    @Given packageManager: PackageManager,
    @Given intent: Intent
): IntentAppPredicate {
    val apps by lazy {
        packageManager.queryIntentActivities(intent, 0)
            .map { it.activityInfo.applicationInfo.packageName }
    }
    return { app -> app.packageName in apps }
}