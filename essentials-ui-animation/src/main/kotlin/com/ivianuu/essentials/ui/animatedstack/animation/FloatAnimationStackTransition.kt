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

package com.ivianuu.essentials.ui.animatedstack.animation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateTo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.ivianuu.essentials.ui.animatable.Alpha
import com.ivianuu.essentials.ui.animatable.Animatable
import com.ivianuu.essentials.ui.animatedstack.StackTransition
import kotlin.time.Duration
import kotlin.time.milliseconds

fun defaultAnimationSpec(
    duration: Duration = 300.milliseconds,
    delay: Duration = 0.milliseconds,
    easing: Easing = FastOutSlowInEasing
): AnimationSpec<Float> = TweenSpec<Float>(
    durationMillis = duration.toLongMilliseconds().toInt(),
    delay = delay.toLongMilliseconds().toInt(),
    easing = easing
)

fun FloatAnimationStackTransition(
    animationSpec: AnimationSpec<Float> = defaultAnimationSpec(),
    apply: @Composable (
        fromAnimatable: Animatable?,
        toAnimatable: Animatable?,
        isPush: Boolean,
        progress: Float
    ) -> Unit
): StackTransition = { context ->
    val animationState: AnimationState<Float, AnimationVector1D> = remember {
        AnimationState(0f)
    }
    context.toAnimatable?.set(Alpha, if (animationState.value == 0f) 0f else 1f)
    LaunchedEffect(true) {
        if (context.toAnimatable != null) context.addTo()
        animationState.animateTo(
            targetValue = 1f,
            animationSpec = animationSpec
        )
        if (context.fromAnimatable != null) context.removeFrom()
        context.onComplete()
    }
    apply(
        context.fromAnimatable,
        context.toAnimatable,
        context.isPush,
        animationState.value
    )
}
