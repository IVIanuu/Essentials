package com.ivianuu.essentials.ui.store

import androidx.compose.Composable
import androidx.compose.collectAsState
import com.ivianuu.essentials.store.Store
import com.ivianuu.essentials.store.StoreScope
import com.ivianuu.essentials.store.setState
import com.ivianuu.essentials.ui.common.rememberRetained
import com.ivianuu.essentials.ui.coroutines.rememberRetainedCoroutinesScope
import com.ivianuu.essentials.ui.resource.Resource
import com.ivianuu.essentials.ui.resource.flowAsResource
import com.ivianuu.essentials.util.dispatchers
import com.ivianuu.injekt.Reader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun <S, V> StoreScope<S, *>.execute(
    block: suspend () -> V,
    reducer: suspend S.(Resource<V>) -> S
): Job {
    return flow { emit(block()) }
        .executeIn(this, reducer)
}

fun <S, V> Flow<V>.executeIn(
    storeScope: StoreScope<S, *>,
    reducer: suspend S.(Resource<V>) -> S
): Job {
    return flowAsResource()
        .onEach {
            storeScope.setState {
                reducer(it)
            }
        }
        .launchIn(storeScope.scope)
}

@Composable
operator fun <S> Store<S, *>.component1() = observedState

@Composable
operator fun <A> Store<*, A>.component2(): (A) -> Unit = { dispatch(it) }

@Composable
val <S> Store<S, *>.observedState: S
    get() = state.collectAsState().value

// todo remove overload once compiler is fixed
@Reader
@Composable
fun <S, A> rememberStore(
    init: CoroutineScope.() -> Store<S, A>
): Store<S, A> = rememberStore(inputs = *emptyArray(), init = init)

@Reader
@Composable
fun <S, A> rememberStore(
    vararg inputs: Any?,
    init: CoroutineScope.() -> Store<S, A>
): Store<S, A> {
    val scope = rememberRetainedCoroutinesScope { dispatchers.default }
    return rememberRetained(*inputs) { init(scope) }
}
