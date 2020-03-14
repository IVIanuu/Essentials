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

package com.ivianuu.essentials.permission.dialogui

import androidx.compose.Composable
import androidx.compose.Pivotal
import androidx.compose.Recompose
import androidx.compose.frames.modelListOf
import androidx.fragment.app.FragmentActivity
import androidx.ui.res.stringResource
import com.ivianuu.essentials.permission.Desc
import com.ivianuu.essentials.permission.Icon
import com.ivianuu.essentials.permission.Metadata
import com.ivianuu.essentials.permission.Permission
import com.ivianuu.essentials.permission.PermissionActivity
import com.ivianuu.essentials.permission.PermissionManager
import com.ivianuu.essentials.permission.PermissionRequest
import com.ivianuu.essentials.permission.PermissionRequestHandlers
import com.ivianuu.essentials.permission.PermissionRequestUi
import com.ivianuu.essentials.permission.R
import com.ivianuu.essentials.permission.Title
import com.ivianuu.essentials.ui.base.ViewModel
import com.ivianuu.essentials.ui.core.ActivityAmbient
import com.ivianuu.essentials.ui.core.Text
import com.ivianuu.essentials.ui.dialog.DialogButton
import com.ivianuu.essentials.ui.dialog.DialogRoute
import com.ivianuu.essentials.ui.dialog.ScrollableDialog
import com.ivianuu.essentials.ui.material.ListItem
import com.ivianuu.essentials.ui.navigation.NavigatorState
import com.ivianuu.essentials.ui.viewmodel.injectViewModel
import com.ivianuu.essentials.util.AppCoroutineDispatchers
import com.ivianuu.essentials.util.Logger
import com.ivianuu.injekt.Factory
import com.ivianuu.injekt.Param
import com.ivianuu.injekt.parametersOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Factory
class DialogPermissionRequestUi(
    private val navigator: NavigatorState
) : PermissionRequestUi {

    override fun performRequest(
        activity: FragmentActivity,
        manager: PermissionManager,
        request: PermissionRequest
    ) {
        navigator.push(PermissionRoute(request))
    }
}

private fun PermissionRoute(request: PermissionRequest) = DialogRoute(
    dismissHandler = { ActivityAmbient.current.finish() }
) {
    Recompose { recompose ->
        ScrollableDialog(
            title = { Text("Required Permissions") }, // todo customizable
            listContent = {
                val viewModel =
                    injectViewModel<PermissionDialogViewModel>(parameters = parametersOf(request))
                val activity = ActivityAmbient.current as PermissionActivity
                viewModel.permissionsToProcess.forEach { permission ->
                    Permission(
                        permission = permission,
                        onClick = { viewModel.permissionClicked(activity, permission) }
                    )
                }
            },
            negativeButton = {
                val activity = ActivityAmbient.current
                DialogButton(onClick = { activity.finish() }) {
                    Text(stringResource(R.string.es_cancel))
                }
            }
        )
    }
}

@Composable
private fun Permission(
    onClick: () -> Unit,
    @Pivotal permission: Permission
) {
    ListItem(
        title = { Text(permission.metadata[Metadata.Title]) },
        subtitle = permission.metadata.getOrNull(Metadata.Desc)?.let{
            {
                Text(it)
            }
        },
        leading = permission.metadata.getOrNull(Metadata.Icon),
        onClick = onClick
    )
}

@Factory
private class PermissionDialogViewModel(
    private val dispatchers: AppCoroutineDispatchers,
    private val logger: Logger,
    private val manager: PermissionManager,
    @Param private val request: PermissionRequest,
    private val requestHandlers: PermissionRequestHandlers
) : ViewModel() {

    private val _permissionsToProcess = modelListOf<Permission>()
    val permissionsToProcess: List<Permission> get() = _permissionsToProcess

    init {
        updatePermissionsToProcessOrFinish()
    }

    fun permissionClicked(activity: PermissionActivity, permission: Permission) {
        coroutineScope.launch {
            logger.d("request $permission")
            withContext(dispatchers.main) {
                requestHandlers.requestHandlerFor(permission)
                    .request(activity, manager, permission)
            }

            updatePermissionsToProcessOrFinish()
        }
    }

    private fun updatePermissionsToProcessOrFinish() {
        coroutineScope.launch {
            val permissionsToProcess = request.permissions
                .filterNot { manager.hasPermissions(it) }

            logger.d("update permissions to process or finish not granted $permissionsToProcess")

            if (permissionsToProcess.isEmpty()) {
                request.onComplete()
            } else {
                withContext(dispatchers.main) {
                    _permissionsToProcess.clear()
                    _permissionsToProcess += permissionsToProcess
                }
            }
        }
    }
}
