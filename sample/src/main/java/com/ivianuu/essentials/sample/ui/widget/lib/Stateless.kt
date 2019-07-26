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

fun BuildContext.StatelessWidget(
    key: Any? = null,
    child: BuildContext.() -> Unit
): Widget = object : StatelessWidget(key) {
    override fun BuildContext.child() {
        child.invoke(this)
    }
}

abstract class StatelessWidget(key: Any? = null) : Widget(key) {
    override fun createElement(): StatelessElement =
        StatelessElement(this)

    abstract fun BuildContext.child()

    internal fun _child(context: BuildContext) = with(context) { child() }
}

open class StatelessElement(widget: StatelessWidget) : ComponentElement(widget) {
    override fun child() {
        widget<StatelessWidget>()._child(this)
    }

    override fun update(newWidget: Widget) {
        super.update(newWidget)
        isDirty = true
        rebuild()
    }
}