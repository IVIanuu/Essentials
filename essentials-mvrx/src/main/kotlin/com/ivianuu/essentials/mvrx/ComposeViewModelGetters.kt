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

package com.ivianuu.essentials.mvrx

import androidx.compose.Composable
import androidx.lifecycle.ViewModelStoreOwner
import com.ivianuu.essentials.ui.compose.core.effect
import com.ivianuu.essentials.ui.compose.core.remember
import com.ivianuu.essentials.ui.compose.coroutines.collect
import com.ivianuu.essentials.ui.compose.injekt.inject
import com.ivianuu.essentials.ui.compose.viewmodel.viewModel
import com.ivianuu.essentials.util.defaultViewModelFactory
import com.ivianuu.essentials.util.defaultViewModelKey
import kotlin.reflect.KClass

@Composable
inline fun <reified T : MvRxViewModel<*>> mvRxViewModel(
    from: ViewModelStoreOwner = inject(),
    key: String = remember { T::class.defaultViewModelKey },
    noinline factory: () -> T = defaultViewModelFactory(T::class)
) = effect {
    mvRxViewModel(type = T::class, from = from, key = key, factory = factory)
}

@Composable
fun <T : MvRxViewModel<*>> mvRxViewModel(
    type: KClass<T>,
    from: ViewModelStoreOwner = inject(),
    key: String = remember { type.defaultViewModelKey },
    factory: () -> T = defaultViewModelFactory(type)
): T = effect {
    val viewModel = viewModel(type, from, key, factory)
    // recompose on changes
    collect(remember { viewModel.flow })
    return@effect viewModel
}