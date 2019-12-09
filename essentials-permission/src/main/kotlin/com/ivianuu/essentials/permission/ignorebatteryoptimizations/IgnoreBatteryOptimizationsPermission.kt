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

package com.ivianuu.essentials.permission.ignorebatteryoptimizations

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.provider.Settings
import androidx.core.net.toUri
import com.ivianuu.essentials.permission.Metadata
import com.ivianuu.essentials.permission.MetadataKeys
import com.ivianuu.essentials.permission.Permission
import com.ivianuu.essentials.permission.PermissionStateProvider
import com.ivianuu.essentials.permission.intent.Intent
import com.ivianuu.essentials.permission.metadataOf
import com.ivianuu.injekt.Factory

@SuppressLint("BatteryLife")
fun IgnoreBatteryOptimizationsPermission(
    context: Context,
    vararg pairs: Pair<Metadata.Key<*>, Any?>
) = Permission(
    metadata = metadataOf(
        MetadataKeys.IgnoreBatteryOptimizationsPermission to Unit,
        MetadataKeys.Intent to Intent(
            Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            "package:${context.packageName}".toUri()
        ),
        *pairs
    )
)

val MetadataKeys.IgnoreBatteryOptimizationsPermission by lazy {
    Metadata.Key<Unit>("IgnoreBatteryOptimizationsPermission")
}

@Factory
class IgnoreBatteryOptimizationsPermissionStateProvider(
    private val context: Context,
    private val powerManager: PowerManager
) : PermissionStateProvider {

    override fun handles(permission: Permission): Boolean =
        permission.metadata.contains(MetadataKeys.IgnoreBatteryOptimizationsPermission)

    override suspend fun isGranted(permission: Permission): Boolean =
        powerManager.isIgnoringBatteryOptimizations(context.packageName)
}