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

abstract class ComponentElement(widget: Widget) : Element(widget) {

    protected abstract fun child()

    var child: Element? = null
        private set

    private var pendingChild: Widget? = null

    override fun mount(parent: Element?, slot: Int?) {
        super.mount(parent, slot)
        firstBuild()
    }

    override fun add(child: Widget) {
        check(pendingChild == null) { "only one child allowed" }
        pendingChild = child
    }

    override fun createView() {
        super.createView()
        child?.createView()
    }

    override fun attachView() {
        super.attachView()
        child?.attachView()
    }

    override fun detachView() {
        super.detachView()
        child?.detachView()
    }

    override fun destroyView() {
        child?.destroyView()
        super.destroyView()
    }

    override fun unmount() {
        child?.unmount()
        child = null
        super.unmount()
    }

    protected open fun firstBuild() {
        rebuild()
    }

    override fun performRebuild() {
        d { "${widget.key} perform rebuild" }
        child()
        val built = pendingChild!!
        d { "${widget.key} rebuild result is ${pendingChild!!.key}" }
        pendingChild = null
        isDirty = false
        child = updateChild(child, built, slot) // todo slot or null?
    }

    override fun onEachChild(block: (Element) -> Unit) {
        child?.let(block)
    }

}