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

package com.ivianuu.essentials.ui.dialog

import androidx.compose.animation.core.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import com.ivianuu.essentials.ui.animation.*
import com.ivianuu.essentials.ui.animation.transition.*
import com.ivianuu.essentials.ui.navigation.*
import com.ivianuu.injekt.*
import kotlin.time.*

interface DialogKey<T> : Key<T>

@Given
fun <T : DialogKey<*>> dialogKeyUiOptionsFactory(): KeyUiOptionsFactory<T> = {
    KeyUiOptions(
        opaque = true,
        transition = DialogStackTransition()
    )
}

fun DialogStackTransition(
    spec: AnimationSpec<Float> = defaultAnimationSpec(220.milliseconds)
): StackTransition = {
    attachTo()
    val fromContentModifier = fromElementModifier(ContentAnimationElementKey)
    val toContentModifier = toElementModifier(ContentAnimationElementKey)
    val toDialogModifier = toElementModifier(DialogAnimationElementKey)
    animate(spec) {
        fromContentModifier?.value = Modifier.alpha(1f - value)
        toContentModifier?.value = Modifier.alpha(value)
        toDialogModifier?.value = Modifier.scale(value)
    }
    detachFrom()
}
