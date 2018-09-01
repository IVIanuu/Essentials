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

package com.ivianuu.essentials.ui.epoxy

import android.content.Context
import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.ivianuu.essentials.util.ContextAware
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.extensions.LayoutContainer

/**
 * Base epoxy holder
 */
open class BaseEpoxyHolder : EpoxyHolder(), ContextAware, LayoutContainer {
    override lateinit var containerView: View
    override val providedContext: Context
        get() = containerView.context

    var boundModel: BaseEpoxyModel? = null

    val disposables: CompositeDisposable
        get() = boundModel?.disposables ?: throw IllegalStateException("no model bound")

    override fun bindView(itemView: View) {
        containerView = itemView
    }

}