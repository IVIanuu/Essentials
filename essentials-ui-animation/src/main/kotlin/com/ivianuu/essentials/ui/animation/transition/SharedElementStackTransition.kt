package com.ivianuu.essentials.ui.animation.transition

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*
import com.ivianuu.essentials.*
import com.ivianuu.essentials.coroutines.*
import com.ivianuu.essentials.ui.animation.*
import com.ivianuu.essentials.ui.animation.util.*
import com.ivianuu.essentials.ui.core.*
import kotlinx.coroutines.*
import kotlin.time.*

fun SharedElementStackTransition(
  vararg sharedElements: Pair<Any, Any>,
  sharedElementAnimationSpec: AnimationSpec<Float> = defaultAnimationSpec(easing = FastOutSlowInEasing),
  contentTransition: StackTransition = CrossFadeStackTransition(sharedElementAnimationSpec),
  waitingTimeout: Duration = 200.milliseconds
): StackTransition = {
  val states = sharedElements
    .map { (from, to) ->
      if (isPush) {
        SharedElementState(from, fromElement(from), fromElementModifier(from)) to
            SharedElementState(to, toElement(to), toElementModifier(to))
      } else {
        SharedElementState(to, fromElement(to), fromElementModifier(to)) to
            SharedElementState(from, toElement(from), toElementModifier(from))
      }
    }

  // hide "to" content while waiting for shared elements
  val toContentModifier = toElementModifier(ContentAnimationElementKey)
  toContentModifier?.value = Modifier.alpha(0f)
  // if we do not yield here some items bounds do not appear
  yield()
  attachTo()

  // wait until all shared elements has been placed on the screen
  withTimeoutOrNull(waitingTimeout) {
    while (true) {
      delay(1)
      if (states.all { (start, end) ->
          start.bounds != null &&
              end.bounds != null
        }) break
    }
  }

  // capture geometry
  states
    .filter { it.first.bounds != null && it.second.bounds != null }
    .forEach { (start, end) ->
      val startBounds = start.bounds!!
      val endBounds = end.bounds!!
      start.capturedGeometry = SharedElementGeometry(
        position = Offset(
          x = startBounds.left + (startBounds.width - endBounds.width) / 2,
          y = startBounds.top + (startBounds.height - endBounds.height) / 2
        ),
        scaleX = startBounds.width / endBounds.width,
        scaleY = startBounds.height / endBounds.height,
        fraction = 0f
      )
      end.capturedGeometry = SharedElementGeometry(
        position = Offset(x = endBounds.left, y = endBounds.top),
        scaleX = 1f,
        scaleY = 1f,
        fraction = 1f
      )
    }

  // install overlay
  overlay {
    states
      .filter { it.first.bounds != null && it.second.bounds != null }
      .forEach { (_, endState) ->
        val endBounds = endState.bounds!!
        val currentGeometry = endState.animatedGeometry!!
        val sharedElementProps = endState.element!![SharedElementPropsKey]!!
        key(endState) {
          Box(
            modifier = Modifier
              .then(object : LayoutModifier {
                override fun MeasureScope.measure(
                  measurable: Measurable,
                  constraints: Constraints
                ): MeasureResult {
                  val placeable = measurable.measure(
                    Constraints.fixed(
                      endBounds.width.toInt(), endBounds.height.toInt()
                    )
                  )
                  return layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                  }
                }
              })
              .graphicsLayer {
                scaleX = currentGeometry.scaleX
                scaleY = currentGeometry.scaleY
                translationX = currentGeometry.position.x
                translationY = currentGeometry.position.y
              }
          ) {
            withCompositionContext(sharedElementProps.compositionContext) {
              CompositionLocalProvider(
                LocalSharedElementTransitionFraction provides endState.animatedGeometry!!.fraction,
                content = sharedElementProps.content
              )
            }
          }
        }
      }
  }
  // show the content
  toContentModifier?.value = Modifier

  par(
    {
      contentTransition(
        object : StackTransitionScope by this {
          override fun attachTo() {
          }

          override fun detachFrom() {
          }
        }
      )
    },
    {
      animate(sharedElementAnimationSpec) { value ->
        states
          .filter { it.first.capturedGeometry != null && it.second.capturedGeometry != null }
          .forEach { (start, end) ->
            end.modifier?.value = Modifier.alpha(0f)
            start.modifier?.value = Modifier.alpha(0f)
            end.animatedGeometry = SharedElementGeometry(
              position = arcLerp(
                if (isPush) start.capturedGeometry!!.position else end.capturedGeometry!!.position,
                if (isPush) end.capturedGeometry!!.position else start.capturedGeometry!!.position,
                if (isPush) value else 1f - value
              ),
              scaleX = lerp(
                start.capturedGeometry!!.scaleX,
                end.capturedGeometry!!.scaleX,
                value
              ),
              scaleY = lerp(
                start.capturedGeometry!!.scaleY,
                end.capturedGeometry!!.scaleY,
                value
              ),
              fraction = if (isPush) value else 1f - value
            )
          }
      }
    }
  )
}

val LocalSharedElementTransitionFraction = compositionLocalOf { 0f }

private data class SharedElementState(
  val key: Any?,
  val element: AnimationElement?,
  val modifier: MutableState<Modifier>?
) {
  var bounds: Rect? = null
    private set
  var animatedGeometry: SharedElementGeometry? by mutableStateOf(null)
  var capturedGeometry: SharedElementGeometry? = null

  init {
    updateModifier()
  }

  private fun updateModifier() {
    modifier?.value = Modifier
      .onGloballyPositioned { coords ->
        if (bounds == null) {
          bounds = coords
            .takeIf { it.isAttached }
            ?.boundsInRoot()
        }
      }
  }
}

private data class SharedElementGeometry(
  val fraction: Float,
  val position: Offset,
  val scaleX: Float,
  val scaleY: Float
)

private class SharedElementProps(
  compositionContext: CompositionContext,
  content: @Composable () -> Unit
) {
  var compositionContext by mutableStateOf(compositionContext)
  var content by mutableStateOf(content)
}

private val SharedElementPropsKey = AnimationElementPropKey<SharedElementProps>()

@Composable fun SharedElement(
  modifier: Modifier = Modifier,
  key: Any,
  isStart: Boolean,
  content: @Composable () -> Unit
) {
  val compositionContext = rememberCompositionContext()
  val props = remember { SharedElementProps(compositionContext, content) }
  SideEffect {
    props.compositionContext = compositionContext
    props.content = content
  }
  Box(
    modifier = modifier
      .animationElement(key, SharedElementPropsKey to props)
  ) {
    CompositionLocalProvider(
      LocalSharedElementTransitionFraction provides if (isStart) 0f else 1f,
      content = content
    )
  }
}
