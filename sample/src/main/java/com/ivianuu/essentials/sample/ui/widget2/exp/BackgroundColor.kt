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

package com.ivianuu.essentials.sample.ui.widget2.exp

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import com.ivianuu.essentials.sample.ui.widget2.lib.BuildContext
import com.ivianuu.essentials.sample.ui.widget2.lib.SingleChildViewGroupWidget
import com.ivianuu.essentials.sample.ui.widget2.lib.Widget

class BackgroundColor(
    child: Widget,
    val color: Int,
    key: Any? = null
) : SingleChildViewGroupWidget<FrameLayout>(child, key) {
    override fun updateView(context: BuildContext, view: FrameLayout) {
        super.updateView(context, view)
        view.setBackgroundColor(color)
    }

    override fun createView(context: BuildContext) =
        FrameLayout(AndroidContext(context)).apply {
            layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        }

}