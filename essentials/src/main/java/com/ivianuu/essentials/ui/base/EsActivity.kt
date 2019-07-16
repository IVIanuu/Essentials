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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ivianuu.director.Router
import com.ivianuu.director.hasRoot
import com.ivianuu.director.router
import com.ivianuu.director.traveler.ControllerNavigator
import com.ivianuu.essentials.ui.mvrx.MvRxView
import com.ivianuu.essentials.ui.traveler.key.keyModule
import com.ivianuu.essentials.util.unsafeLazy
import com.ivianuu.injekt.InjektTrait
import com.ivianuu.injekt.Module
import com.ivianuu.injekt.android.activityComponent
import com.ivianuu.injekt.inject
import com.ivianuu.traveler.Navigator
import com.ivianuu.traveler.android.AppNavigator
import com.ivianuu.traveler.android.setNavigator
import com.ivianuu.traveler.common.ResultNavigator
import com.ivianuu.traveler.common.compositeNavigatorOf
import com.ivianuu.traveler.pop
import com.ivianuu.traveler.setRoot

/**
 * Base activity
 */
abstract class EsActivity : AppCompatActivity(), InjektTrait, MvRxView {

    override val component by unsafeLazy {
        activityComponent {
            modules(listOf(keyModule(intent.extras, false)))
            modules(this@EsActivity.modules())
        }
    }

    val travelerRouter by inject<com.ivianuu.traveler.Router>()

    protected open val layoutRes get() = 0

    open val containerId
        get() = android.R.id.content

    open val startKey: Any?
        get() = null

    lateinit var router: Router
        private set

    protected open val navigator: Navigator by unsafeLazy {
        val navigators = mutableListOf<ResultNavigator>().apply {
            addAll(navigators())
            add(ControllerNavigator(router))
            add(AppNavigator(this@EsActivity))
        }

        compositeNavigatorOf(navigators)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (layoutRes != 0) {
            setContentView(layoutRes)
        }

        router = createRouter()

        navigateToStartKeyIfNeeded()
    }

    override fun onStart() {
        super.onStart()
        invalidate()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        travelerRouter.setNavigator(this, navigator)
    }

    override fun onBackPressed() {
        if (router.backstack.size > 1) {
            travelerRouter.pop()
        } else {
            super.onBackPressed()
        }
    }

    override fun invalidate() {
    }

    protected open fun navigators(): List<ResultNavigator> = emptyList()

    protected open fun modules(): List<Module> = emptyList()

    protected open fun createRouter(): Router = router(containerId)

    protected open fun navigateToStartKeyIfNeeded() {
        if (!router.hasRoot) {
            startKey?.let { travelerRouter.setRoot(it) }
        }
    }

}