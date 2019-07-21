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

package com.ivianuu.essentials.sample.ui.widget2.layout

import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ivianuu.essentials.sample.ui.widget2.exp.AndroidContextContext
import com.ivianuu.essentials.sample.ui.widget2.lib.BuildContext
import com.ivianuu.essentials.sample.ui.widget2.lib.SingleChildViewGroupWidget
import com.ivianuu.essentials.sample.ui.widget2.lib.Widget

open class Align(
    val gravity: Int,
    child: Widget,
    key: Any? = null
) : SingleChildViewGroupWidget<LinearLayout>(child, key) {

    override fun updateView(context: BuildContext, view: LinearLayout) {
        super.updateView(context, view)
        view.gravity = gravity
    }

    override fun createView(context: BuildContext): LinearLayout =
        LinearLayout(AndroidContextContext(context)).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
}

open class Center(
    child: Widget,
    key: Any? = null
) : Align(Gravity.CENTER, child, key)