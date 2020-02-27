package com.ivianuu.essentials.ui.common

import androidx.compose.Composable
import androidx.compose.Compose
import androidx.compose.CompositionReference
import androidx.compose.Context
import androidx.compose.FrameManager
import androidx.compose.Observe
import androidx.compose.composer
import androidx.compose.compositionReference
import androidx.compose.onDispose
import androidx.compose.remember
import androidx.ui.core.*
import androidx.ui.foundation.shape.RectangleShape
import androidx.ui.unit.*
import com.github.ajalt.timberkt.d
import com.ivianuu.essentials.ui.core.Axis
import com.ivianuu.essentials.ui.core.retain
import kotlin.ranges.coerceAtLeast
import kotlin.ranges.coerceIn

@Composable
fun <T> ScrollableList(
    items: List<T>,
    modifier: Modifier = Modifier.None,
    direction: Axis = Axis.Vertical,
    position: ScrollPosition = retain { ScrollPosition() },
    enabled: Boolean = true,
    itemCallback: @Composable (Int, T) -> Unit
) {
    ScrollableList(
        itemCount = items.size,
        modifier = modifier,
        direction = direction,
        position = position,
        enabled = enabled,
        itemCallback = { itemCallback(it, items[it]) }
    )
}

@Composable
fun ScrollableList(
    itemCount: Int,
    modifier: Modifier = Modifier.None,
    direction: Axis = Axis.Vertical,
    position: ScrollPosition = retain { ScrollPosition() },
    enabled: Boolean = true,
    itemCallback: @Composable (Int) -> Unit
) {
    ScrollableList(
        modifier = modifier,
        direction = direction,
        position = position,
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
    position: ScrollPosition = retain { ScrollPosition() },
    enabled: Boolean = true,
    itemCallbackFactory: (Int) -> @Composable (() -> Unit)?
) {
    val state = remember { ScrollableListState() }
    state.context = ContextAmbient.current
    state.compositionRef = compositionReference()
    state.forceRecompose = true
    state.composableFactory = itemCallbackFactory
    state.position = position
    state.direction = direction

    onDispose { state.dispose() }

    Scrollable(
        position = state.position,
        direction = direction,
        enabled = enabled
    ) {
        Clip(shape = RectangleShape) {
            composer.emit<LayoutNode>(
                key = 0,
                ctor = { LayoutNode() },
                update = {
                    node.modifier = modifier
                    node.ref = state.rootNodeRef
                    node.measureBlocks = state.measureBlocks
                }
            )
        }

        Observe {
            position.value // force recompose on reads
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
    lateinit var position: ScrollPosition

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
            constraints: Constraints
        ): MeasureScope.LayoutResult {
            version++

            if (forceRecompose) {
                rootNode.ignoreModelReads { recomposeAllChildren() }
                FrameManager.nextFrame()
            }

            val cacheSize = with(measureScope) { 250.ipx/*.toIntPx()*/ }

            lateinit var scrollPosition: Px
            rootNode.ignoreModelReads { scrollPosition = position.value }

            val targetStartScrollPosition = max(scrollPosition - cacheSize, 0.px)

            d { "begin measure $version scroll pos $scrollPosition constraints $constraints" }

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
                    d { "end measure $version with no children" }
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
                            position.correctBy(-scrollPosition)
                        }
                        d { "end measure $version ran out of children with correction $scrollPosition" }
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
                    d { "end measure $version todo" }
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
                if (estimatedMaxScrollPosition != position.maxValue) {
                    d { "update max scroll position $estimatedMaxScrollPosition" }
                    position.updateBounds(maxValue = estimatedMaxScrollPosition)
                }
            }

            d { "end measure $version" }

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
        d { "create child for $index result is success ${composable != null}" }
        if (composable == null) return null

        val childNode = LayoutNode()

        childNode.measureBlocks =
            MeasuringIntrinsicsMeasureBlocks { measurables, constraints ->
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

        rootNode.emitInsertAt(0, childNode)

        val childState = ChildState(childNode, index, composable)

        childStates[childNode] = childState

        childState.compose()

        return childNode
    }

    private fun getOrAddChild(index: Int): LayoutNode? = getChild(index) ?: addChild(index)

    private fun removeChild(child: LayoutNode) {
        child.state.dispose()
        childStates.remove(child)
        rootNode.emitRemoveAt(
            rootNode.layoutChildren.indexOf(child),
            1
        )
    }

    private fun childAfter(child: LayoutNode): LayoutNode? =
        getChild(child.state.index + 1)

    fun collectGarbage(leadingGarbage: Int, trailingGarbage: Int) {
        d { "collect garbage leading $leadingGarbage trailing $trailingGarbage" }
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
        d { "try insert and layout leading child at $index" }
        val child = getOrAddChild(index) ?: return null
        d { "insert and layout leading child $index" }
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
            d { "place items ${rootNode.layoutChildren.map { it.state.index }.sorted()}" }
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

        private val composition = Compose.subcomposeInto(node, context, compositionRef) {
            OnlyOnce(composable)
        }

        fun compose() {
            composition.compose()
        }

        fun dispose() {
            composition.dispose()
        }

        @Composable
        private fun OnlyOnce(children: @Composable () -> Unit) {
            children()
        }
    }
}

/**
 * Default [LayoutNode.MeasureBlocks] object implementation, providing intrinsic measurements
 * that use the measure block replacing the measure calls with intrinsic measurement calls.
 */
private fun MeasuringIntrinsicsMeasureBlocks(measureBlock: MeasureBlock) =
        object : LayoutNode.MeasureBlocks {
            override fun measure(
                    measureScope: MeasureScope,
                    measurables: List<Measurable>,
                    constraints: Constraints
            ) = measureScope.measureBlock(measurables, constraints)
            override fun minIntrinsicWidth(
                    density: Density,
                    measurables: List<IntrinsicMeasurable>,
                    h: IntPx
            ) = density.MeasuringMinIntrinsicWidth(measureBlock, measurables, h)
            override fun minIntrinsicHeight(
                    density: Density,
                    measurables: List<IntrinsicMeasurable>,
                    w: IntPx
            ) = density.MeasuringMinIntrinsicHeight(measureBlock, measurables, w)
            override fun maxIntrinsicWidth(
                    density: Density,
                    measurables: List<IntrinsicMeasurable>,
                    h: IntPx
            ) = density.MeasuringMaxIntrinsicWidth(measureBlock, measurables, h)
            override fun maxIntrinsicHeight(
                    density: Density,
                    measurables: List<IntrinsicMeasurable>,
                    w: IntPx
            ) = density.MeasuringMaxIntrinsicHeight(measureBlock, measurables, w)

            override fun toString(): String {
                // this calls simpleIdentityToString on measureBlock because it is typically a lambda,
                // which has a useless toString that doesn't hint at the source location
                return simpleIdentityToString(
                        this,
                        "MeasuringIntrinsicsMeasureBlocks"
                ) + "{ measureBlock=${simpleIdentityToString(measureBlock)} }"
            }
        }

/**
 * Default implementation for the min intrinsic width of a layout. This works by running the
 * measure block with measure calls replaced with intrinsic measurement calls.
 */
private inline fun Density.MeasuringMinIntrinsicWidth(
        measureBlock: MeasureBlock,
        measurables: List<IntrinsicMeasurable>,
        h: IntPx
): IntPx {
    val mapped = measurables.map {
        DefaultIntrinsicMeasurable(it, IntrinsicMinMax.Min, IntrinsicWidthHeight.Width)
    }
    val constraints = Constraints(maxHeight = h)
    val layoutReceiver = IntrinsicsMeasureScope(this)
    val layoutResult = layoutReceiver.measureBlock(mapped, constraints)
    return layoutResult.width
}

/**
 * Default implementation for the min intrinsic width of a layout. This works by running the
 * measure block with measure calls replaced with intrinsic measurement calls.
 */
private inline fun Density.MeasuringMinIntrinsicHeight(
        measureBlock: MeasureBlock,
        measurables: List<IntrinsicMeasurable>,
        w: IntPx
): IntPx {
    val mapped = measurables.map {
        DefaultIntrinsicMeasurable(it, IntrinsicMinMax.Min, IntrinsicWidthHeight.Height)
    }
    val constraints = Constraints(maxWidth = w)
    val layoutReceiver = IntrinsicsMeasureScope(this)
    val layoutResult = layoutReceiver.measureBlock(mapped, constraints)
    return layoutResult.height
}

/**
 * Default implementation for the max intrinsic width of a layout. This works by running the
 * measure block with measure calls replaced with intrinsic measurement calls.
 */
private inline fun Density.MeasuringMaxIntrinsicWidth(
        measureBlock: MeasureBlock,
        measurables: List<IntrinsicMeasurable>,
        h: IntPx
): IntPx {
    val mapped = measurables.map {
        DefaultIntrinsicMeasurable(it, IntrinsicMinMax.Max, IntrinsicWidthHeight.Width)
    }
    val constraints = Constraints(maxHeight = h)
    val layoutReceiver = IntrinsicsMeasureScope(this)
    val layoutResult = layoutReceiver.measureBlock(mapped, constraints)
    return layoutResult.width
}

/**
 * Default implementation for the max intrinsic height of a layout. This works by running the
 * measure block with measure calls replaced with intrinsic measurement calls.
 */
private inline fun Density.MeasuringMaxIntrinsicHeight(
        measureBlock: MeasureBlock,
        measurables: List<IntrinsicMeasurable>,
        w: IntPx
): IntPx {
    val mapped = measurables.map {
        DefaultIntrinsicMeasurable(it, IntrinsicMinMax.Max, IntrinsicWidthHeight.Height)
    }
    val constraints = Constraints(maxWidth = w)
    val layoutReceiver = IntrinsicsMeasureScope(this)
    val layoutResult = layoutReceiver.measureBlock(mapped, constraints)
    return layoutResult.height
}

/**
 * Used to return a fixed sized item for intrinsics measurements in [Layout]
 */
private class DummyPlaceable(width: IntPx, height: IntPx) : Placeable() {
    override fun get(line: AlignmentLine): IntPx? = null
    override val size = IntPxSize(width, height)
    override fun performPlace(position: IntPxPosition) { }
}

/**
 * Identifies an [IntrinsicMeasurable] as a min or max intrinsic measurement.
 */
@PublishedApi
internal enum class IntrinsicMinMax {
    Min, Max
}

/**
 * Identifies an [IntrinsicMeasurable] as a width or height intrinsic measurement.
 */
@PublishedApi
internal enum class IntrinsicWidthHeight {
    Width, Height
}

/**
 * A wrapper around a [Measurable] for intrinsic measurments in [Layout]. Consumers of
 * [Layout] don't identify intrinsic methods, but we can give a reasonable implementation
 * by using their [measure], substituting the intrinsics gathering method
 * for the [Measurable.measure] call.
 */
@PublishedApi
internal class DefaultIntrinsicMeasurable(
        val measurable: IntrinsicMeasurable,
        val minMax: IntrinsicMinMax,
        val widthHeight: IntrinsicWidthHeight
) : Measurable {
    override val parentData: Any?
        get() = measurable.parentData

    override fun measure(constraints: Constraints): Placeable {
        if (widthHeight == IntrinsicWidthHeight.Width) {
            val width = if (minMax == IntrinsicMinMax.Max) {
                measurable.maxIntrinsicWidth(constraints.maxHeight)
            } else {
                measurable.minIntrinsicWidth(constraints.maxHeight)
            }
            return DummyPlaceable(width, constraints.maxHeight)
        }
        val height = if (minMax == IntrinsicMinMax.Max) {
            measurable.maxIntrinsicHeight(constraints.maxWidth)
        } else {
            measurable.minIntrinsicHeight(constraints.maxWidth)
        }
        return DummyPlaceable(constraints.maxWidth, height)
    }

    override fun minIntrinsicWidth(height: IntPx): IntPx {
        return measurable.minIntrinsicWidth(height)
    }

    override fun maxIntrinsicWidth(height: IntPx): IntPx {
        return measurable.maxIntrinsicWidth(height)
    }

    override fun minIntrinsicHeight(width: IntPx): IntPx {
        return measurable.minIntrinsicHeight(width)
    }

    override fun maxIntrinsicHeight(width: IntPx): IntPx {
        return measurable.maxIntrinsicHeight(width)
    }
}

/**
 * Receiver scope for [Layout]'s layout lambda when used in an intrinsics call.
 */
@PublishedApi
internal class IntrinsicsMeasureScope(
        density: Density
) : MeasureScope(), Density by density {
    // TODO(popam): clean this up and prevent measuring inside intrinsics
}