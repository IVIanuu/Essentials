package com.ivianuu.essentials.ui.common

import androidx.animation.AnimationBuilder
import androidx.animation.AnimationClockObservable
import androidx.animation.AnimationEndReason
import androidx.compose.Composable
import androidx.compose.Model
import androidx.ui.animation.AnimatedFloatModel
import androidx.ui.core.AnimationClockAmbient
import androidx.ui.core.Direction
import androidx.ui.core.gesture.DragObserver
import androidx.ui.core.gesture.PressGestureDetector
import androidx.ui.core.gesture.TouchSlopDragGestureDetector
import androidx.ui.foundation.animation.FlingConfig
import androidx.ui.foundation.animation.fling
import androidx.ui.unit.Px
import androidx.ui.unit.PxPosition
import androidx.ui.unit.coerceIn
import androidx.ui.unit.px
import com.github.ajalt.timberkt.d
import com.ivianuu.essentials.ui.core.Axis
import com.ivianuu.essentials.ui.core.retain

@Composable
fun RetainedScrollPosition(
    initial: Px = 0.px,
    minValue: Px = 0.px,
    maxValue: Px = Px.Infinity,
    animationClock: AnimationClockObservable = AnimationClockAmbient.current
): ScrollPosition {
    val flingConfig = FlingConfig()
    return retain {
        ScrollPosition(
            animationClock,
            initial, minValue, maxValue
        ) { flingConfig }
    }
}

@Model
class ScrollPosition(
    animationClock: AnimationClockObservable,
    initial: Px = 0.px,
    minValue: Px = 0.px,
    maxValue: Px = Px.Infinity,
    var flingConfigFactory: (Px) -> FlingConfig
) {

    val value: Px
        get() = animatedFloat.value.px
            .coerceIn(_minValue, _maxValue) // todo remove once fixed

    private var _minValue = minValue
    val minValue: Px get() = _minValue

    private var _maxValue = maxValue
    val maxValue: Px get() = _maxValue

    private val animatedFloat = AnimatedFloatModel(initial.value, animationClock).apply {
        setBounds(minValue.value, maxValue.value)
    }

    val isAnimating: Boolean
        get() = animatedFloat.isRunning

    var direction = ScrollDirection.Idle
        internal set

    fun smoothScrollTo(
        value: Px,
        anim: AnimationBuilder<Float>,
        onEnd: (endReason: AnimationEndReason, finishValue: Float) -> Unit = { _, _ -> }
    ) {
        animatedFloat.animateTo(value.value, anim) { endReason, finishValue ->
            onEnd(endReason, finishValue)
            direction = ScrollDirection.Idle
        }
    }

    fun smoothScrollBy(
        value: Px,
        anim: AnimationBuilder<Float>,
        onEnd: (endReason: AnimationEndReason, finishValue: Float) -> Unit = { _, _ -> }
    ) {
        smoothScrollTo(this.value + value, anim) { endReason, finishValue ->
            onEnd(endReason, finishValue)
            direction = ScrollDirection.Idle
        }
    }

    fun scrollTo(value: Px) {
        if (this.value != value) {
            d { "scroll to $value" }
            animatedFloat.snapTo(value.value)
        }
    }

    fun scrollBy(value: Px) {
        scrollTo(this.value + value)
    }

    fun correctBy(value: Px) {
        scrollBy(value)
    }

    fun flingBy(velocity: Px) {
        d { "fling by $velocity" }
        animatedFloat.fling(
            flingConfigFactory(velocity),
            velocity.value
        )
    }

    fun updateBounds(minValue: Px = _minValue, maxValue: Px = _maxValue) {
        check(minValue <= maxValue) {
            "Min value $minValue cannot be greater than max value $maxValue"
        }
        _minValue = minValue
        _maxValue = maxValue
        animatedFloat.setBounds(minValue.value, maxValue.value)
        scrollTo(value.coerceIn(_minValue, _maxValue))

        d { "update bounds $minValue $maxValue" }
    }
}

@Composable
fun Scrollable(
    position: ScrollPosition,
    direction: Axis = Axis.Vertical,
    enabled: Boolean = true,
    children: @Composable () -> Unit
) {
    PressGestureDetector(onPress = { position.scrollTo(position.value) }) {
        TouchSlopDragGestureDetector(
            dragObserver = object : DragObserver {
                override fun onDrag(dragDistance: PxPosition): PxPosition {
                    if (!enabled) return PxPosition.Origin
                    val oldValue = position.value
                    val distance = -when (direction) {
                        Axis.Horizontal -> dragDistance.x
                        Axis.Vertical -> dragDistance.y
                    }
                    val newValue =
                        (oldValue + distance).coerceIn(position.minValue, position.maxValue)

                    val scrollDirection = when (direction) {
                        Axis.Horizontal -> {
                            if (dragDistance.x <= 0.px) ScrollDirection.Forward else ScrollDirection.Reverse
                        }
                        Axis.Vertical -> {
                            if (dragDistance.y <= 0.px) ScrollDirection.Forward else ScrollDirection.Reverse
                        }
                    }

                    val consumed = -(newValue - oldValue)
                    position.direction = scrollDirection
                    position.scrollTo(newValue)

                    return when (direction) {
                        Axis.Horizontal -> PxPosition(
                            x = consumed,
                            y = 0.px
                        )
                        Axis.Vertical -> PxPosition(
                            x = 0.px,
                            y = consumed
                        )
                    }
                }

                override fun onStop(velocity: PxPosition) {
                    if (!enabled) return
                    val mainAxisVelocity = -when (direction) {
                        Axis.Horizontal -> velocity.x
                        Axis.Vertical -> velocity.y
                    }
                    position.flingBy(mainAxisVelocity)
                }
            },
            canDrag = { dragDirection ->
                if (!enabled) return@TouchSlopDragGestureDetector false
                return@TouchSlopDragGestureDetector when (direction) {
                    Axis.Horizontal -> dragDirection == Direction.LEFT || dragDirection == Direction.RIGHT
                    Axis.Vertical -> dragDirection == Direction.UP || dragDirection == Direction.DOWN
                }
            },
            children = children
        )
    }
}

enum class ScrollDirection {
    Idle,
    Forward,
    Reverse
}
