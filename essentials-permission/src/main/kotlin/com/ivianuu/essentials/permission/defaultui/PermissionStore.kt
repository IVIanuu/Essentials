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

package com.ivianuu.essentials.permission.defaultui

import com.ivianuu.essentials.permission.PermissionRequest
import com.ivianuu.essentials.permission.defaultui.PermissionAction.*
import com.ivianuu.essentials.permission.hasPermissions
import com.ivianuu.essentials.permission.requestHandler
import com.ivianuu.essentials.store.storeProvider
import com.ivianuu.essentials.ui.navigation.Navigator
import com.ivianuu.essentials.ui.navigation.popTop
import com.ivianuu.essentials.util.Logger
import com.ivianuu.essentials.util.exhaustive
import com.ivianuu.essentials.util.startUi
import com.ivianuu.injekt.Binding
import com.ivianuu.injekt.FunApi
import kotlinx.coroutines.flow.first

@Binding
fun permissionStore(
    hasPermissions: hasPermissions,
    logger: Logger,
    navigator: Navigator,
    requestHandler: requestHandler,
    startUi: startUi,
    @FunApi request: PermissionRequest,
) = storeProvider<PermissionState, PermissionAction>(PermissionState()) {
    suspend fun updatePermissionsToProcessOrFinish() {
        val permissionsToProcess = request.permissions
            .filterNot { hasPermissions(listOf(it)).first() }

        logger.d("update permissions to process or finish not granted $permissionsToProcess")

        if (permissionsToProcess.isEmpty()) {
            navigator.popTop()
        } else {
            setState { copy(permissionsToProcess = permissionsToProcess) }
        }
    }

    updatePermissionsToProcessOrFinish()

    for (action in this) {
        when (action) {
            is RequestPermission -> {
                action.permission.requestHandler().request(action.permission)
                startUi()
                updatePermissionsToProcessOrFinish()
            }
        }.exhaustive
    }
}