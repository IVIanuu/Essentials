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

package com.ivianuu.essentials.permission.root

import com.ivianuu.essentials.permission.Permission
import com.ivianuu.essentials.permission.PermissionRequestHandler
import com.ivianuu.essentials.permission.PermissionStateProvider
import com.ivianuu.essentials.permission.R
import com.ivianuu.essentials.shell.IsShellAvailableUseCase
import com.ivianuu.essentials.util.LoadStringResourceUseCase
import com.ivianuu.essentials.util.Toaster
import com.ivianuu.injekt.Given

interface RootPermission : Permission

@Given
fun <P : RootPermission> rootPermissionStateProvider(
    @Given isShellAvailable: IsShellAvailableUseCase
): PermissionStateProvider<P> = { isShellAvailable() }

@Given
fun <P : RootPermission> rootPermissionRequestHandler(
    @Given isShellAvailable: IsShellAvailableUseCase,
    @Given stringResource: LoadStringResourceUseCase,
    @Given toaster: Toaster
): PermissionRequestHandler<P> = {
    val isOk = isShellAvailable()
    if (!isOk) toaster(stringResource(R.string.es_no_root, emptyList()))
}
