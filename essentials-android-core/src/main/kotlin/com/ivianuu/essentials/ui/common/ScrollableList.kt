package com.ivianuu.essentials.ui.common

import android.content.Context
import androidx.compose.Composable
import androidx.compose.CompositionReference
import androidx.compose.FrameManager
import androidx.compose.Observe
import androidx.compose.compositionReference
import androidx.compose.onDispose
import androidx.compose.remember
import androidx.ui.core.Constraints
import androidx.ui.core.ContextAmbient
import androidx.ui.core.DrawClipToBounds
import androidx.ui.core.LayoutDirection
import androidx.ui.core.LayoutNode
import androidx.ui.core.Measurable
import androidx.ui.core.MeasureScope
import androidx.ui.core.MeasuringIntrinsicsMeasureBlocks
import androidx.ui.core.Modifier
import androidx.ui.core.Ref
import androidx.ui.core.subcomposeInto
import androidx.ui.unit.IntPx
import androidx.ui.unit.Px
import androidx.ui.unit.ipx
import androidx.ui.unit.max
import androidx.ui.unit.px
import androidx.ui.unit.round
import com.ivianuu.essentials.ui.core.Axis

// todo remove

@Composable
fun <T> ScrollableList(
    items: List<T>,
    modifier: Modifier = Modifier.None,
    direction: Axis = Axis.Vertical,
    scrollableState: ScrollableState = RetainedScrollableState(),
    enabled: Boolean = true,
    itemCallback: @Composable (Int, T) -> Unit
) {
    ScrollableList(
        itemCount = items.size,
        modifier = modifier,
        direction = direction,
        scrollableState = scrollableState,
        enabled = enabled,
        itemCallback = { itemCallback(it, items[it]) }
    )
}

@Composable
fun ScrollableList(
    itemCount: Int,
    modifier: Modifier = Modifier.None,
    direction: Axis = Axis.Vertical,
    scrollableState: ScrollableState = RetainedScrollableState(),
    enabled: Boolean = true,
    itemCallback: @Composable (Int) -> Unit
) {
    ScrollableList(
        modifier = modifier,
        direction = direction,
        scrollableState = scrollableState,
        enabled = enabled,
        itemCallbackFactory = { index ->
            if (index in 0 until itemCount) (@Composable { itemCallback(index) })
            else null
        }
    )
}

@Composable
fun ScrollableList(
    modifier: Modifier = Modifier.None,
    direction: Axis = Axis.Vertical,
    scrollableState: ScrollableState = RetainedScrollableState(),
    enabled: Boolean = true,
    itemCallbackFactory: (Int) -> @Composable (() -> Unit)?
) {
    val state = remember { ScrollableListState() }
    state.context = ContextAmbient.current
    state.compositionRef = compositionReference()
    state.forceRecompose = true
    state.composableFactory = itemCallbackFactory
    state.scrollableState = scrollableState
    state.direction = direction

    onDispose { state.dispose() }

    Scrollable(
        state = state.scrollableState,
        direction = direction,
        enabled = enabled
    ) {
        LayoutNode(
            modifier = modifier + DrawClipToBounds,
            ref = state.rootNodeRef,
            measureBlocks = state.measureBlocks
        )

        Observe {
            scrollableState.value // force recompose on reads
            state.onScroll()
        }
    }

    state.recomposeIfAttached()
}

private class ScrollableListState {

    val rootNodeRef = Ref<LayoutNode>()
    var forceRecompose = false
    lateinit var compositionRef: CompositionReference
    lateinit var context: Context
    lateinit var scrollableState: ScrollableState

    lateinit var direction: Axis

    val rootNode get() = rootNodeRef.value!!

    val measureBlocks = ListMeasureBlocks()

    var composableFactory: (Int) -> @Composable (() -> Unit)? = { null }
        set(value) {
            if (field == value) return
            field = value
            if (rootNodeRef.value != null) {
                for (child in rootNode.layoutChildren.toList()) {
                    val newComposable = composableFactory(child.state.index)
                    if (newComposable != null) {
                        child.state.composable = newComposable
                    } else {
                        removeChild(child)
                    }
                }
            }
        }

    private val childStates = mutableMapOf<LayoutNode, ChildState>()

    private val LayoutNode.state get() = childStates.getValue(this)

    private var version = 0

    fun onScroll() {
        rootNodeRef.value?.requestRemeasure()
    }

    fun dispose() {
        childStates.values.forEach { it.dispose() }
    }

    fun recomposeIfAttached() {
        if (rootNodeRef.value?.owner != null) {
            recomposeAllChildren()
        }
    }

    private fun recomposeAllChildren() {
        rootNode.layoutChildren.forEach {
            it.state.compose()
        }
        forceRecompose = false
    }

