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

package com.ivianuu.essentials.permission

import com.ivianuu.injekt.Given
import com.ivianuu.injekt.GivenSetElement
import com.ivianuu.injekt.Macro
import com.ivianuu.injekt.Qualifier

@Qualifier annotation class PermissionStateProviderBinding
@Macro @GivenSetElement
fun <T : @PermissionStateProviderBinding PermissionStateProvider> stateProviderIntoSet(
    @Given instance: T): PermissionStateProvider = instance

@Qualifier annotation class PermissionRequestHandlerBinding
@Macro @GivenSetElement
fun <T : @PermissionRequestHandlerBinding PermissionRequestHandler> requestHandlerIntoSet(
    @Given instance: T): PermissionRequestHandler = instance

@Qualifier annotation class PermissionRequestRouteFactoryBinding
@Macro @Given
fun <T : @PermissionRequestRouteFactoryBinding PermissionRequestKeyFactory> permissionRequestKeyFactory(
    @Given instance: T
): PermissionRequestKeyFactory = instance