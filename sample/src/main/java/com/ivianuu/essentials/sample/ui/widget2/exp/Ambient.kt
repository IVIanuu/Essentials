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

import com.ivianuu.essentials.sample.ui.widget2.lib.BuildContext
import com.ivianuu.essentials.sample.ui.widget2.lib.InheritedWidget
import com.ivianuu.essentials.sample.ui.widget2.lib.Widget
import com.ivianuu.essentials.sample.ui.widget2.lib.ancestorInheritedElementForWidgetOfExactType

class Ambient<T>(
    private val key: Any? = null,
    private val defaultFactory: (() -> T)? = null
) {
    @Suppress("UNCHECKED_CAST")
    internal val defaultValue by lazy {
        val fn = defaultFactory
        if (fn != null) fn()
        else null as T
    }

    override fun hashCode() = key.hashCode()
    override fun equals(other: Any?) = this === other
    override fun toString(): String = "Ambient<$key>"

    fun of(context: BuildContext): T =
        context.ancestorInheritedElementForWidgetOfExactType<Provider<T>>()!!.value

    inner class Provider<T>(
        val value: T,
        child: Widget
    ) : InheritedWidget(child = child, key = key) {
        override fun updateShouldNotify(oldWidget: InheritedWidget): Boolean = true
    }
}