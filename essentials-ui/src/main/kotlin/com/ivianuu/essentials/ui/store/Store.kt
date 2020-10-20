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
import androidx.compose.runtime.key
import com.ivianuu.essentials.coroutines.DefaultDispatcher
import com.ivianuu.essentials.store.Store
import com.ivianuu.essentials.store.StoreScope
import com.ivianuu.essentials.store.setStateIn
import com.ivianuu.essentials.ui.common.rememberRetained
import com.ivianuu.essentials.ui.coroutines.rememberRetainedCoroutinesScope
import com.ivianuu.essentials.ui.resource.Resource
import com.ivianuu.essentials.ui.resource.flowAsResource
import com.ivianuu.injekt.FunBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

@Composable
operator fun <S> Store<S, *>.component1() = snapshotState

@Composable
operator fun <A> Store<*, A>.component2(): (A) -> Unit = { dispatch(it) }

@Composable
val <S> Store<S, *>.snapshotState: S
    get() = state.collectAsState().value

typealias rememberStore<S, A> = @Composable () -> Store<S, A>
@FunBinding
@Composable
fun <S, A> rememberStore(
    rememberStoreViaFactory: rememberStoreViaFactory<S, A>,
    provider: () -> (CoroutineScope) -> Store<S, A>
): Store<S, A> = rememberStoreViaFactory { provider()(it) }

typealias rememberStore1<S, A, P1> = @Composable (P1) -> Store<S, A>
@FunBinding
@Composable
fun <S, A, P1> rememberStore1(
    rememberStoreViaFactory: rememberStoreViaFactory<S, A>,
    provider: (P1) -> (CoroutineScope) -> Store<S, A>,
    p1: P1
): Store<S, A> = key(p1) {
    rememberStoreViaFactory { provider(p1)(it) }
}

typealias rememberStore2<S, A, P1, P2> = @Composable (P1, P2) -> Store<S, A>
@FunBinding
@Composable
fun <S, A, P1, P2> rememberStore2(
    rememberStoreViaFactory: rememberStoreViaFactory<S, A>,
    provider: (P1, P2) -> (CoroutineScope) -> Store<S, A>,
    p1: P1,
    p2: P2
): Store<S, A> = key(p1, p2) {
    rememberStoreViaFactory { provider(p1, p2)(it) }
}

typealias rememberStore3<S, A, P1, P2, P3> = @Composable (P1, P2, P3) -> Store<S, A>
@FunBinding
@Composable
fun <S, A, P1, P2, P3> rememberStore3(
    rememberStoreViaFactory: rememberStoreViaFactory<S, A>,
    provider: (P1, P2, P3) -> (CoroutineScope) -> Store<S, A>,
    p1: P1,
    p2: P2,
    p3: P3
): Store<S, A> = key(p1, p2, p3) {
    rememberStoreViaFactory { provider(p1, p2, p3)(it) }
}

typealias rememberStore4<S, A, P1, P2, P3, P4> = @Composable (P1, P2, P3, P4) -> Store<S, A>
@FunBinding
@Composable
fun <S, A, P1, P2, P3, P4> rememberStore4(
    rememberStoreViaFactory: rememberStoreViaFactory<S, A>,
    provider: (P1, P2, P3, P4) -> (CoroutineScope) -> Store<S, A>,
    p1: P1,
    p2: P2,
    p3: P3,
    p4: P4
): Store<S, A> = key(p1, p2, p3, p4) {
    rememberStoreViaFactory { provider(p1, p2, p3, p4)(it) }
}

typealias rememberStore5<S, A, P1, P2, P3, P4, P5> = @Composable (P1, P2, P3, P4, P5) -> Store<S, A>
@FunBinding
@Composable
fun <S, A, P1, P2, P3, P4, P5> rememberStore5(
    rememberStoreViaFactory: rememberStoreViaFactory<S, A>,
    provider: (P1, P2, P3, P4, P5) -> (CoroutineScope) -> Store<S, A>,
    p1: P1,
    p2: P2,
    p3: P3,
    p4: P4,
    p5: P5
): Store<S, A> = key(p1, p2, p3, p4, p5) {
    rememberStoreViaFactory { provider(p1, p2, p3, p4, p5)(it) }
}

typealias rememberStoreViaFactory<S, A> = @Composable ((CoroutineScope) -> Store<S, A>) -> Store<S, A>
@FunBinding
@Composable
fun <S, A> rememberStoreViaFactory(
    defaultDispatcher: DefaultDispatcher,
    init: (CoroutineScope) -> Store<S, A>
): Store<S, A> {
    val scope = rememberRetainedCoroutinesScope { defaultDispatcher }
    return rememberRetained { init(scope) }
}