    private inner class ListMeasureBlocks : LayoutNode.NoIntrinsicsMeasureBlocks("Unsupported") {
        override fun measure(
            measureScope: MeasureScope,
            measurables: List<Measurable>,
            constraints: Constraints,
            layoutDirection: LayoutDirection
        ): MeasureScope.LayoutResult {
            version++

            if (forceRecompose) {
                rootNode.ignoreModelReads { recomposeAllChildren() }
                FrameManager.nextFrame()
            }

            val cacheSize = with(measureScope) { 250.ipx/*.toIntPx()*/ }

            lateinit var scrollPosition: Px
            rootNode.ignoreModelReads { scrollPosition = scrollableState.value }

            val targetStartScrollPosition = max(scrollPosition - cacheSize, 0.px)

            val viewportMainAxisSize: IntPx
            val viewportCrossAxisSize: IntPx
            val childConstraints: Constraints

            when (direction) {
                Axis.Horizontal -> {
                    viewportMainAxisSize = constraints.maxWidth
                    viewportCrossAxisSize = constraints.maxHeight
                    childConstraints = Constraints.fixedHeight(viewportCrossAxisSize)
                }
                Axis.Vertical -> {
                    viewportMainAxisSize = constraints.maxHeight
                    viewportCrossAxisSize = constraints.maxWidth
                    childConstraints = Constraints.fixedWidth(viewportCrossAxisSize)
                }
            }

            val targetEndScrollPosition = scrollPosition + viewportMainAxisSize + cacheSize

            var leadingGarbage = 0
            var trailingGarbage = 0
            var reachedEnd = false

            if (rootNode.layoutChildren.isEmpty()) {
                if (addChild(0) == null) {
                    // There are no children.
                    return doLayout(
                        measureScope,
                        viewportMainAxisSize,
                        viewportCrossAxisSize,
                        scrollPosition
                    )
                }
            }

            var leadingChildWithLayout: LayoutNode? = null
            var trailingChildWithLayout: LayoutNode? = null

            var earliestUsefulChild = rootNode.layoutChildren.minBy { it.state.index }
            var earliestScrollPosition = earliestUsefulChild!!.state.layoutOffset

            while (earliestScrollPosition > targetStartScrollPosition) {
                earliestUsefulChild = insertAndLayoutLeadingChild(childConstraints)

                if (earliestUsefulChild == null) {
                    val firstChild = firstChild!!
                    firstChild.state.layoutOffset = 0.px

                    if (targetStartScrollPosition == 0.px) {
                        earliestUsefulChild = firstChild
                        leadingChildWithLayout = earliestUsefulChild
                        trailingChildWithLayout = earliestUsefulChild
                        break
                    } else {
                        rootNode.ignoreModelReads {
                            scrollableState.correctBy(-scrollPosition)
                        }
                        return doLayout(
                            measureScope,
                            viewportMainAxisSize,
                            viewportCrossAxisSize,
                            scrollPosition
                        )
                    }
                }

                val firstChildScrollPosition = earliestScrollPosition - firstChild!!.height
                earliestUsefulChild.state.layoutOffset = firstChildScrollPosition
                leadingChildWithLayout = earliestUsefulChild
                trailingChildWithLayout = earliestUsefulChild
                earliestScrollPosition = earliestUsefulChild.state.layoutOffset
            }

            if (leadingChildWithLayout == null) {
                earliestUsefulChild!!.measure(childConstraints)
                leadingChildWithLayout = earliestUsefulChild
                trailingChildWithLayout = earliestUsefulChild
            }

            var inLayoutRange = true
            var child = earliestUsefulChild
            var index = child!!.state.index
            var endScrollPosition = child.state.layoutOffset + child.height
            fun advance(src: String): Boolean {
                if (child == trailingChildWithLayout) inLayoutRange = false
                child = childAfter(child!!)
                if (child == null) inLayoutRange = false
                index += 1
                if (!inLayoutRange) {
                    if (child == null || child!!.state.index != index) {
                        // We are missing a child. Insert it (and lay it out) if possible.
                        child = addChild(trailingChildWithLayout!!.state.index + 1)
                        child?.doMeasure(childConstraints)
                        if (child == null) {
                            // We have run out of children.
                            return false
                        }
                    } else {
                        // Lay out the child.
                        child!!.doMeasure(childConstraints)
                    }
                    trailingChildWithLayout = child
                }
                child!!.state.layoutOffset = endScrollPosition
                endScrollPosition = child!!.state.layoutOffset + child!!.height
                return true
            }

            while (endScrollPosition < targetStartScrollPosition) {
                leadingGarbage += 1
                if (!advance("first child that ends")) {
                    collectGarbage(leadingGarbage - 1, 0)
                    return doLayout(
                        measureScope,
                        viewportMainAxisSize,
                        viewportCrossAxisSize,
                        scrollPosition
                    )
                }
            }

            while (endScrollPosition < targetEndScrollPosition) {
                if (!advance("first child after our end")) {
                    reachedEnd = true
                    break
                }
            }

            if (child != null) {
                child = childAfter(child!!)
                while (child != null) {
                    trailingGarbage += 1
                    child = childAfter(child!!)
                }
            }

            collectGarbage(leadingGarbage, trailingGarbage)

            rootNode.ignoreModelReads {
                val estimatedMaxScrollPosition = if (reachedEnd) max(
                    endScrollPosition - viewportMainAxisSize,
                    0.px
                ) else Px.Infinity
                if (estimatedMaxScrollPosition != scrollableState.maxValue) {
                    scrollableState.updateBounds(maxValue = estimatedMaxScrollPosition)
                }
            }

            return doLayout(
                measureScope,
                viewportMainAxisSize,
                viewportCrossAxisSize,
                scrollPosition
            )
        }
    }

