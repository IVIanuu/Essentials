/*
 * Copyright 2019 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of

private class CounterChangeHandler : SharedElementTransitionChangeHandler() {

    override fun getSharedElementTransition(changeData: ChangeData) =
        transitionSetOf(ChangeBounds(), ChangeTransform())

    override fun getEnterTransition(changeData: ChangeData) = Slide(Gravity.END)

    override fun getExitTransition(changeData: ChangeData) = Slide(Gravity.START)

    override fun configureSharedElements(changeData: ChangeData) {
        addSharedElement("count")
    }

    override fun copy() = CounterChangeHandler()
}the License at
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

import com.ivianuu.essentials.sample.R
import com.ivianuu.essentials.ui.director.controllerRoute
import com.ivianuu.essentials.ui.director.mvrxViewModel
import com.ivianuu.injekt.parametersOf
import kotlinx.android.synthetic.main.controller_counter.*

fun counterRoute(screen: Int) = controllerRoute(
        layoutRes = R.layout.controller_counter,
        render = {
                val viewModel = mvrxViewModel<CounterViewModel> { parametersOf(screen) }

        screen_up.setOnClickListener { viewModel.screenUpClicked() }
        screen_down.setOnClickListener { viewModel.screenDownClicked() }
        root_screen.setOnClickListener { viewModel.rootScreenClicked() }
        list_screen.setOnClickListener { viewModel.listScreenClicked() }
        check_apps.setOnClickListener { viewModel.checkAppsClicked() }
        do_work.setOnClickListener { viewModel.doWorkClicked() }
        nav_bar.setOnClickListener { viewModel.navBarClicked() }
        twilight.setOnClickListener { viewModel.twilightClicked() }
        md_list.setOnClickListener { viewModel.materialListClicked() }

        count.text = "Screen: ${viewModel.state.screen}"
    }
)