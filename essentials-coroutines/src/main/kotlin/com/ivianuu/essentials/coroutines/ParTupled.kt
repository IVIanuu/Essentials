package com.ivianuu.essentials.coroutines

import com.ivianuu.essentials.tuples.Tuple10
import com.ivianuu.essentials.tuples.Tuple11
import com.ivianuu.essentials.tuples.Tuple12
import com.ivianuu.essentials.tuples.Tuple13
import com.ivianuu.essentials.tuples.Tuple14
import com.ivianuu.essentials.tuples.Tuple15
import com.ivianuu.essentials.tuples.Tuple16
import com.ivianuu.essentials.tuples.Tuple17
import com.ivianuu.essentials.tuples.Tuple18
import com.ivianuu.essentials.tuples.Tuple19
import com.ivianuu.essentials.tuples.Tuple2
import com.ivianuu.essentials.tuples.Tuple20
import com.ivianuu.essentials.tuples.Tuple21
import com.ivianuu.essentials.tuples.Tuple3
import com.ivianuu.essentials.tuples.Tuple4
import com.ivianuu.essentials.tuples.Tuple5
import com.ivianuu.essentials.tuples.Tuple6
import com.ivianuu.essentials.tuples.Tuple7
import com.ivianuu.essentials.tuples.Tuple8
import com.ivianuu.essentials.tuples.Tuple9
import com.ivianuu.essentials.tuples.tupleOf

suspend fun <A, B> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, concurrency: Int = defaultConcurrency): Tuple2<A, B> {
    val result = par<Any?>(blockA, blockB, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B)
}

suspend fun <A, B, C> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, concurrency: Int = defaultConcurrency): Tuple3<A, B, C> {
    val result = par<Any?>(blockA, blockB, blockC, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C)
}

suspend fun <A, B, C, D> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, concurrency: Int = defaultConcurrency): Tuple4<A, B, C, D> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D)
}

suspend fun <A, B, C, D, E> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, concurrency: Int = defaultConcurrency): Tuple5<A, B, C, D, E> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E)
}

suspend fun <A, B, C, D, E, F> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, concurrency: Int = defaultConcurrency): Tuple6<A, B, C, D, E, F> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F)
}

suspend fun <A, B, C, D, E, F, G> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, blockG: suspend () -> G, concurrency: Int = defaultConcurrency): Tuple7<A, B, C, D, E, F, G> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, blockG, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F, result[6] as G)
}

suspend fun <A, B, C, D, E, F, G, H> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, blockG: suspend () -> G, blockH: suspend () -> H, concurrency: Int = defaultConcurrency): Tuple8<A, B, C, D, E, F, G, H> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, blockG, blockH, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F, result[6] as G, result[7] as H)
}

suspend fun <A, B, C, D, E, F, G, H, I> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, blockG: suspend () -> G, blockH: suspend () -> H, blockI: suspend () -> I, concurrency: Int = defaultConcurrency): Tuple9<A, B, C, D, E, F, G, H, I> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, blockG, blockH, blockI, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F, result[6] as G, result[7] as H, result[8] as I)
}

suspend fun <A, B, C, D, E, F, G, H, I, J> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, blockG: suspend () -> G, blockH: suspend () -> H, blockI: suspend () -> I, blockJ: suspend () -> J, concurrency: Int = defaultConcurrency): Tuple10<A, B, C, D, E, F, G, H, I, J> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, blockG, blockH, blockI, blockJ, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F, result[6] as G, result[7] as H, result[8] as I, result[9] as J)
}

suspend fun <A, B, C, D, E, F, G, H, I, J, K> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, blockG: suspend () -> G, blockH: suspend () -> H, blockI: suspend () -> I, blockJ: suspend () -> J, blockK: suspend () -> K, concurrency: Int = defaultConcurrency): Tuple11<A, B, C, D, E, F, G, H, I, J, K> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, blockG, blockH, blockI, blockJ, blockK, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F, result[6] as G, result[7] as H, result[8] as I, result[9] as J, result[10] as K)
}

