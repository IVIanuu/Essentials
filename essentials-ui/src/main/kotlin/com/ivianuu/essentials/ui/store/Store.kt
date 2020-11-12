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
import androidx.compose.runtime.remember
import com.ivianuu.essentials.coroutines.DefaultDispatcher
import com.ivianuu.essentials.coroutines.GlobalScope
import com.ivianuu.essentials.store.Store
import com.ivianuu.essentials.store.StoreScope
import com.ivianuu.essentials.store.setStateIn
import com.ivianuu.essentials.ui.common.rememberRetained
import com.ivianuu.essentials.ui.resource.Resource
import com.ivianuu.essentials.ui.resource.flowAsResource
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.reflect.KType
import kotlin.reflect.typeOf

fun <S, V> StoreScope<S, *>.execute(
    block: suspend () -> V,
    reducer: suspend S.(Resource<V>) -> S
): Job {
    return flow { emit(block()) }
        .executeIn(this, reducer)
}

fun <S, V> Flow<V>.executeIn(
    scope: StoreScope<S, *>,
    reducer: suspend S.(Resource<V>) -> S,
): Job = flowAsResource().setStateIn(scope, reducer)

@Effect
annotation class UiStoreBinding {
    companion object {
        @Binding
        @Composable
        inline fun <reified T : Store<S, A>, reified S, reified A> uiStore(
            defaultDispatcher: DefaultDispatcher,
            noinline provider: (CoroutineScope) -> T
        ): Store<S, A> = uiStoreImpl(defaultDispatcher, typeOf<T>(), provider)

        @PublishedApi
        @Composable
        internal fun <S, A> uiStoreImpl(
            defaultDispatcher: DefaultDispatcher,
            type: KType,
            provider: (CoroutineScope) -> Store<S, A>
        ): Store<S, A> {
            return rememberRetained(key = type) {
                UiStoreRunner(CoroutineScope(Job() + defaultDispatcher), provider)
            }.store
        }
    }
}

@PublishedApi
internal class UiStoreRunner<S, A>(
    private val coroutineScope: CoroutineScope,
    store: (CoroutineScope) -> Store<S, A>
) : DisposableHandle {
    val store = store(coroutineScope)
    override fun dispose() {
        coroutineScope.cancel()
    }
}

@Qualifier
@Target(AnnotationTarget.TYPE)
annotation class State

@Qualifier
@Target(AnnotationTarget.TYPE)
annotation class UiState

@Binding
@Composable
operator fun <S> Store<S, *>.component1(): @UiState S = state.collectAsState().value

@Binding
inline val <S> @State StateFlow<S>.flow: @State Flow<S>
    get() = this

@Binding
inline val <S> Store<S, *>.stateFlow: @State StateFlow<S>
    get() = state

@Qualifier
@Target(AnnotationTarget.TYPE)
annotation class Dispatch

@Binding
operator fun <A> Store<*, A>.component2(): @Dispatch (A) -> Unit = { dispatch(it) }

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
fun <T : Store<S, A>, S, A> stateEffectStoreDecoratorDefault(
    scope: () -> GlobalScope,
    stateEffects: Set<StateEffectBlock<S>>?,
    factory: () -> T
): () -> T {
    return if (stateEffects == null) factory
    else ({
        val instance = factory()
        scope().launch {
            instance.state.collectLatest { state ->
                coroutineScope {
                    stateEffects.forEach { effect ->
                        launch {
                            effect(state)
                        }
                    }
                }
            }
        }
        instance
    })
}

@Decorator
fun <T : Store<S, A>, S, A> stateEffectStoreDecoratorComposable(
    scope: () -> GlobalScope,
    stateEffects: Set<StateEffectBlock<S>>?,
    factory: @Composable () -> T
): @Composable () -> T {
    return if (stateEffects == null) factory
    else ({
        val instance = factory()
        scope().launch {
            instance.state.collectLatest { state ->
                coroutineScope {
                    stateEffects.forEach { effect ->
                        launch {
                            effect(state)
                        }
                    }
                }
            }
        }
        instance
    })
}

@Decorator
fun <T : Store<S, A>, S, A> stateEffectStoreDecoratorSuspend(
    scope: () -> GlobalScope,
    stateEffects: Set<StateEffectBlock<S>>?,
    factory: suspend () -> T
): suspend () -> T {
    return if (stateEffects == null) factory
    else ({
        val instance = factory()
        scope().launch {
            instance.state.collectLatest { state ->
                coroutineScope {
                    stateEffects.forEach { effect ->
                        launch {
                            effect(state)
                        }
                    }
                }
            }
        }
        instance
    })
}
