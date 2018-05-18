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

package com.ivianuu.essentials.ui.traveler

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelStoreOwner
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.containerId
import com.ivianuu.essentials.ui.base.BaseActivity
import com.ivianuu.essentials.ui.traveler.navigator.FragmentSwapperNavigator.HideStrategy
import com.ivianuu.essentials.ui.traveler.navigator.KeyFragmentAppNavigator
import com.ivianuu.essentials.ui.traveler.navigator.KeyFragmentNavigator
import com.ivianuu.essentials.ui.traveler.navigator.KeyFragmentSwapperNavigator
import com.ivianuu.essentials.util.ext.getViewModel
import com.ivianuu.traveler.Navigator
import com.ivianuu.traveler.Router

val BaseActivity.router: Router
    get() = getRouter(fragmentContainer)

val Fragment.router: Router
    get() {
        val parent = parentFragment
        return parent?.getRouter(containerId) ?: requireActivity().getRouter(containerId)
    }

val Fragment.rootRouter: Router
    get() {
        val parent = parentFragment
        return parent?.rootRouter ?: requireActivity().getRouter(containerId)
    }

fun ViewModelStoreOwner.getTravelerStore() =
    getViewModel<TravelerStore>()

fun ViewModelStoreOwner.getTraveler(containerId: Int) =
    getTravelerStore().getTraveler(containerId)

fun ViewModelStoreOwner.getNavigatorHolder(containerId: Int) =
    getTraveler(containerId).navigatorHolder

fun ViewModelStoreOwner.getRouter(containerId: Int) =
    getTraveler(containerId).router

fun <T> T.setupRouter(
    navigator: Navigator,
    containerId: Int
): Router where T : ViewModelStoreOwner, T : LifecycleOwner {
    val traveler = getTraveler(containerId)
    NavigatorLifecycleObserver(this, navigator, traveler.navigatorHolder)
    return traveler.router
}

fun <T> T.removeRouter(containerId: Int) where T : ViewModelStoreOwner, T : LifecycleOwner {
    getTravelerStore().removeTraveler(containerId)
}

fun FragmentActivity.setupKeyFragmentRouter(containerId: Int) =
    setupKeyFragmentRouter(supportFragmentManager, containerId)

fun Fragment.setupKeyFragmentRouter(containerId: Int) =
    setupKeyFragmentRouter(childFragmentManager, containerId)

fun <T> T.setupKeyFragmentRouter(
    fm: FragmentManager,
    containerId: Int
): Router where T : ViewModelStoreOwner, T : LifecycleOwner {
    val navigator = KeyFragmentNavigator(fm, containerId)
    return setupRouter(navigator, containerId)
}

fun FragmentActivity.setupKeyFragmentAppRouter(containerId: Int) =
    setupKeyFragmentRouter(supportFragmentManager, containerId)

fun Fragment.setupKeyFragmentAppRouter(containerId: Int) =
    setupKeyFragmentRouter(childFragmentManager, containerId)

fun <T> T.setupKeyFragmentAppRouter(
    activity: FragmentActivity, fm: FragmentManager, containerId: Int
): Router where T : ViewModelStoreOwner, T : LifecycleOwner {
    val navigator = KeyFragmentAppNavigator(activity, fm, containerId)
    return setupRouter(navigator, containerId)
}

fun FragmentActivity.setupKeyFragmentSwapperRouter(
    containerId: Int,
    hideStrategy: HideStrategy = HideStrategy.DETACH,
    swapOnReselection: Boolean = true
) = setupKeyFragmentSwapperRouter(
    supportFragmentManager, containerId,
    hideStrategy, swapOnReselection
)

fun Fragment.setupKeyFragmentSwapperRouter(
    containerId: Int,
    hideStrategy: HideStrategy = HideStrategy.DETACH,
    swapOnReselection: Boolean = true
) = setupKeyFragmentSwapperRouter(
    childFragmentManager, containerId,
    hideStrategy, swapOnReselection
)

fun <T> T.setupKeyFragmentSwapperRouter(
    fm: FragmentManager,
    containerId: Int,
    hideStrategy: HideStrategy = HideStrategy.DETACH,
    swapOnReselection: Boolean = true
): Router where T : ViewModelStoreOwner, T : LifecycleOwner {
    val navigator = KeyFragmentSwapperNavigator(
        fm,
        containerId, hideStrategy, swapOnReselection
    )
    return setupRouter(navigator, containerId)
}