    private fun LayoutNode.doMeasure(constraints: Constraints) {
        try {
            measure(constraints)
        } catch (e: Exception) {
        }
    }

    private fun getChild(index: Int): LayoutNode? {
        if (index < 0) return null
        return rootNode.layoutChildren.singleOrNull { it.state.index == index }
    }

    private fun addChild(index: Int): LayoutNode? {
        val composable = composableFactory(index)
        if (composable == null) return null

        val childNode = LayoutNode()

        childNode.measureBlocks =
            MeasuringIntrinsicsMeasureBlocks { measurables, constraints, _ ->
                val childConstraints = Constraints(
                    minWidth = constraints.minWidth,
                    maxWidth = constraints.maxWidth
                )
                val placeables = measurables.map { measurable ->
                    measurable.measure(childConstraints)
                }
                val width = (placeables.maxBy { it.width.value }?.width ?: 0.ipx)
                    .coerceAtLeast(constraints.minWidth)
                val height = placeables.sumBy { it.height.value }.ipx.coerceIn(
                    constraints.minHeight,
                    constraints.maxHeight
                )
                layout(width, height) {
                    var offset = 0.ipx
                    placeables.forEach { placeable ->
                        when (direction) {
                            Axis.Horizontal -> {
                                placeable.place(offset, 0.ipx)
                                offset += placeable.width
                            }
                            Axis.Vertical -> {
                                placeable.place(0.ipx, offset)
                                offset += placeable.height
                            }
                        }
                    }
                }
            }

        rootNode.insertAt(0, childNode)

        val childState = ChildState(childNode, index, composable)

        childStates[childNode] = childState

        childState.compose()

        return childNode
    }

    private fun getOrAddChild(index: Int): LayoutNode? = getChild(index) ?: addChild(index)

    private fun removeChild(child: LayoutNode) {
        child.state.dispose()
        childStates.remove(child)
        rootNode.removeAt(
            rootNode.layoutChildren.indexOf(child),
            1
        )
    }

    private fun childAfter(child: LayoutNode): LayoutNode? =
        getChild(child.state.index + 1)

    fun collectGarbage(leadingGarbage: Int, trailingGarbage: Int) {
        repeat(leadingGarbage) {
            rootNode.layoutChildren
                .minBy { it.state.index }
                ?.let { removeChild(it) }
        }
        repeat(trailingGarbage) {
            rootNode.layoutChildren
                .maxBy { it.state.index }
                ?.let { removeChild(it) }
        }
    }

    private val firstChild get() = rootNode.layoutChildren.minBy { it.state.index }

    private fun insertAndLayoutLeadingChild(constraints: Constraints): LayoutNode? {
        val index = firstChild?.let { it.state.index - 1 } ?: 0
        val child = getOrAddChild(index) ?: return null
        child.measure(constraints)
        return child
    }

    private fun doLayout(
        measureScope: MeasureScope,
        viewportMainAxisSize: IntPx,
        viewportCrossAxisSize: IntPx,
        position: Px
    ): MeasureScope.LayoutResult {
        val width: IntPx
        val height: IntPx
        when (direction) {
            Axis.Horizontal -> {
                width = viewportMainAxisSize
                height = viewportCrossAxisSize
            }
            Axis.Vertical -> {
                width = viewportCrossAxisSize
                height = viewportMainAxisSize
            }
        }
        return measureScope.layout(width = width, height = height) {
            rootNode.layoutChildren
                .forEach { child ->
                    when (direction) {
                        Axis.Horizontal -> {
                            child.place(
                                x = (child.state.layoutOffset - position).round(),
                                y = 0.ipx
                            )
                        }
                        Axis.Vertical -> {
                            child.place(
                                x = 0.ipx,
                                y = (child.state.layoutOffset - position).round()
                            )
                        }
                    }
                }
        }
    }

    private inner class ChildState(
        val node: LayoutNode,
        val index: Int,
        var composable: @Composable () -> Unit
    ) {

        var layoutOffset = 0.px

        private val composition = subcomposeInto(node, context, compositionRef) {
            composable()
        }

        fun compose() {
            composition.compose()
        }

        fun dispose() {
            composition.dispose()
        }
    }
}