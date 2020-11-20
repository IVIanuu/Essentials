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
import androidx.compose.runtime.remember
import com.ivianuu.essentials.util.Logger
import com.ivianuu.essentials.util.sortedGraph
import com.ivianuu.injekt.Arg
import com.ivianuu.injekt.Effect
import com.ivianuu.injekt.ForEffect
import com.ivianuu.injekt.FunApi
import com.ivianuu.injekt.FunBinding
import com.ivianuu.injekt.SetElements

@Effect
annotation class UiDecoratorBinding(
    val key: String,
    val dependencies: Array<String> = [],
    val dependents: Array<String> = []
) {
    companion object {
        @SetElements
        fun <T : @Composable (@Composable () -> Unit) -> Unit> uiDecoratorIntoSet(
            @Arg("key") key: String,
            @Arg("dependencies") dependencies: Array<String>?,
            @Arg("dependents") dependents: Array<String>?,
            content: @ForEffect T
        ): UiDecorators = setOf(UiDecorator(
            key = key,
            dependencies = dependencies?.toSet() ?: emptySet(),
            dependents = dependents?.toSet() ?: emptySet(),
            content = content as @Composable (@Composable () -> Unit) -> Unit
        ))
    }
}

data class UiDecorator(
    val key: String,
    val dependencies: Set<String>,
    val dependents: Set<String>,
    val content: @Composable (@Composable () -> Unit) -> Unit
)

typealias UiDecorators = Set<UiDecorator>
@SetElements
fun defaultUiDecorators(): UiDecorators = emptySet()

@FunBinding
@Composable
fun DecorateUi(
    decorators: UiDecorators,
    logger: Logger,
    @FunApi children: @Composable () -> Unit
) {
    remember {
        decorators
            .sortedGraph(
                key = { it.key },
                dependencies = { it.dependencies },
                dependents = { it.dependents }
            )
            .reversed()
            .fold(children) { acc, decorator ->
                {
                    logger.d("Decorate ui ${decorator.key}")
                    decorator.content(acc)
                }
            }
    }()
}

@Effect
annotation class AppThemeBinding {
    companion object {
        // The theme typically uses EsMaterialTheme which itself uses the SystemBarManager
        // So we ensure that were running after the system bars decorator
        @UiDecoratorBinding("app_theme", dependencies = ["system_bars"])
        fun <T : @Composable (@Composable () -> Unit) -> Unit> uiDecorator(
            instance: @ForEffect T
        ): T = instance
    }
}