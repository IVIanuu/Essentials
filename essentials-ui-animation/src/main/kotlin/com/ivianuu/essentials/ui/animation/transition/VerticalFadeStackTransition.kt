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

package com.ivianuu.essentials.ui.animation.transition

import androidx.compose.animation.core.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import com.ivianuu.essentials.ui.animation.*
import com.ivianuu.essentials.ui.animation.util.*

fun VerticalFadeStackTransition(
    spec: AnimationSpec<Float> = defaultAnimationSpec()
) = ContentAnimationStackTransition(spec) { fromModifier, toModifier, value ->
    fromModifier?.value = if (isPush) Modifier else Modifier.alpha(1f - value)
        .fractionalTranslation(translationYFraction = 0.3f * value)
    toModifier?.value =  if (isPush) Modifier.alpha(value)
        .fractionalTranslation(translationYFraction = 0.3f * (1f - value))
    else Modifier
}
