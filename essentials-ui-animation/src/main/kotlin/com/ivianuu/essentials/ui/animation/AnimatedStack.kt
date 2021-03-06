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

package com.ivianuu.essentials.ui.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.key
import androidx.compose.ui.*
import com.ivianuu.essentials.coroutines.*
import com.ivianuu.essentials.ui.animation.transition.*
import com.ivianuu.injekt.scope.*
import kotlinx.coroutines.*

@Composable fun <T> AnimatedBox(
  current: T,
  modifier: Modifier = Modifier,
  transition: StackTransition = CrossFadeStackTransition(),
  itemContent: @Composable (T) -> Unit
) {
  AnimatedStack(
    modifier = modifier,
    items = listOf(current),
    transition = transition,
    itemContent = itemContent
  )
}

@Composable fun <T> AnimatedStack(
  items: List<T>,
  modifier: Modifier = Modifier,
  transition: StackTransition = LocalStackTransition.current,
  itemContent: @Composable (T) -> Unit
) {
  val state = remember { AnimatedStackWithItemsState(transition, itemContent, items) }
  SideEffect {
    state.update(transition, itemContent, items)
  }
  AnimatedStack(modifier = modifier, children = state.children)
}

private class AnimatedStackWithItemsState<T>(
  transition: StackTransition,
  itemContent: @Composable (T) -> Unit,
  items: List<T>
) {
  var children by mutableStateOf(emptyList<AnimatedStackChild<T>>())
    private set

  init {
    update(transition, itemContent, items)
  }

  fun update(transition: StackTransition, itemContent: @Composable (T) -> Unit, items: List<T>) {
    children = items.map { getOrCreateChild(it, transition, itemContent) }
  }

  @Suppress("UNCHECKED_CAST")
  private fun getOrCreateChild(
    item: T,
    transition: StackTransition,
    itemContent: @Composable (T) -> Unit
  ): AnimatedStackChild<T> {
    val content: @Composable () -> Unit = { itemContent(item) }
    children.firstOrNull { it.key == item }
      ?.let {
        it.enterTransition = transition
        it.exitTransition = transition
        it.content = content
        return it
      }
    return AnimatedStackChild(key = item, transition = transition, content = content)
  }
}

@Composable fun <T> AnimatedStack(
  modifier: Modifier = Modifier,
  children: List<AnimatedStackChild<T>>,
) {
  val scope = rememberCoroutineScope()
  val defaultTransition = LocalStackTransition.current
  val state = remember { AnimatedStackState(scope, children, defaultTransition) }

  SideEffect {
    state.defaultTransition = defaultTransition
    state.updateChildren(children)
  }

  Box(modifier = modifier, propagateMinConstraints = true) {
    state.visibleChildren.toList().forEach { child ->
      key(child.key) { child.Content() }
    }

    state.animationOverlays.toList().forEach { overlay ->
      key(overlay) { overlay() }
    }
  }
}

@Stable class AnimatedStackChild<T>(
  val key: T,
  opaque: Boolean = false,
  enterTransition: StackTransition? = null,
  exitTransition: StackTransition? = null,
  content: @Composable () -> Unit
) {
  var opaque by mutableStateOf(opaque)
  var enterTransition by mutableStateOf(enterTransition)
  var exitTransition by mutableStateOf(exitTransition)
  var content by mutableStateOf(content)
  val elementStore = AnimationElementStore()

  constructor(
    key: T,
    opaque: Boolean = false,
    transition: StackTransition? = null,
    content: @Composable () -> Unit
  ) : this(key, opaque, transition, transition, content)

  @Composable internal fun Content() {
    CompositionLocalProvider(LocalAnimatedStackChild provides this@AnimatedStackChild) {
      Box(modifier = Modifier.animationElement(ContentAnimationElementKey)) {
        content()
      }
    }
  }
}

val LocalAnimatedStackChild = staticCompositionLocalOf<AnimatedStackChild<*>> {
  error("No stack child provided")
}