suspend fun <A, B, C, D, E, F, G, H, I, J, K, L> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, blockG: suspend () -> G, blockH: suspend () -> H, blockI: suspend () -> I, blockJ: suspend () -> J, blockK: suspend () -> K, blockL: suspend () -> L, concurrency: Int = defaultConcurrency): Tuple12<A, B, C, D, E, F, G, H, I, J, K, L> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, blockG, blockH, blockI, blockJ, blockK, blockL, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F, result[6] as G, result[7] as H, result[8] as I, result[9] as J, result[10] as K, result[11] as L)
}

suspend fun <A, B, C, D, E, F, G, H, I, J, K, L, M> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, blockG: suspend () -> G, blockH: suspend () -> H, blockI: suspend () -> I, blockJ: suspend () -> J, blockK: suspend () -> K, blockL: suspend () -> L, blockM: suspend () -> M, concurrency: Int = defaultConcurrency): Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, blockG, blockH, blockI, blockJ, blockK, blockL, blockM, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F, result[6] as G, result[7] as H, result[8] as I, result[9] as J, result[10] as K, result[11] as L, result[12] as M)
}

suspend fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, blockG: suspend () -> G, blockH: suspend () -> H, blockI: suspend () -> I, blockJ: suspend () -> J, blockK: suspend () -> K, blockL: suspend () -> L, blockM: suspend () -> M, blockN: suspend () -> N, concurrency: Int = defaultConcurrency): Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, blockG, blockH, blockI, blockJ, blockK, blockL, blockM, blockN, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F, result[6] as G, result[7] as H, result[8] as I, result[9] as J, result[10] as K, result[11] as L, result[12] as M, result[13] as N)
}

suspend fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, blockG: suspend () -> G, blockH: suspend () -> H, blockI: suspend () -> I, blockJ: suspend () -> J, blockK: suspend () -> K, blockL: suspend () -> L, blockM: suspend () -> M, blockN: suspend () -> N, blockO: suspend () -> O, concurrency: Int = defaultConcurrency): Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, blockG, blockH, blockI, blockJ, blockK, blockL, blockM, blockN, blockO, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F, result[6] as G, result[7] as H, result[8] as I, result[9] as J, result[10] as K, result[11] as L, result[12] as M, result[13] as N, result[14] as O)
}

suspend fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, blockG: suspend () -> G, blockH: suspend () -> H, blockI: suspend () -> I, blockJ: suspend () -> J, blockK: suspend () -> K, blockL: suspend () -> L, blockM: suspend () -> M, blockN: suspend () -> N, blockO: suspend () -> O, blockP: suspend () -> P, concurrency: Int = defaultConcurrency): Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, blockG, blockH, blockI, blockJ, blockK, blockL, blockM, blockN, blockO, blockP, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F, result[6] as G, result[7] as H, result[8] as I, result[9] as J, result[10] as K, result[11] as L, result[12] as M, result[13] as N, result[14] as O, result[15] as P)
}

suspend fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, blockG: suspend () -> G, blockH: suspend () -> H, blockI: suspend () -> I, blockJ: suspend () -> J, blockK: suspend () -> K, blockL: suspend () -> L, blockM: suspend () -> M, blockN: suspend () -> N, blockO: suspend () -> O, blockP: suspend () -> P, blockQ: suspend () -> Q, concurrency: Int = defaultConcurrency): Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, blockG, blockH, blockI, blockJ, blockK, blockL, blockM, blockN, blockO, blockP, blockQ, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F, result[6] as G, result[7] as H, result[8] as I, result[9] as J, result[10] as K, result[11] as L, result[12] as M, result[13] as N, result[14] as O, result[15] as P, result[16] as Q)
}

