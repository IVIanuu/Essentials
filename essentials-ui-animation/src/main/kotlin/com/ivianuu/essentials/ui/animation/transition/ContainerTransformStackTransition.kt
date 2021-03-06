package com.ivianuu.essentials.ui.animation.transition

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import com.ivianuu.essentials.*
import com.ivianuu.essentials.coroutines.*
import com.ivianuu.essentials.ui.animation.*
import com.ivianuu.essentials.ui.animation.util.*
import com.ivianuu.essentials.ui.core.*

fun ContainerTransformStackTransition(
  closedKey: Any,
  openedKey: Any,
  spec: AnimationSpec<Float> = defaultAnimationSpec(),
): StackTransition = {
  val fromModifier = fromElementModifier(if (isPush) closedKey else openedKey)!!
  val toModifier = toElementModifier(if (isPush) openedKey else closedKey)!!

  toModifier.value = Modifier.alpha(0f)

  attachTo()

  val (fromBounds, toBounds) = parTupled(
    { fromModifier.awaitLayoutCoordinates().boundsInWindow() },
    { toModifier.awaitLayoutCoordinates().boundsInWindow() }
  )

  val startPosition = fromBounds.topLeft
  val endPosition = toBounds.topLeft

  val startWidth = fromBounds.width
  val startHeight = fromBounds.height
  val endWidth = toBounds.width
  val endHeight = toBounds.height

  var currentFraction by mutableStateOf(0f)
  var currentScrimAlpha by mutableStateOf(0f)
  var currentPosition by mutableStateOf(startPosition)
  var currentWidth by mutableStateOf(startWidth)
  var currentHeight by mutableStateOf(startHeight)
  var currentColor by mutableStateOf(Color.Transparent)
  var currentCornerSize by mutableStateOf(0.dp)
  var currentBorderWidth by mutableStateOf(0.dp)
  var currentBorderColor by mutableStateOf(Color.Transparent)
  var currentElevation by mutableStateOf(0.dp)
  var currentAbsoluteElevation by mutableStateOf(0.dp)
  var currentFromAlpha by mutableStateOf(1f)
  var currentToAlpha by mutableStateOf(0f)

  val fromProps =
    fromElement(if (isPush) closedKey else openedKey)!![ContainerTransformPropsKey]!!
  val toProps = toElement(if (isPush) openedKey else closedKey)!![ContainerTransformPropsKey]!!

  overlay {
    Box(
      modifier = Modifier.fillMaxSize()
        .background(Color.Black.copy(alpha = 0.54f * currentScrimAlpha))
    ) {
      CompositionLocalProvider(
        LocalAbsoluteElevation provides currentAbsoluteElevation
      ) {
        Surface(
          modifier = Modifier
            .composed {
              with(LocalDensity.current) {
                size(currentWidth.toDp(), currentHeight.toDp())
                  .graphicsLayer {
                    translationX = currentPosition.x
                    translationY = currentPosition.y
                  }
              }
            },
          shape = RoundedCornerShape(currentCornerSize),
          color = currentColor,
          border = if (currentBorderWidth > 0.dp) BorderStroke(
            currentBorderWidth,
            currentBorderColor
          ) else null,
          elevation = currentElevation
        ) {
          withCompositionContext(fromProps.compositionContext) {
            Box(
              modifier = Modifier
                .then(object : LayoutModifier {
                  override fun MeasureScope.measure(
                    measurable: Measurable,
                    constraints: Constraints
                  ): MeasureResult {
                    val placeable = measurable.measure(
                      Constraints.fixed(
                        fromBounds.width.toInt(), fromBounds.height.toInt()
                      )
                    )
                    return layout(currentWidth.toInt(), currentHeight.toInt()) {
                      placeable.place(0, 0)
                    }
                  }
                })
                .graphicsLayer {
                  alpha = currentFromAlpha
                  val sourceSize = Size(
                    fromBounds.width,
                    fromBounds.width * currentHeight / currentWidth
                  )
                  val destinationSize = Size(
                    currentWidth,
                    sourceSize.height * currentWidth / sourceSize.width
                  )
                  transformOrigin = TransformOrigin(0f, 0f)
                  scaleX = destinationSize.width / sourceSize.width
                  scaleY = destinationSize.height / sourceSize.height
                },
            ) {
              CompositionLocalProvider(
                LocalContainerTransformTransitionFraction provides
                    if (isPush) currentFraction else 1f - currentFraction,
                content = fromProps.content
              )
            }
          }
          withCompositionContext(toProps.compositionContext) {
            Box(
              modifier = Modifier
                .then(object : LayoutModifier {
                  override fun MeasureScope.measure(
                    measurable: Measurable,
                    constraints: Constraints
                  ): MeasureResult {
                    val placeable = measurable.measure(
                      Constraints.fixed(
                        toBounds.width.toInt(), toBounds.height.toInt()
                      )
                    )
                    return layout(currentWidth.toInt(), currentHeight.toInt()) {
                      placeable.place(0, 0)
                    }
                  }
                })
                .graphicsLayer {
                  alpha = currentToAlpha
                  val sourceSize = Size(
                    toBounds.width,
                    toBounds.width * currentHeight / currentWidth
                  )
                  val destinationSize = Size(
                    currentWidth,
                    sourceSize.height * currentWidth / sourceSize.width
                  )
                  transformOrigin = TransformOrigin(0f, 0f)
                  scaleX = destinationSize.width / sourceSize.width
                  scaleY = destinationSize.height / sourceSize.height
                },
            ) {
              CompositionLocalProvider(
                LocalContainerTransformTransitionFraction provides
                    if (isPush) 1f - currentFraction else currentFraction,
                content = toProps.content
              )
            }
          }
        }
      }
    }
  }

  animate(spec) { value ->
    fromModifier.value = Modifier.alpha(0f)
    currentFraction = value
    currentScrimAlpha = if (isPush) {
      interval(0f, 0.3f, value)
    } else {
      lerp(1f, 0f, FastOutSlowInEasing.transform(value))
    }
    currentPosition = lerp(startPosition, endPosition, FastOutSlowInEasing.transform(value))
    currentWidth = lerp(startWidth, endWidth, FastOutSlowInEasing.transform(value))
    currentHeight = lerp(startHeight, endHeight, FastOutSlowInEasing.transform(value))
    currentColor = lerp(fromProps.color, toProps.color, value)
    currentCornerSize = lerp(fromProps.cornerSize, toProps.cornerSize, value)
    currentBorderWidth = lerp(fromProps.borderWidth, toProps.borderWidth, value)
    currentBorderColor = lerp(fromProps.borderColor, toProps.borderColor, value)
    currentElevation = lerp(fromProps.elevation, toProps.elevation, value)
    currentAbsoluteElevation = lerp(fromProps.absoluteElevation, toProps.absoluteElevation, value)
    currentToAlpha = interval(0.2f, 0.4f, value)
    if (!isPush) {
      currentFromAlpha = lerp(1f, 0f, interval(0.15f, 0.45f, value))
    }
  }
}

