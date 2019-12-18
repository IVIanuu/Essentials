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

package com.ivianuu.essentials.ui.viewmodel

import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.ivianuu.essentials.ui.injekt.ComponentAmbient
import com.ivianuu.essentials.ui.injekt.inject
import com.ivianuu.essentials.util.defaultViewModelFactory
import com.ivianuu.essentials.util.defaultViewModelKey
import com.ivianuu.essentials.util.getViewModel
import com.ivianuu.injekt.ParametersDefinition
import com.ivianuu.injekt.Type
import com.ivianuu.injekt.typeOf
import kotlin.reflect.KClass

@Composable
inline fun <reified T : ViewModel> viewModel(
    from: ViewModelStoreOwner = inject(),
    key: String = remember { T::class.defaultViewModelKey },
    noinline factory: () -> T = defaultViewModelFactory(T::class)
): T = viewModel(type = T::class, key = key, from = from, factory = factory)

@Composable
fun <T : ViewModel> viewModel(
    type: KClass<T>,
    from: ViewModelStoreOwner = inject(),
    key: String = remember { type.defaultViewModelKey },
    factory: () -> T = defaultViewModelFactory(type)
): T = remember {
    from.getViewModel(type = type, key = key, from = from, factory = factory)
}

@Composable
inline fun <reified T : ViewModel> injectViewModel(
    from: ViewModelStoreOwner = inject(),
    key: String = remember { T::class.defaultViewModelKey },
    name: Any? = null,
    noinline parameters: ParametersDefinition? = null
): T = injectViewModel(
    typeOf<T>(),
    from,
    key,
    name,
    parameters
)

@Composable
fun <T : ViewModel> injectViewModel(
    type: Type<T>,
    from: ViewModelStoreOwner = inject(),
    key: String = remember { type.defaultViewModelKey },
    name: Any? = null,
    parameters: ParametersDefinition? = null
): T {
    val component = ambient(ComponentAmbient)
    return viewModel(
        type = type.raw as KClass<T>,
        from = from,
        key = key,
        factory = { component.get(type = type, name = name, parameters = parameters) }
    )
}