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

package com.ivianuu.essentials.apps.ui

import androidx.lifecycle.viewModelScope
import com.ivianuu.compose.ComponentComposition
import com.ivianuu.essentials.apps.AppInfo
import com.ivianuu.essentials.apps.AppStore
import com.ivianuu.essentials.ui.compose.AppBar
import com.ivianuu.essentials.ui.compose.ListItem
import com.ivianuu.essentials.ui.compose.Scaffold
import com.ivianuu.essentials.ui.compose.SimpleLoading
import com.ivianuu.essentials.ui.compose.navigation.Navigator
import com.ivianuu.essentials.ui.compose.navigation.Route
import com.ivianuu.essentials.ui.compose.navigation.navigator
import com.ivianuu.essentials.ui.mvrx.MvRxViewModel
import com.ivianuu.essentials.ui.mvrx.injectMvRxViewModel
import com.ivianuu.essentials.util.AppDispatchers
import com.ivianuu.essentials.util.Async
import com.ivianuu.essentials.util.Loading
import com.ivianuu.essentials.util.Success
import com.ivianuu.essentials.util.Uninitialized
import com.ivianuu.injekt.Inject
import com.ivianuu.injekt.Param
import com.ivianuu.injekt.parametersOf

fun ComponentComposition.AppPickerRoute(launchableOnly: Boolean = false) = Route {
    Scaffold(
        appBar = { AppBar(titleRes = R.string.es_title_app_picker) },
        content = {
            val (state, viewModel) = injectMvRxViewModel(AppPickerViewModel::class) {
                parametersOf(launchableOnly, navigator)
            }

            when (state.apps) {
                is Loading -> SimpleLoading()
                is Success -> state.apps()?.forEach { app ->
                    ListItem(
                        title = app.appName,
                        onClick = { viewModel.appClicked(app) },
                        leadingAction = {
                            /*val coroutineScope = coroutineScope
                            Avatar()
                               val avatar = findView<ImageView>(R.id.es_list_avatar)
                                Glide.with(avatar)
                                    .load(AppIcon(app.packageName))
                                    .apply(
                                        RequestOptions()
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                    )
                                    .into(avatar)
                             */
                        }
                    )
                }
            }
        }
    )
}

@Inject
internal class AppPickerViewModel(
    @Param private val launchableOnly: Boolean,
    private val appStore: AppStore,
    dispatchers: AppDispatchers,
    @Param private val navigator: Navigator
) : MvRxViewModel<AppPickerState>(AppPickerState()) {

    init {
        viewModelScope.execute(
            context = dispatchers.io,
            block = {
                if (launchableOnly) {
                    appStore.getLaunchableApps()
                } else {
                    appStore.getInstalledApps()
                }
            },
            reducer = { copy(apps = it) }
        )
    }

    fun appClicked(app: AppInfo) {
        navigator.pop(app)
    }
}

internal data class AppPickerState(val apps: Async<List<AppInfo>> = Uninitialized)