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

package com.ivianuu.essentials.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ivianuu.director.Controller
import com.ivianuu.director.requireActivity
import com.ivianuu.essentials.injection.ChildControllerComponent
import com.ivianuu.essentials.injection.ControllerComponent
import com.ivianuu.essentials.ui.navigation.Navigator
import com.ivianuu.essentials.ui.navigation.Route
import com.ivianuu.essentials.util.InjektTraitContextWrapper
import com.ivianuu.essentials.util.cast
import com.ivianuu.essentials.util.unsafeLazy
import com.ivianuu.injekt.InjektTrait
import com.ivianuu.injekt.Module
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.clearFindViewByIdCache

/**
 * Base controller
 */
abstract class EsController : Controller(), InjektTrait, LayoutContainer {

    override val component by unsafeLazy {
        if (parentController != null) {
            ChildControllerComponent {
                modules(this@EsController.modules())
            }
        } else {
            ControllerComponent {
                modules(this@EsController.modules())
            }
        }
    }

    override val containerView: View?
        get() = _containerView
    private var _containerView: View? = null

    val navigator: Navigator
        get() = requireActivity().cast<EsActivity>().navigator

    var route: Route? = null
        internal set

    protected open val layoutRes: Int get() = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup
    ): View {
        check(layoutRes != -1) { "no layoutRes provided" }
        val injectorInflater =
            inflater.cloneInContext(InjektTraitContextWrapper(requireActivity(), this))
        return injectorInflater.inflate(layoutRes, container, false)
            .also { setContentView(it) }
    }

    override fun onDestroyView(view: View) {
        clearFindViewByIdCache()
        _containerView = null
        super.onDestroyView(view)
    }

    protected open fun modules(): List<Module> = emptyList()

    protected fun setContentView(view: View) {
        _containerView = view
        onViewCreated(view)
    }

    protected open fun onViewCreated(view: View) {
    }
}
