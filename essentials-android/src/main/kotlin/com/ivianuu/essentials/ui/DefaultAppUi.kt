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

package com.ivianuu.essentials.ui

import androidx.compose.runtime.Composable
import com.ivianuu.essentials.ui.core.AppUiBinding
import com.ivianuu.essentials.ui.navigation.Content
import com.ivianuu.essentials.ui.navigation.HomeRoute
import com.ivianuu.essentials.ui.navigation.Navigator
import com.ivianuu.essentials.ui.navigation.setRootIfEmpty
import com.ivianuu.injekt.FunBinding

@AppUiBinding
@FunBinding
@Composable
fun DefaultAppUi(
    homeRoute: HomeRoute,
    navigator: Navigator
) {
    navigator.setRootIfEmpty(homeRoute)
    navigator.Content()
}