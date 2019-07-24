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

import com.github.ajalt.timberkt.d

fun StatelessWidget(
    id: Any,
    key: Any? = null,
    meta: String? = null,
    child: BuildContext.() -> Unit
): Widget = FuncW(id, key, meta, child)

private class FuncW(
    val id: Any,
    key: Any? = null,
    val meta: String? = null,
    val child: BuildContext.() -> Unit
) : StatelessWidget(key = joinKey(id, key)) {
    override fun BuildContext.child() {
        child.invoke(this)
    }

    override fun toString(): String {
        return "Stateless(id=$id,key=$key,meta=$meta)"
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

    override fun onEachChild(block: (Element) -> Unit) {
        d { "on each child ${widget.key} child is $child" }
        super.onEachChild(block)
    }
}