val ContainerTransformPropsKey = AnimationElementPropKey<ContainerTransformProps>()

class ContainerTransformProps(
  compositionContext: CompositionContext,
  content: @Composable () -> Unit,
  color: Color,
  cornerSize: Dp,
  borderWidth: Dp,
  borderColor: Color,
  elevation: Dp,
  absoluteElevation: Dp
) {
  var compositionContext by mutableStateOf(compositionContext)
  var content by mutableStateOf(content)
  var color by mutableStateOf(color)
  var cornerSize by mutableStateOf(cornerSize)
  var borderWidth by mutableStateOf(borderWidth)
  var borderColor by mutableStateOf(borderColor)
  var elevation by mutableStateOf(elevation)
  var absoluteElevation by mutableStateOf(absoluteElevation)
}

val LocalContainerTransformTransitionFraction = compositionLocalOf { 0f }

@Composable fun ContainerTransformSurface(
  key: Any,
  isOpened: Boolean,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.colors.surface,
  cornerSize: Dp = 0.dp,
  borderWidth: Dp = 0.dp,
  borderColor: Color = Color.Transparent,
  elevation: Dp = 0.dp,
  content: @Composable () -> Unit = {},
) {
  val compositionContext = rememberCompositionContext()
  val absoluteElevation = LocalAbsoluteElevation.current
  val props = remember {
    ContainerTransformProps(
      compositionContext, content, color,
      cornerSize, borderWidth, borderColor, elevation, absoluteElevation
    )
  }
  SideEffect {
    props.content = content
    props.cornerSize = cornerSize
    props.color = color
    props.borderWidth = borderWidth
    props.borderColor = borderColor
    props.elevation = elevation
    props.absoluteElevation = absoluteElevation
  }
  Surface(
    modifier = modifier
      .animationElement(key, ContainerTransformPropsKey to props),
    color = color,
    shape = RoundedCornerShape(cornerSize),
    border = if (borderWidth > 0.dp) BorderStroke(borderWidth, borderColor) else null,
    elevation = elevation
  ) {
    CompositionLocalProvider(
      LocalContainerTransformTransitionFraction provides if (isOpened) 1f else 0f
    ) {
      val compositionContext = rememberCompositionContext()
      SideEffect {
        props.compositionContext = compositionContext
      }
      content()
    }
  }
}
