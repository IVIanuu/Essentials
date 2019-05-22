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

package com.ivianuu.essentials.sample.ui.counter

import android.os.Bundle
import android.view.View
import com.ivianuu.essentials.sample.R
import com.ivianuu.essentials.ui.base.EsFragment
import com.ivianuu.essentials.ui.mvrx.injekt.injectMvRxViewModel
import com.ivianuu.essentials.ui.mvrx.withState
import com.ivianuu.essentials.ui.traveler.anim.NavOptions
import com.ivianuu.essentials.ui.traveler.anim.horizontal
import com.ivianuu.essentials.ui.traveler.key.FragmentKey
import com.ivianuu.essentials.ui.traveler.key.getKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.controller_counter.*

@Parcelize
data class CounterKey(val screen: Int) : FragmentKey(
    ::CounterFragment,
    options = NavOptions().horizontal()
)

class CounterFragment : EsFragment() {

    override fun modules() = listOf(counterModule)

    override val layoutRes: Int get() = R.layout.controller_counter

    private val viewModel: CounterViewModel by injectMvRxViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screen_up.setOnClickListener { viewModel.screenUpClicked() }
        screen_down.setOnClickListener { viewModel.screenDownClicked() }
        root_screen.setOnClickListener { viewModel.rootScreenClicked() }
        list_screen.setOnClickListener { viewModel.listScreenClicked() }
        check_apps.setOnClickListener { viewModel.checkAppsClicked() }
        do_work.setOnClickListener { viewModel.doWorkClicked() }
    }

    override fun invalidate() {
        withState(viewModel) { count.text = "Screen : ${it.screen}" }
    }

    override fun toString() = "CounterFragment(${getKey<CounterKey>().screen})"
}