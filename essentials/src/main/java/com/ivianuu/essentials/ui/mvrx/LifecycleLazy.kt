/*
 * Copyright 2018 Manuel Wrage
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

package com.ivianuu.essentials.ui.mvrx

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@PublishedApi
internal class LifecycleLazy<T>(
    owner: LifecycleOwner,
    private val event: Lifecycle.Event = Lifecycle.Event.ON_CREATE,
    initializer: () -> T
) : Lazy<T> {

    private var initializer: (() -> T)? = initializer
    private var _value: Any? = this

    init {
        owner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (this@LifecycleLazy.event == event) {
                    if (!isInitialized()) value
                    owner.lifecycle.removeObserver(this)
                }
            }
        })
    }

    override val value: T
        get() {
            if (_value === this) {
                _value = initializer!!()
                initializer = null
            }
            @Suppress("UNCHECKED_CAST")
            return _value as T
        }

    override fun isInitialized(): Boolean = _value !== this

    override fun toString(): String =
        if (isInitialized()) value.toString() else "Lazy value not initialized yet."
}