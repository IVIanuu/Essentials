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

package com.ivianuu.essentials.ui.store

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.ivianuu.essentials.coroutines.DefaultDispatcher
import com.ivianuu.essentials.coroutines.GlobalScope
import com.ivianuu.essentials.ui.common.rememberRetained
import com.ivianuu.injekt.Binding
import com.ivianuu.injekt.Decorator
import com.ivianuu.injekt.Effect
import com.ivianuu.injekt.Qualifier
import com.ivianuu.injekt.SetElements
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@Effect
annotation class UiStoreBinding {
    companion object {
        @Binding
        @Composable
        inline fun <reified T : StateFlow<S>, reified S> uiStore(
            defaultDispatcher: DefaultDispatcher,
            noinline provider: (CoroutineScope) -> T
        ): StateFlow<S> = uiStoreImpl(defaultDispatcher, typeOf<T>(), provider)

        @PublishedApi
        @Composable
        internal fun <S> uiStoreImpl(
            defaultDispatcher: DefaultDispatcher,
            type: KType,
            provider: (CoroutineScope) -> StateFlow<S>
        ): StateFlow<S> {
            return rememberRetained(key = type) {
                UiStoreRunner(CoroutineScope(Job() + defaultDispatcher), provider)
            }.store
        }
    }
}

@PublishedApi
internal class UiStoreRunner<S>(
    private val coroutineScope: CoroutineScope,
    store: (CoroutineScope) -> StateFlow<S>
) : DisposableHandle {
    val store = store(coroutineScope)
    override fun dispose() {
        coroutineScope.cancel()
    }
}

@Qualifier
@Target(AnnotationTarget.TYPE)
annotation class Initial

@Qualifier
@Target(AnnotationTarget.TYPE)
annotation class State

@Binding
inline val <S> StateFlow<S>.flow: @State Flow<S>
    get() = this

@Qualifier
@Target(AnnotationTarget.TYPE)
annotation class UiState
@Binding
@Composable
fun <S> StateFlow<S>.latest(): @UiState S = collectAsState().value

@Effect
annotation class StateEffect {
    companion object {
        @SetElements
        fun <T : suspend (S) -> Unit, S> intoSet(instance: T): Set<StateEffectBlock<S>> =
            setOf(instance)
    }
}

typealias StateEffectBlock<S> = suspend (S) -> Unit

@Decorator
fun <T : StateFlow<S>, S> stateEffectStoreDecoratorDefault(
    scope: () -> GlobalScope,
    stateEffects: Set<StateEffectBlock<S>>?,
    factory: () -> T
): () -> T {
    return if (stateEffects == null || stateEffects.isEmpty()) factory
    else {
        var state: Pair<StateFlow<S>, Job>? = null
        {
            val instance = factory()
            if (state == null || state!!.first != instance) {
                state = instance to scope().launch {
                    instance.collectLatest { state ->
                        coroutineScope {
                            stateEffects.forEach { effect ->
                                launch {
                                    effect(state)
                                }
                            }
                        }
                    }
                }
            }
            instance
        }
    }
}

@Decorator
fun <T : StateFlow<S>, S> stateEffectStoreDecoratorComposable(
    scope: () -> GlobalScope,
    stateEffects: Set<StateEffectBlock<S>>?,
    factory: @Composable () -> T
): @Composable () -> T {
    return if (stateEffects == null || stateEffects.isEmpty()) factory
    else {
        var state: Pair<StateFlow<S>, Job>? = null
        {
            val instance = factory()
            if (state == null || state!!.first != instance) {
                state = instance to scope().launch {
                    instance.collectLatest { state ->
                        coroutineScope {
                            stateEffects.forEach { effect ->
                                launch {
                                    effect(state)
                                }
                            }
                        }
                    }
                }
            }
            instance
        }
    }
}

@Decorator
fun <T : StateFlow<S>, S> stateEffectStoreDecoratorSuspend(
    scope: () -> GlobalScope,
    stateEffects: Set<StateEffectBlock<S>>?,
    factory: suspend () -> T
): suspend () -> T {
    return if (stateEffects == null || stateEffects.isEmpty()) factory
    else {
        var state: Pair<StateFlow<S>, Job>? = null
        {
            val instance = factory()
            if (state == null || state!!.first != instance) {
                state = instance to scope().launch {
                    instance.collectLatest { state ->
                        coroutineScope {
                            stateEffects.forEach { effect ->
                                launch {
                                    effect(state)
                                }
                            }
                        }
                    }
                }
            }
            instance
        }
    }
}
