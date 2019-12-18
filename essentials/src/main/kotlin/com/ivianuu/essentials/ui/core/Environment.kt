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

package com.ivianuu.essentials.ui.core

import androidx.activity.ComponentActivity
import androidx.compose.Composable
import androidx.compose.remember
import androidx.ui.core.CoroutineContextAmbient
import androidx.ui.core.ambientDensity
import androidx.ui.foundation.isSystemInDarkTheme
import com.ivianuu.essentials.ui.common.MultiAmbientProvider
import com.ivianuu.essentials.ui.common.with
import com.ivianuu.essentials.ui.injekt.ComponentAmbient
import com.ivianuu.essentials.ui.material.SystemBarManager
import com.ivianuu.essentials.ui.material.SystemBarManagerAmbient
import com.ivianuu.injekt.Component
import kotlin.coroutines.CoroutineContext

@Composable
fun EsEnvironment(
    activity: ComponentActivity,
    container: AndroidComposeViewContainer,
    component: Component,
    coroutineContext: CoroutineContext,
    children: @Composable() () -> Unit
) {
    MultiAmbientProvider(
        ActivityAmbient with activity,
        ComponentAmbient with component,
        CoroutineContextAmbient with coroutineContext,
        SystemBarManagerAmbient with remember { SystemBarManager(activity) }
    ) {
        val viewportMetrics = container.viewportMetrics
        val density = ambientDensity()
        val isDarkTheme = isSystemInDarkTheme()

        val mediaQuery = MediaQuery(
            size = viewportMetrics.size,
            viewPadding = viewportMetrics.viewPadding,
            viewInsets = viewportMetrics.viewInsets,
            density = density,
            darkMode = isDarkTheme
        )

        MediaQueryProvider(value = mediaQuery, children = children)
    }
}