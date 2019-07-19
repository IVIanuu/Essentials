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

package com.ivianuu.essentials.sample.ui.widget.lib

import android.view.ViewGroup
import android.widget.FrameLayout

internal class RootWidget(
    val rootBuildContext: RootBuildContext,
    val buildChildren: BuildContext.() -> Unit
) : ViewGroupWidget<ViewGroup>() {

    override fun buildChildren() {
        super.buildChildren()
        buildChildren.invoke(this)
    }

    override fun createView(container: ViewGroup): ViewGroup {
        return FrameLayout(container.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

}