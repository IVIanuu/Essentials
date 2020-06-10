package com.ivianuu.essentials.ui.animatedstack.animation

import androidx.ui.core.Modifier
import com.ivianuu.essentials.ui.animatedstack.StackAnimation
import com.ivianuu.essentials.ui.layout.offsetFraction
import kotlin.time.Duration
import kotlin.time.milliseconds

fun VerticalFadeStackAnimation(duration: Duration = 300.milliseconds): StackAnimation {
    return FloatStackAnimation(duration = duration) { fromElement, toElement, isPush, progress ->
        if (toElement != null && isPush) {
            toElement.drawLayerModifier.alpha = progress
            toElement.animationModifier = Modifier
                .offsetFraction(y = 0.3f * (1f - progress))
        }
        if (fromElement != null && !isPush) {
            fromElement.drawLayerModifier.alpha = 1f - progress
            fromElement.animationModifier = Modifier
                .offsetFraction(y = 0.3f * progress)
        }
    }
}
