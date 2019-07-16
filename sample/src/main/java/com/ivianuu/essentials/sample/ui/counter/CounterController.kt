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

package com.ivianuu.essentials.sample.ui.counter

import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.Slide
import android.view.Gravity
import android.view.View
import com.ivianuu.director.ChangeData
import com.ivianuu.director.common.changehandler.SharedElementTransitionChangeHandler
import com.ivianuu.essentials.sample.R
import com.ivianuu.essentials.ui.base.EsController
import com.ivianuu.essentials.ui.mvrx.injekt.injectMvRxViewModel
import com.ivianuu.essentials.ui.traveler.NavOptions
import com.ivianuu.essentials.ui.traveler.handler
import com.ivianuu.essentials.ui.traveler.key.ControllerKey
import com.ivianuu.kommon.core.transition.transitionSetOf
import kotlinx.android.synthetic.main.controller_counter.*

data class CounterKey(val screen: Int) :
    ControllerKey(::CounterController, NavOptions().handler(CounterChangeHandler()))

class CounterChangeHandler : SharedElementTransitionChangeHandler() {

    override fun getSharedElementTransition(changeData: ChangeData) =
        transitionSetOf(ChangeBounds(), ChangeTransform())

    override fun getEnterTransition(changeData: ChangeData) = Slide(Gravity.END)

    override fun getExitTransition(changeData: ChangeData) = Slide(Gravity.START)

    override fun configureSharedElements(changeData: ChangeData) {
        addSharedElement("count")
    }

    override fun copy() = CounterChangeHandler()
}

class CounterController : EsController() {

    override val layoutRes: Int get() = R.layout.controller_counter

    private val viewModel: CounterViewModel by injectMvRxViewModel()

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)

        screen_up.setOnClickListener { viewModel.screenUpClicked() }
        screen_down.setOnClickListener { viewModel.screenDownClicked() }
        root_screen.setOnClickListener { viewModel.rootScreenClicked() }
        list_screen.setOnClickListener { viewModel.listScreenClicked() }
        check_apps.setOnClickListener { viewModel.checkAppsClicked() }
        do_work.setOnClickListener { viewModel.doWorkClicked() }
        nav_bar.setOnClickListener { viewModel.navBarClicked() }
        twilight.setOnClickListener { viewModel.twilightClicked() }
        md_list.setOnClickListener { viewModel.materialListClicked() }
    }

    override fun invalidate() {
        count.text = "Screen: ${viewModel.state.screen}"
    }

}