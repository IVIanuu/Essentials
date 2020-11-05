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

package com.ivianuu.essentials.store

import com.ivianuu.essentials.coroutines.EventFlow
import com.ivianuu.essentials.store.StoreFromSourceImpl.StoreMessage.DispatchAction
import com.ivianuu.essentials.store.StoreFromSourceImpl.StoreMessage.SetState
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.launch

interface Store<S, A> {
    val state: StateFlow<S>
    fun dispatch(action: A)
}

interface StoreScope<S, A> : CoroutineScope {
    val actions: Flow<A>
    val state: Flow<S>
    suspend fun setState(block: suspend S.() -> S): S
}

operator fun <S, A> StoreScope<S, A>.iterator(): StoreScopeActionIterator<S, A> {
    val channelIterator = actions.produceIn(this).iterator()
    return object : StoreScopeActionIterator<S, A> {
        override suspend fun hasNext(): Boolean = channelIterator.hasNext()
        override suspend fun next(): A = channelIterator.next()
    }
}

interface StoreScopeActionIterator<S, A> {
    suspend operator fun hasNext(): Boolean
    suspend operator fun next(): A
}

suspend inline fun <S, A> StoreScope<S, A>.currentState(): S = state.first()

inline fun <S, A, T> Flow<T>.setStateIn(
    scope: StoreScope<S, A>,
    crossinline reducer: suspend S.(T) -> S,
): Job {
    return onEach {
        scope.setState {
            reducer(it)
        }
    }
        .launchIn(scope)
}

suspend inline fun <S, A> StoreScope<S, A>.onEachAction(block: (A) -> Unit) {
    for (action in this) block(action)
}

fun <S, A> CoroutineScope.store(
    initial: S,
    block: suspend StoreScope<S, A>.() -> Unit
): Store<S, A> {
    val state = MutableStateFlow(initial)
    return storeFromSource(
        state = state,
        setState = { state.value = it },
        block = block
    )
}

fun <S, A> CoroutineScope.storeFromSource(
    state: StateFlow<S>,
    setState: suspend (S) -> Unit,
    block: suspend StoreScope<S, A>.() -> Unit
): Store<S, A> = StoreFromSourceImpl(this, state, setState, block)

internal class StoreFromSourceImpl<S, A>(
    scope: CoroutineScope,
    override val state: StateFlow<S>,
    private val setState: suspend (S) -> Unit,
    block: suspend StoreScope<S, A>.() -> Unit
) : Store<S, A>, StoreScope<S, A>, CoroutineScope by scope {

    override val actions = EventFlow<A>()

    private val actor = actor<StoreMessage<S, A>>(capacity = Channel.UNLIMITED) {
        for (msg in channel) {
            @Suppress("IMPLICIT_CAST_TO_ANY")
            when (msg) {
                is DispatchAction -> actions.emit(msg.action)
                is SetState -> {
                    val currentState = state.value
                    val newState = msg.block(currentState)
                    if (currentState != newState) setState(newState)
                    msg.acknowledged.complete(newState)
                }
            }.let {}
        }
    }

    init {
        launch { block() }
    }

    override fun dispatch(action: A) {
        actor.offer(DispatchAction(action))
    }

    override suspend fun setState(block: suspend S.() -> S): S {
        val acknowledged = CompletableDeferred<S>()
        actor.offer(SetState(acknowledged, block))
        return acknowledged.await()
    }

    sealed class StoreMessage<S, A> {
        data class DispatchAction<S, A>(val action: A) : StoreMessage<S, A>()
        data class SetState<S, A>(
            val acknowledged: CompletableDeferred<S>,
            val block: suspend S.() -> S,
        ) : StoreMessage<S, A>()
    }
}
