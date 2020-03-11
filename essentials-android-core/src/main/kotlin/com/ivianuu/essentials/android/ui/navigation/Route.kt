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

package com.ivianuu.essentials.android.ui.navigation

import androidx.compose.Composable
import androidx.compose.Immutable
import androidx.compose.staticAmbientOf

@Immutable
class Route(
    val opaque: Boolean = false,
    val keepState: Boolean = false,
    val enterTransition: RouteTransition? = null,
    val exitTransition: RouteTransition? = null,
    val content: @Composable () -> Unit
) {

    constructor(
        opaque: Boolean = false,
        keepState: Boolean = false,
        transition: RouteTransition? = null,
        content: @Composable () -> Unit
    ) : this(
        opaque = opaque,
        keepState = keepState,
        enterTransition = transition,
        exitTransition = transition,
        content = content
    )

    fun copy(
        opaque: Boolean = this.opaque,
        keepState: Boolean = this.keepState,
        enterTransition: RouteTransition? = this.enterTransition,
        exitTransition: RouteTransition? = this.exitTransition,
        content: @Composable () -> Unit = this.content
    ): Route = Route(
        opaque = opaque,
        keepState = keepState,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        content = content
    )

    @Composable
    internal fun compose() {
        content()
    }
}

val RouteAmbient =
    staticAmbientOf<Route> { error("No route provided") }