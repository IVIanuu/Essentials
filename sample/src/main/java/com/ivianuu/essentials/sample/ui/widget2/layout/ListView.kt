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

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.epoxy.TypedEpoxyController
import com.github.ajalt.timberkt.d
import com.ivianuu.essentials.sample.ui.widget.lib.properties
import com.ivianuu.essentials.sample.ui.widget2.exp.AndroidContextContext
import com.ivianuu.essentials.sample.ui.widget2.lib.BuildContext
import com.ivianuu.essentials.sample.ui.widget2.lib.ComponentElement
import com.ivianuu.essentials.sample.ui.widget2.lib.Element
import com.ivianuu.essentials.sample.ui.widget2.lib.ViewElement
import com.ivianuu.essentials.sample.ui.widget2.lib.ViewWidget
import com.ivianuu.essentials.sample.ui.widget2.lib.Widget
import com.ivianuu.essentials.ui.epoxy.EsHolder
import com.ivianuu.essentials.ui.epoxy.SimpleModel
import com.ivianuu.essentials.util.cast

class ListView(
    val children: List<Widget>,
    key: Any? = null
) : ViewWidget<RecyclerView>(key) {

    override fun createElement() =
        ListViewElement(this)

    override fun createView(
        context: BuildContext
    ) = EpoxyRecyclerView(AndroidContextContext(context)).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        val epoxyController =
            WidgetEpoxyController(context.cast())
        adapter = epoxyController.adapter
        properties.set("epoxy_controller", epoxyController)
    }

    override fun updateView(context: BuildContext, view: RecyclerView) {
        super.updateView(context, view)
        d { "update list view $children" }
        view.properties.get<WidgetEpoxyController>("epoxy_controller")
            ?.setData(children)
    }
}

class ListViewElement(widget: ListView) : ViewElement<RecyclerView>(widget) {
    override fun insertChildView(view: View, slot: Int?) {
    }

    override fun moveChildView(view: View, slot: Int?) {
    }

    override fun removeChildView(view: View) {
    }

}

private class WidgetEpoxyController(val element: Element) : TypedEpoxyController<List<Widget>>() {
    override fun buildModels(data: List<Widget>?) {
        d { "build models $data" }
        data?.forEach {
            add(
                WidgetEpoxyModel(
                    element,
                    it
                )
            )
        }
    }
}

private data class WidgetEpoxyModel(
    private val parent: Element,
    private val widget: Widget
) : SimpleModel(id = widget.key!!) {

    override fun bind(holder: EsHolder) {
        super.bind(holder)
        val element = holder.root.properties.get<Element>("element")!!
        element.update(widget)
    }

    override fun getViewType(): Int = widget::class.hashCode() + (widget.key?.hashCode() ?: 0)

    override fun buildView(parent: ViewGroup): View {
        val element = widget.createElement()
        element.mount(this.parent, null)
        element.attachView()

        // todo this is hacky
        val view: View = if (element is ViewElement<*>) {
            element.view!!
        } else {
            var childElement = element
            while (childElement is ComponentElement) {
                childElement = childElement.child!!
            }

            (childElement as ViewElement<*>).view!!
        }

        view.properties.set("element", element)

        d { "build view with element $element $view" }

        return view
    }

}