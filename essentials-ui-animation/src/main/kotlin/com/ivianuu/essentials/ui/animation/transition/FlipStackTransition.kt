package com.ivianuu.essentials.ui.animation.transition

import androidx.compose.animation.core.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*

fun FlipStackTransition(
  direction: FlipDirection = FlipDirection.RIGHT,
  spec: AnimationSpec<Float> = defaultAnimationSpec(easing = FastOutSlowInEasing)
) = ContentAnimationStackTransition(spec) { fromModifier, toModifier, value ->
  toModifier?.value = Modifier
    .graphicsLayer {
      alpha = value
      if (direction == FlipDirection.LEFT || direction == FlipDirection.RIGHT)
        rotationY = direction.startRotation - direction.startRotation * value
      else
        rotationX = direction.startRotation - direction.startRotation * value
    }
  fromModifier?.value = Modifier
    .graphicsLayer {
      alpha = 1f - value
      if (direction == FlipDirection.LEFT || direction == FlipDirection.RIGHT)
        rotationY = direction.endRotation * value
      else
        rotationX = direction.endRotation * value
    }
}

enum class FlipDirection(
  val startRotation: Float,
  val endRotation: Float
) {
  LEFT(-180f, 180f),
  RIGHT(180f, -180f),
  UP(-180f, 180f),
  DOWN(180f, -180f);
}
