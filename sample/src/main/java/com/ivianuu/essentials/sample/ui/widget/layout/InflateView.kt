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

package com.ivianuu.essentials.sample.ui.widget.layout

import android.view.ViewGroup
import com.ivianuu.essentials.sample.ui.widget.lib.ContainerAmbient
import com.ivianuu.essentials.sample.ui.widget.lib.UpdateView
import com.ivianuu.essentials.sample.ui.widget.lib.ViewWidget
import com.ivianuu.essentials.sample.ui.widget.lib.Widget
import com.ivianuu.kommon.core.view.inflate
import kotlin.reflect.KClass

inline fun <reified V : ViewGroup> InflateViewWidget(
    layoutRes: Int,
    key: Any? = null,
    noinline updateView: UpdateView<V>? = null
) = InflateViewWidget(
    layoutRes = layoutRes,
    viewType = V::class,
    key = key,
    updateView = updateView
)

fun <V : ViewGroup> InflateViewWidget(
    layoutRes: Int,
    viewType: KClass<V>,
    key: Any? = null,
    updateView: UpdateView<V>? = null
): Widget = ViewWidget(
    viewType = viewType,
    key = key,
    createView = {
        val container = +ContainerAmbient
        container.inflate<V>(layoutRes)
    },
    updateView = updateView
)