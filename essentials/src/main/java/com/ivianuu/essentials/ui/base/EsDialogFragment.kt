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

package com.ivianuu.essentials.ui.base

import android.content.Context
import android.content.DialogInterface
import androidx.fragment.app.DialogFragment
import com.ivianuu.essentials.ui.common.BackHandler
import com.ivianuu.essentials.ui.mvrx.MvRxView
import com.ivianuu.essentials.ui.traveler.key.keyModule
import com.ivianuu.essentials.util.ContextAware
import com.ivianuu.essentials.util.ext.unsafeLazy
import com.ivianuu.injekt.InjektTrait
import com.ivianuu.injekt.Module
import com.ivianuu.injekt.android.fragmentComponent
import com.ivianuu.injekt.inject
import com.ivianuu.traveler.Router
import com.ivianuu.traveler.goBack

/**
 * Base dialog fragment
 */
abstract class EsDialogFragment : DialogFragment(), BackHandler, ContextAware, InjektTrait,
    MvRxView {

    override val component by unsafeLazy {
        fragmentComponent(
            modules = listOf(keyModule(arguments)) + modules()
        )
    }

    override val providedContext: Context
        get() = requireContext()

    val router by inject<Router>()

    private var dismissed = false

    override fun onStart() {
        super.onStart()
        invalidate()
    }

    override fun dismiss() {
        dismissInternal()
    }

    override fun dismissAllowingStateLoss() {
        dismissInternal()
    }

    override fun onDismiss(dialog: DialogInterface) {
        dismissInternal()
    }

    override fun invalidate() {
    }

    override fun handleBack(): Boolean = false

    protected open fun modules(): List<Module> = emptyList()

    private fun dismissInternal() {
        if (!dismissed) {
            dismissed = true
            router.goBack()
        }
    }

}