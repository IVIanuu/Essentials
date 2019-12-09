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

package com.ivianuu.essentials.mvrx.injekt

import androidx.lifecycle.ViewModelStoreOwner
import com.ivianuu.essentials.mvrx.MvRxView
import com.ivianuu.essentials.mvrx.MvRxViewModel
import com.ivianuu.essentials.mvrx.getMvRxViewModel
import com.ivianuu.essentials.mvrx.mvRxViewModel
import com.ivianuu.essentials.util.defaultViewModelKey
import com.ivianuu.injekt.InjektTrait
import com.ivianuu.injekt.ParametersDefinition
import com.ivianuu.injekt.get

inline fun <S, reified T : MvRxViewModel<*>> S.injectMvRxViewModel(
    noinline from: () -> ViewModelStoreOwner = { this },
    noinline key: () -> String = { T::class.defaultViewModelKey },
    noinline name: () -> Any? = { null },
    noinline parameters: ParametersDefinition? = null
): Lazy<T> where S : MvRxView, S : InjektTrait =
    mvRxViewModel(from, key) { get(name = name(), parameters = parameters) }

inline fun <S, reified T : MvRxViewModel<*>> S.getMvRxViewModel(
    from: ViewModelStoreOwner = this,
    key: String = T::class.defaultViewModelKey,
    name: Any? = null,
    noinline parameters: ParametersDefinition? = null
): T where S : MvRxView, S : InjektTrait =
    getMvRxViewModel(from, key) { get(name = name, parameters = parameters) }