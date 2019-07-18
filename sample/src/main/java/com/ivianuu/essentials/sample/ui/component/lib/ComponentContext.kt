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

package com.ivianuu.essentials.sample.ui.component.lib

import android.view.ViewGroup
import com.github.ajalt.timberkt.d
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ComponentContext(
    private val rootViewId: Int,
    private val rootViewProvider: () -> ViewGroup,
    private val buildComponents: BuildContext.() -> Unit
) {

    private val rootId = "root-${UUID.randomUUID()}"
    private var rootNode: ComponentNode? = null

    private val generationTracker = GenerationTracker()

    fun invalidate() {
        GlobalScope.launch(Dispatchers.IO) {
            val runGeneration: Int
            val previousNode: ComponentNode?

            synchronized(this) {
                runGeneration = generationTracker.incrementAndGetNextScheduled()
                previousNode = rootNode
            }

            val newNode = ComponentNode(
                component = RootComponent(rootId),
                containerId = rootViewId,
                children = null
            )

            with(ComponentNodeBuildContext(this@ComponentContext, newNode)) {
                buildComponents()
            }

            d { "prev node $previousNode" }
            d { "new node $newNode" }

            val ops = mutableListOf<() -> Unit>()
            createOps(ops, newNode, previousNode)

            withContext(Dispatchers.Main) {
                if (generationTracker.finishGeneration(runGeneration)) {
                    rootNode = newNode
                    ops.forEach { it() }
                }
            }
        }
    }

    fun cancelAll() {
        generationTracker.finishMaxGeneration()
    }

    private fun createOps(
        ops: MutableList<() -> Unit>,
        newNode: ComponentNode?,
        oldNode: ComponentNode?
    ) {
        if (newNode == oldNode) return

        if (newNode != null && oldNode == null) {
            ops.add {
                newNode.component.addOrUpdate(
                    rootViewProvider().findViewById(newNode.containerId!!)
                )
            }
        } else if (newNode != null && oldNode != null) {
            if (newNode.component != oldNode.component) {
                if (newNode.component.viewType == oldNode.component.viewType) {
                    ops.add {
                        newNode.component.addOrUpdate(
                            rootViewProvider().findViewById(newNode.containerId!!)
                        )
                    }
                } else {
                    ops.add {
                        newNode.component.addOrUpdate(
                            rootViewProvider().findViewById(newNode.containerId!!)
                        )
                        oldNode.component.removeIfPossible(
                            rootViewProvider().findViewById(newNode.containerId)
                        )
                    }
                }
            }
        } else if (newNode == null && oldNode != null) {
            ops.add {
                oldNode.component.removeIfPossible(
                    rootViewProvider().findViewById(oldNode.containerId!!)
                )
            }
        }

        val processedNodes = mutableListOf<ComponentNode>()

        newNode?.children?.forEach { newChildrenNode ->
            processedNodes.add(newChildrenNode)
            val oldChildrenNode = oldNode?.children?.firstOrNull {
                it.component.id == newChildrenNode.component.id
            }
            if (oldChildrenNode != null) processedNodes.add(oldChildrenNode)
            createOps(ops, newChildrenNode, oldChildrenNode)
        }

        oldNode?.children?.forEach { oldChildrenNode ->
            if (!processedNodes.contains(oldChildrenNode)) {
                createOps(ops, null, oldChildrenNode)
            }
        }
    }


    private class GenerationTracker {

        @Volatile
        private var maxScheduledGeneration: Int = 0
        @Volatile
        private var maxFinishedGeneration: Int = 0

        fun incrementAndGetNextScheduled(): Int = synchronized(this) {
            ++maxScheduledGeneration
        }

        fun finishMaxGeneration(): Boolean = synchronized(this) {
            val isInterrupting = hasUnfinishedGeneration()
            maxFinishedGeneration = maxScheduledGeneration
            return@finishMaxGeneration isInterrupting
        }

        fun hasUnfinishedGeneration(): Boolean = synchronized(this) {
            maxScheduledGeneration > maxFinishedGeneration
        }

        fun finishGeneration(runGeneration: Int): Boolean = synchronized(this) {
            val isLatestGeneration =
                maxScheduledGeneration == runGeneration && runGeneration > maxFinishedGeneration

            if (isLatestGeneration) {
                maxFinishedGeneration = runGeneration
            }

            return@synchronized isLatestGeneration
        }
    }

}