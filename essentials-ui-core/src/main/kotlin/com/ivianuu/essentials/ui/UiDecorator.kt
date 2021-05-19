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

import androidx.compose.runtime.*
import com.ivianuu.essentials.*
import com.ivianuu.essentials.logging.*
import com.ivianuu.essentials.ui.core.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.common.*

@Provide fun <@Spread T : UiDecorator> uiDecoratorElement(
  instance: T,
  key: TypeKey<T>,
  config: UiDecoratorConfig<T> = UiDecoratorConfig.DEFAULT
): UiDecoratorElement = UiDecoratorElement(key, instance as UiDecorator, config)

class UiDecoratorConfig<out T : UiDecorator>(
  val dependencies: Set<TypeKey<UiDecorator>> = emptySet(),
  val dependents: Set<TypeKey<UiDecorator>> = emptySet(),
) {
  companion object {
    val DEFAULT = UiDecoratorConfig<Nothing>(emptySet(), emptySet())
  }
}

typealias UiDecorator = @Composable (@Composable () -> Unit) -> Unit

data class UiDecoratorElement(
  val key: TypeKey<UiDecorator>,
  val decorator: UiDecorator,
  val config: UiDecoratorConfig<*>
)

@Provide object UiDecoratorElementTreeDescriptor : TreeDescriptor<UiDecoratorElement> {
  override fun key(value: UiDecoratorElement): Any = value.key
  override fun dependencies(value: UiDecoratorElement): Set<Any> = value.config.dependencies
  override fun dependents(value: UiDecoratorElement): Set<Any> = value.config.dependents
}

typealias DecorateUi = @Composable (@Composable () -> Unit) -> Unit

@Provide fun decorateUi(
  elements: Set<UiDecoratorElement> = emptySet(),
  _: Logger
): DecorateUi = { content ->
  remember {
    elements
      .sortedTopological()
      .reversed()
      .fold(content) { acc, element ->
        {
          d { "Decorate ui ${element.key}" }
          element.decorator(acc)
        }
      }
  }.invoke()
}

typealias AppTheme = UiDecorator

@Provide val appThemeConfig = UiDecoratorConfig<AppTheme>(
  dependencies = setOf(typeKeyOf<SystemBarManagerProvider>())
)
