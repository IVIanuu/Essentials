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

package com.ivianuu.essentials.sample.ui.widget2.lib

import android.content.Context

abstract class ProxyWidget(open val child: Widget, key: Any? = null) : Widget(key)

abstract class ProxyElement(widget: ProxyWidget) : ComponentElement(widget) {
    override fun build(): Widget = widget<ProxyWidget>().child

    override fun update(context: Context, newWidget: Widget) {
        val oldWidget = widget
        super.update(context, newWidget)
        updated(oldWidget)
        isDirty = true
        rebuild(context)
    }

    protected open fun updated(oldWidget: Widget) {
        notifyClients(oldWidget)
    }

    protected open fun notifyClients(oldWidget: Widget) {
    }
}