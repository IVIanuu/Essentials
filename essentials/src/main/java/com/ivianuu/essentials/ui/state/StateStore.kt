package com.ivianuu.essentials.ui.state

import com.ivianuu.essentials.util.ext.behaviorSubject
import com.ivianuu.essentials.util.ext.requireValue
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import com.uber.autodispose.subscribeBy
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * State store
 */
internal class StateStore<S : Any>(scopeProvider: ScopeProvider) {

    private val subject = behaviorSubject<S>()

    private val flushQueueSubject = behaviorSubject<Unit>()

    private val jobs = Jobs<S>()

    val observable: Observable<S> = subject.distinctUntilChanged()

    internal val state: S
        get() {
            requireInitialState()
            return subject.requireValue()
        }

    private val hasInitialState = subject.value != null

    init {
        flushQueueSubject
            .observeOn(Schedulers.newThread())
            // We don't want race conditions with setting the state on multiple background threads
            // simultaneously in which two state reducers get the same initial state to reduce.
            .autoDisposable(scopeProvider)
            .subscribeBy(
                onNext = { flushQueues() },
                onError = { handleError(it) }
            )
    }

    fun setInitialState(initialState: S) {
        if (hasInitialState) throw IllegalStateException("initial state already set")
        subject.onNext(initialState)
    }

    fun get(block: (S) -> Unit) {
        requireInitialState()
        jobs.enqueueGetStateBlock(block)
        flushQueueSubject.onNext(Unit)
    }

    fun set(stateReducer: S.() -> S) {
        if (!hasInitialState) throw IllegalStateException("set initial state must be called first")
        jobs.enqueueSetStateBlock(stateReducer)
        flushQueueSubject.onNext(Unit)
    }

    private fun flushQueues() {
        flushSetStateQueue()
        val block = jobs.dequeueGetStateBlock() ?: return
        block(state)
        flushQueues()
    }

    private fun flushSetStateQueue() {
        val blocks = jobs.dequeueAllSetStateBlocks() ?: return

        blocks
            .fold(state) { state, reducer -> state.reducer() }
            .run { subject.onNext(this) }
    }

    private fun handleError(throwable: Throwable) {
        // Throw the root cause to remove all of the rx stacks.
        // TODO: better error handling
        var e: Throwable? = throwable
        while (e?.cause != null) e = e.cause
        e?.let { throw it }
    }

    private fun requireInitialState() {
        if (!hasInitialState) throw IllegalStateException("set initial state must be called first")
    }

    private class Jobs<S> {

        private val getStateQueue = LinkedList<(state: S) -> Unit>()
        private var setStateQueue = LinkedList<S.() -> S>()

        @Synchronized
        fun enqueueGetStateBlock(block: (state: S) -> Unit) {
            getStateQueue.push(block)
        }

        @Synchronized
        fun enqueueSetStateBlock(block: S.() -> S) {
            setStateQueue.push(block)
        }

        @Synchronized
        fun dequeueGetStateBlock(): ((state: S) -> Unit)? {
            if (getStateQueue.isEmpty()) return null

            return getStateQueue.removeFirst()
        }

        @Synchronized
        fun dequeueAllSetStateBlocks(): List<(S.() -> S)>? {
            // do not allocate empty queue for no-op flushes
            if (setStateQueue.isEmpty()) return null

            val queue = setStateQueue
            setStateQueue = LinkedList()
            return queue
        }
    }
}