@Stable class AnimatedStackState<T>(
  val scope: CoroutineScope,
  private var children: List<AnimatedStackChild<T>>,
  var defaultTransition: StackTransition,
) {
  internal val animationOverlays = mutableStateListOf<@Composable () -> Unit>()
  internal val visibleChildren = mutableStateListOf<AnimatedStackChild<T>>()
  internal val runningTransactions = mutableMapOf<T, AnimatedStackTransaction<T>>()

  init {
    children
      .filterVisible()
      .forEach {
        performChange(
          from = null,
          to = it,
          isPush = true,
          transition = NoOpStackTransition
        )
      }
  }

  internal fun updateChildren(newChildren: List<AnimatedStackChild<T>>) {
    // do not allow pushing the same child twice
    newChildren
      .groupBy { it }
      .forEach {
        check(it.value.size == 1) {
          "Trying to push the same child multiple times $it"
        }
      }

    val oldChildren = children.toList()
    val oldVisibleChildren = oldChildren.filterVisible()

    children = newChildren

    val newVisibleChildren = newChildren.filterVisible()

    if (oldVisibleChildren == newVisibleChildren) return

    val oldTopChild = oldVisibleChildren.lastOrNull()
    val newTopChild = newVisibleChildren.lastOrNull()

    // check if we should animate the top children
    val replacingTopChildren = newTopChild != null && oldTopChild != newTopChild

    // Remove all visible children which shouldn't be visible anymore
    // from top to bottom
    oldVisibleChildren
      .dropLast(if (replacingTopChildren) 1 else 0)
      .reversed()
      .filterNot { it in newVisibleChildren }
      .forEach { child ->
        val localTransition = child.exitTransition
          ?: defaultTransition
        performChange(
          from = child,
          to = null,
          isPush = false,
          transition = localTransition
        )
      }

    // Add any new children to the stack from bottom to top
    newVisibleChildren
      .dropLast(if (replacingTopChildren) 1 else 0)
      .filterNot { it in oldVisibleChildren }
      .forEachIndexed { i, child ->
        val localTransition =
          child.enterTransition ?: defaultTransition
        performChange(
          from = newVisibleChildren.getOrNull(i - 1),
          to = child,
          isPush = true,
          transition = localTransition
        )
      }

    val isPush = newTopChild !in oldChildren

    // Replace the old visible top with the new one
    if (replacingTopChildren) {
      val localTransition =
        if (isPush) newTopChild?.enterTransition ?: defaultTransition
        else oldTopChild?.exitTransition ?: defaultTransition
      performChange(
        from = oldTopChild,
        to = newTopChild,
        isPush = isPush,
        transition = localTransition
      )
    }
  }

  private fun List<AnimatedStackChild<T>>.filterVisible(): List<AnimatedStackChild<T>> {
    val visibleChildren = mutableListOf<AnimatedStackChild<T>>()

    for (child in reversed()) {
      visibleChildren.add(0, child)
      if (!child.opaque) break
    }

    return visibleChildren
  }

  private fun performChange(
    from: AnimatedStackChild<T>?,
    to: AnimatedStackChild<T>?,
    isPush: Boolean,
    transition: StackTransition
  ) {
    from?.let { runningTransactions[it.key]?.cancel() }
    val transaction = AnimatedStackTransaction(
      from, to, isPush, transition, this,
      from !in children
    )
    runningTransactions[transaction.transactionKey] = transaction
    transaction.execute()
  }
}

internal class AnimatedStackTransaction<T>(
  private val from: AnimatedStackChild<T>?,
  private val to: AnimatedStackChild<T>?,
  private val isPush: Boolean,
  private val transition: StackTransition,
  private val state: AnimatedStackState<T>,
  private val forceFromRemoval: Boolean
) {
  val transactionKey = when {
    to != null -> to.key
    from != null -> from.key
    else -> throw AssertionError()
  }

  private val removesFrom: Boolean
    get() = forceFromRemoval || (from != null && (!isPush || !to!!.opaque))

  private var job: Job? = null

  private var isCancelled = false
  private var isCompleted = false

  fun execute() {
    job = state.scope.launch(start = CoroutineStart.UNDISPATCHED) {
      runWithCleanup(
        block = {
          val transitionScope = object : StackTransitionScope, CoroutineScope by this {
            override val state: AnimatedStackState<*>
              get() = this@AnimatedStackTransaction.state
            override val isPush: Boolean
              get() = this@AnimatedStackTransaction.isPush
            override val from: AnimatedStackChild<*>?
              get() = this@AnimatedStackTransaction.from
            override val to: AnimatedStackChild<*>?
              get() = this@AnimatedStackTransaction.to
            override val fromWillBeRemoved: Boolean
              get() = removesFrom

            override fun attachTo() {
              this@AnimatedStackTransaction.attachTo()
            }

            override fun detachFrom() {
              this@AnimatedStackTransaction.removeFrom()
            }
          }
          transition(transitionScope)
        },
        cleanup = { complete() }
      )
    }
  }

  fun cancel() {
    if (isCancelled) return
    isCancelled = true
    job?.cancel()
    job = null
    complete()
  }

  private fun attachTo() {
    if (to == null) return
    val oldToIndex = state.visibleChildren.indexOf(to)
    val fromIndex = state.visibleChildren.indexOf(from)
    val toIndex = when {
      fromIndex != -1 -> if (isPush) fromIndex + 1 else fromIndex
      oldToIndex != -1 -> oldToIndex
      else -> state.visibleChildren.size
    }
    if (oldToIndex != toIndex) {
      if (oldToIndex != -1) state.visibleChildren.removeAt(oldToIndex)
      state.visibleChildren.add(toIndex, to)
    }
  }

  private fun removeFrom() {
    if (from == null || !removesFrom) return
    state.visibleChildren -= from
  }

  private fun complete() {
    if (isCompleted) return
    isCompleted = true
    job?.cancel()
    job = null
    if (to != null && to !in state.visibleChildren) attachTo()
    if (removesFrom && from in state.visibleChildren) removeFrom()
    state.runningTransactions -= transactionKey
  }
}
