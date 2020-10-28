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

package com.ivianuu.essentials.ui.animatable

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.globalBounds
import androidx.compose.ui.onGloballyPositioned

@Stable
class Animatable(val tag: Any) {

    private val _props = mutableStateMapOf<Prop<*>, Any?>()
    val props: Map<Prop<*>, Any?> get() = _props

    private val modifiersByProps = mutableMapOf<Prop<*>, Modifier>()

    private val onGloballyPositioned = Modifier.onGloballyPositioned {
        layoutCoordinates = it
    }
    private val drawLayerModifier = MutableDrawLayerModifier()
    private val baseModifier = onGloballyPositioned.then(drawLayerModifier)

    internal var modifier by mutableStateOf(baseModifier)

    operator fun <T> get(prop: Prop<T>): T = props.getValue(prop) as T

    operator fun <T> set(prop: Prop<T>, value: T): Animatable {
        _props[prop] = value
        if (prop is DrawLayerProp) {
            prop.apply(drawLayerModifier, value)
        }
        if (prop is ModifierProp) {
            modifiersByProps[prop] = prop.asModifier(value)
            modifier = baseModifier.then(
                (
                        modifiersByProps.values
                            .reduceOrNull { acc, modifier -> acc.then(modifier) } ?: Modifier
                        )
            )
        }
        return this
    }

    fun <T> getOrNull(prop: Prop<T>): T? = _props[prop] as? T

    inline fun <T> getOrElse(prop: Prop<T>, defaultValue: () -> T): T =
        if (prop in props) get(prop) else defaultValue()

    var layoutCoordinates: LayoutCoordinates? by mutableStateOf(null)
        internal set
}

val Animatable.bounds: Rect?
    get() = layoutCoordinates
        ?.takeIf { it.isAttached }
        ?.globalBounds

val Animatable.width: Int?
    get() = layoutCoordinates
        ?.takeIf { it.isAttached }
        ?.size?.width

val Animatable.height: Int?
    get() = layoutCoordinates
        ?.takeIf { it.isAttached }
        ?.size?.height

fun Animatable.setFractionTranslationX(fraction: Float) =
    set(TranslationX, (width ?: 0) * fraction)

fun Animatable.setFractionTranslationY(fraction: Float) =
    set(TranslationY, (height ?: 0) * fraction)

fun Modifier.animatable(
    tag: Any,
    vararg props: Prop.Pair<*>
): Modifier = composed {
    val animatable = animatableFor(tag)
    remember(*props) {
        props.forEach { animatable[it.prop as Prop<Any?>] = it.value }
    }
    animatable.modifier
}
