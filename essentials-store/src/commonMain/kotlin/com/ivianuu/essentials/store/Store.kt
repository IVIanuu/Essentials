package com.ivianuu.essentials.store

import com.ivianuu.essentials.coroutines.EventFlow
import com.ivianuu.essentials.coroutines.ScopeCoroutineScope
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.scope.GivenScope
import com.ivianuu.injekt.scope.Scoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlin.reflect.KClass

interface Store<S, A> : StateFlow<S>, Collector<A>

fun <S, A> CoroutineScope.store(
    initial: S,
    actions: MutableSharedFlow<A> = EventFlow(),
    block: suspend StoreScope<S, A>.() -> Unit
): Store<S, A> {
    val state = state(initial) {
        object : StoreScope<S, A>, StateScope<S> by this {
            override val actions: Flow<A>
                get() = actions
        }.block()
    }
    return object : Store<S, A>, StateFlow<S> by state, Collector<A> {
        override fun emit(value: A) {
            actions.tryEmit(value)
        }
    }
}

interface StoreScope<S, A> : StateScope<S> {
    val actions: Flow<A>
}

inline fun <reified T> StoreScope<*, in T>.effectOn(noinline block: suspend (T) -> Unit): Job =
    actions.filterIsInstance<T>().effect(block)

inline fun <S, reified T : Any> StoreScope<S, in T>.updateOn(
    @Suppress("UNUSED_PARAMETER") clazz: KClass<T>,
    noinline reducer: S.(T) -> S
): Job = actions.filterIsInstance<T>().update(reducer)

typealias StoreBuilder<GS, S, A> = suspend StoreScope<S, A>.() -> Unit

@Given
class StoreBuilderModule<@Given T : StoreBuilder<GS, S, A>, GS : GivenScope, S, A> {
    @Given
    fun store(
        @Given builder: T,
        @Given initial: @InitialOrFallback S,
        @Given actions: MutableSharedFlow<A>,
        @Given scope: ScopeCoroutineScope<GS>
    ): @Scoped<GS> Store<S, A> = scope.store(initial, actions, builder)

    @Given
    val actions: @Scoped<GS> MutableSharedFlow<A>
        get() = EventFlow()
}

fun <S, A> StoreBuilder<*, S, A>.toStore(
    scope: CoroutineScope,
    initial: S,
    actions: MutableSharedFlow<A> = EventFlow()
): Store<S, A> = scope.store(initial, actions, this)