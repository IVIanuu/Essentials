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

interface BuildContext {
    val parent: BuildContext?

    fun invalidate()
    fun emit(widget: Widget<*>, containerId: Int? = null)
}

fun BuildContext(
    view: ViewGroup,
    buildWidgets: BuildContext.() -> Unit
): BuildContext = RootBuildContext(view, buildWidgets)

class RootBuildContext(
    val view: ViewGroup,
    private val buildWidgets: BuildContext.() -> Unit
) : BuildContext {

    override val parent: BuildContext?
        get() = null

    private val root = RootWidget(this)

    init {
        val rootView = root.createView(view)
        view.addView(rootView)
        invalidate()
    }

    override fun invalidate() {
        root.children?.clear()
        buildWidgets()
        root.dispatchLayout(view)
        root.dispatchBind(view)
    }

    override fun emit(widget: Widget<*>, containerId: Int?) {
        root.emit(widget, containerId)
    }

}