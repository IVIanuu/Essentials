/*
 * Copyright 2019 Manuel Wrage
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

package com.ivianuu.essentials.sample.ui.widget.view

import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.ivianuu.essentials.sample.ui.widget.lib.BuildContext
import com.ivianuu.essentials.sample.ui.widget.lib.LayoutParamsWidget

fun BuildContext.MatchParent(child: BuildContext.() -> Unit) =
    Size(size = ViewGroup.LayoutParams.MATCH_PARENT, child = child)

fun BuildContext.WrapContent(child: BuildContext.() -> Unit) =
    Size(size = ViewGroup.LayoutParams.WRAP_CONTENT, child = child)

fun BuildContext.Size(size: Int, child: BuildContext.() -> Unit) =
    Size(width = size, height = size, child = child)

fun BuildContext.Size(
    width: Int,
    height: Int,
    child: BuildContext.() -> Unit
) = LayoutParamsWidget(
    props = listOf(
        ViewGroup.LayoutParams::width,
        ViewGroup.LayoutParams::height
    ),
    updateLayoutParams = {
        if (it.width != width || it.height != height) {
            it.width = width
            it.height = height
            true
        } else {
            false
        }
    },
    child = child
)

fun BuildContext.Gravity(gravity: Int, child: BuildContext.() -> Unit) = LayoutParamsWidget(
    props = listOf(FrameLayout.LayoutParams::gravity, LinearLayout.LayoutParams::gravity),
    updateLayoutParams = { lp ->
        when (lp) {
            is FrameLayout.LayoutParams -> {
                if (lp.gravity != gravity) {
                    lp.gravity = gravity
                    true
                } else {
                    false
                }
            }
            is LinearLayout.LayoutParams -> {
                if (lp.gravity != gravity) {
                    lp.gravity = gravity
                    true
                } else {
                    false
                }
            }
            else -> error("cannot apply gravity to $lp")
        }
    },
    child = child
)

fun BuildContext.Margin(margin: Int, child: BuildContext.() -> Unit) =
    Margin(margin, margin, margin, margin, child)

fun BuildContext.Margin(
    left: Int = 0,
    top: Int = 0,
    right: Int = 0,
    bottom: Int = 0,
    child: BuildContext.() -> Unit
) = LayoutParamsWidget(
    props = listOf(ViewGroup.MarginLayoutParams::setMargins),
    updateLayoutParams = {
        if (it is ViewGroup.MarginLayoutParams) {
            if (it.leftMargin != left
                || it.topMargin != top
                || it.rightMargin != right
                || it.bottomMargin != bottom
            ) {
                it.leftMargin = left
                it.topMargin = top
                it.rightMargin = right
                it.bottomMargin = bottom
                true
            } else {
                false
            }
        } else {
            error("cannot apply margin to $it")
        }
    },
    child = child
)

fun BuildContext.Weight(
    weight: Float,
    child: BuildContext.() -> Unit
) = LayoutParamsWidget(
    props = listOf(LinearLayout.LayoutParams::weight),
    updateLayoutParams = {
        if (it is LinearLayout.LayoutParams) {
            if (it.weight != weight) {
                it.weight = weight
                true
            } else {
                false
            }
        } else {
            error("cannot apply weight to $it")
        }
    },
    child = child
)