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

package com.ivianuu.essentials.ui.compose.es

import android.view.View
import androidx.compose.Composable
import androidx.lifecycle.lifecycleScope
import com.ivianuu.director.requireActivity
import com.ivianuu.essentials.ui.compose.core.invokeAsComposable
import com.ivianuu.essentials.ui.navigation.director.ControllerRoute
import com.ivianuu.essentials.ui.navigation.director.controllerRoute
import kotlinx.coroutines.launch

fun composeControllerRoute(
    popOnConfigurationChange: Boolean = false,
    options: ControllerRoute.Options? = null,
    compose: @Composable() () -> Unit
) = controllerRoute(
    options = options,
    factory = {
        ComposeRouteController(
            popOnConfigurationChange,
            compose
        )
    }
)

private class ComposeRouteController(
    private val popOnConfigurationChange: Boolean,
    private val _compose: @Composable() () -> Unit
) : ComposeController() {

    override fun content() {
        _compose.invokeAsComposable()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        lifecycleScope.launch {
            if (requireActivity().isChangingConfigurations && popOnConfigurationChange) {
                navigator.pop()
            }
        }
    }
}