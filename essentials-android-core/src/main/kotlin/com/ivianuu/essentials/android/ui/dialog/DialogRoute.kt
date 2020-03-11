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

package com.ivianuu.essentials.android.ui.dialog

import androidx.compose.Composable
import com.ivianuu.essentials.android.ui.navigation.NavigatorAmbient
import com.ivianuu.essentials.android.ui.navigation.Route
import com.ivianuu.essentials.android.ui.navigation.transition.FadeRouteTransition

fun DialogRoute(
    dismissible: Boolean = true,
    dismissHandler: @Composable () -> Unit = { NavigatorAmbient.current.popTop() },
    dialog: @Composable () -> Unit
) = Route(
    opaque = true,
    enterTransition = FadeRouteTransition(),
    exitTransition = FadeRouteTransition()
) {
    DialogWrapper(
        dismissible = dismissible,
        dismissHandler = dismissHandler,
        dialog = dialog
    )
}