suspend fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, blockG: suspend () -> G, blockH: suspend () -> H, blockI: suspend () -> I, blockJ: suspend () -> J, blockK: suspend () -> K, blockL: suspend () -> L, blockM: suspend () -> M, blockN: suspend () -> N, blockO: suspend () -> O, blockP: suspend () -> P, blockQ: suspend () -> Q, blockR: suspend () -> R, concurrency: Int = defaultConcurrency): Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, blockG, blockH, blockI, blockJ, blockK, blockL, blockM, blockN, blockO, blockP, blockQ, blockR, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F, result[6] as G, result[7] as H, result[8] as I, result[9] as J, result[10] as K, result[11] as L, result[12] as M, result[13] as N, result[14] as O, result[15] as P, result[16] as Q, result[17] as R)
}

suspend fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, blockG: suspend () -> G, blockH: suspend () -> H, blockI: suspend () -> I, blockJ: suspend () -> J, blockK: suspend () -> K, blockL: suspend () -> L, blockM: suspend () -> M, blockN: suspend () -> N, blockO: suspend () -> O, blockP: suspend () -> P, blockQ: suspend () -> Q, blockR: suspend () -> R, blockS: suspend () -> S, concurrency: Int = defaultConcurrency): Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, blockG, blockH, blockI, blockJ, blockK, blockL, blockM, blockN, blockO, blockP, blockQ, blockR, blockS, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F, result[6] as G, result[7] as H, result[8] as I, result[9] as J, result[10] as K, result[11] as L, result[12] as M, result[13] as N, result[14] as O, result[15] as P, result[16] as Q, result[17] as R, result[18] as S)
}

suspend fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, blockG: suspend () -> G, blockH: suspend () -> H, blockI: suspend () -> I, blockJ: suspend () -> J, blockK: suspend () -> K, blockL: suspend () -> L, blockM: suspend () -> M, blockN: suspend () -> N, blockO: suspend () -> O, blockP: suspend () -> P, blockQ: suspend () -> Q, blockR: suspend () -> R, blockS: suspend () -> S, blockT: suspend () -> T, concurrency: Int = defaultConcurrency): Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, blockG, blockH, blockI, blockJ, blockK, blockL, blockM, blockN, blockO, blockP, blockQ, blockR, blockS, blockT, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F, result[6] as G, result[7] as H, result[8] as I, result[9] as J, result[10] as K, result[11] as L, result[12] as M, result[13] as N, result[14] as O, result[15] as P, result[16] as Q, result[17] as R, result[18] as S, result[19] as T)
}

suspend fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> parTupled(blockA: suspend () -> A, blockB: suspend () -> B, blockC: suspend () -> C, blockD: suspend () -> D, blockE: suspend () -> E, blockF: suspend () -> F, blockG: suspend () -> G, blockH: suspend () -> H, blockI: suspend () -> I, blockJ: suspend () -> J, blockK: suspend () -> K, blockL: suspend () -> L, blockM: suspend () -> M, blockN: suspend () -> N, blockO: suspend () -> O, blockP: suspend () -> P, blockQ: suspend () -> Q, blockR: suspend () -> R, blockS: suspend () -> S, blockT: suspend () -> T, blockU: suspend () -> U, concurrency: Int = defaultConcurrency): Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> {
    val result = par<Any?>(blockA, blockB, blockC, blockD, blockE, blockF, blockG, blockH, blockI, blockJ, blockK, blockL, blockM, blockN, blockO, blockP, blockQ, blockR, blockS, blockT, blockU, concurrency = concurrency)
    @Suppress("UNCHECKED_CAST")
    return tupleOf(result[0] as A, result[1] as B, result[2] as C, result[3] as D, result[4] as E, result[5] as F, result[6] as G, result[7] as H, result[8] as I, result[9] as J, result[10] as K, result[11] as L, result[12] as M, result[13] as N, result[14] as O, result[15] as P, result[16] as Q, result[17] as R, result[18] as S, result[19] as T, result[20] as U)
}

