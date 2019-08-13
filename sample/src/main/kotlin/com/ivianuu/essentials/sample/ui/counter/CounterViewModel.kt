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

import com.ivianuu.essentials.hidenavbar.NavBarConfig
import com.ivianuu.essentials.hidenavbar.NavBarController
import com.ivianuu.essentials.sample.work.WorkScheduler
import com.ivianuu.essentials.securesettings.SecureSettingsHelper
import com.ivianuu.essentials.securesettings.SecureSettingsRoute
import com.ivianuu.essentials.twilight.TwilightSettingsRoute
import com.ivianuu.essentials.ui.compose.navigation.Navigator
import com.ivianuu.essentials.ui.mvrx.MvRxViewModel
import com.ivianuu.injekt.Inject
import com.ivianuu.injekt.Param

@Inject
class CounterViewModel(
    @Param screen: Int,
    @Param private val navigator: Navigator,
    private val navBarController: NavBarController,
    private val secureSettingsHelper: SecureSettingsHelper,
    private val workScheduler: WorkScheduler
) : MvRxViewModel<CounterState>(CounterState(screen)) {

    private var navBarHidden = false

    fun screenUpClicked() {
        //navigator.push(counterRoute())
    }

    fun screenDownClicked() {
        navigator.pop()
    }

    fun rootScreenClicked() {
        // todo router.popToRoot()
    }

    fun listScreenClicked() {
        //navigator.push(listRoute)
    }

    fun checkAppsClicked() {
        //navigator.push(checkAppsRoute)
    }

    fun doWorkClicked() {
        workScheduler.scheduleWork()
    }

    fun twilightClicked() {
        navigator.push(TwilightSettingsRoute())
    }

    fun materialListClicked() {
        //navigator.push(materialListRoute)
    }

    fun navBarClicked() {
        if (secureSettingsHelper.canWriteSecureSettings()) {
            navBarHidden = !navBarHidden
            navBarController.setNavBarConfig(NavBarConfig(navBarHidden))
        } else {
            navigator.push(SecureSettingsRoute(true))
        }
    }
}

data class CounterState(val screen: Int)