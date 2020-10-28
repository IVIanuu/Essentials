/*
 * Copyright 2020 Manuel Wrage
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

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.onCommit
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ivianuu.essentials.apps.AppInfo
import com.ivianuu.essentials.apps.coil.AppIcon
import com.ivianuu.essentials.apps.getInstalledApps
import com.ivianuu.essentials.apps.ui.CheckableAppsAction.DeselectAll
import com.ivianuu.essentials.apps.ui.CheckableAppsAction.SelectAll
import com.ivianuu.essentials.apps.ui.CheckableAppsAction.ToggleApp
import com.ivianuu.essentials.apps.ui.CheckableAppsAction.UpdateRefs
import com.ivianuu.essentials.store.currentState
import com.ivianuu.essentials.store.storeProvider
import com.ivianuu.essentials.ui.core.Text
import com.ivianuu.essentials.ui.material.ListItem
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.popup.PopupMenu
import com.ivianuu.essentials.ui.popup.PopupMenuButton
import com.ivianuu.essentials.ui.resource.Idle
import com.ivianuu.essentials.ui.resource.Resource
import com.ivianuu.essentials.ui.resource.ResourceLazyColumnFor
import com.ivianuu.essentials.ui.resource.map
import com.ivianuu.essentials.ui.store.component1
import com.ivianuu.essentials.ui.store.component2
import com.ivianuu.essentials.ui.store.executeIn
import com.ivianuu.essentials.ui.store.rememberStore
import com.ivianuu.essentials.util.exhaustive
import com.ivianuu.injekt.Assisted
import com.ivianuu.injekt.Binding
import com.ivianuu.injekt.FunBinding
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

@FunBinding
@Composable
fun CheckableAppsPage(
    store: rememberStore<CheckableAppsState, CheckableAppsAction>,
    checkedApps: @Assisted Set<String>,
    onCheckedAppsChanged: @Assisted (Set<String>) -> Unit,
    appFilter: @Assisted AppFilter,
    appBarTitle: @Assisted String
) {
    val (state, dispatch) = store()

    onCommit(checkedApps, onCheckedAppsChanged, appFilter) {
        dispatch(UpdateRefs(checkedApps, onCheckedAppsChanged, appFilter))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(appBarTitle) },
                actions = {
                    PopupMenuButton(
                        items = listOf(
                            PopupMenu.Item(
                                onSelected = {
                                    dispatch(SelectAll)
                                }
                            ) {
                                Text(R.string.es_select_all)
                            },
                            PopupMenu.Item(
                                onSelected = {
                                    dispatch(DeselectAll)
                                }
                            ) {
                                Text(R.string.es_deselect_all)
                            }
                        )
                    )
                }
            )
        }
    ) {
        ResourceLazyColumnFor(state.checkableApps) { app ->
            CheckableApp(
                app = app,
                onClick = { dispatch(ToggleApp(app)) }
            )
        }
    }
}

@Composable
private fun CheckableApp(
    onClick: () -> Unit,
    app: CheckableApp
) {
    ListItem(
        title = { Text(app.info.appName) },
        leading = {
            CoilImage(
                data = AppIcon(packageName = app.info.packageName),
                modifier = Modifier.size(40.dp)
            )
        },
        trailing = {
            Checkbox(
                checked = app.isChecked,
                onCheckedChange = { onClick() }
            )
        },
        onClick = onClick
    )
}

@Binding
fun checkableAppsStore(
    getInstalledApps: getInstalledApps,
) = storeProvider<CheckableAppsState, CheckableAppsAction>(CheckableAppsState()) {
    val installedApps = async { getInstalledApps() }
    state
        .map { it.appFilter }
        .distinctUntilChanged()
        .mapLatest { installedApps.await().filter(it) }
        .executeIn(this) { copy(apps = it) }

    for (action in this) {
        suspend fun pushNewCheckedApps(reducer: (MutableSet<String>) -> Unit) {
            val currentState = currentState()
            val newCheckedApps = currentState.checkableApps()!!
                .filter { it.isChecked }
                .map { it.info.packageName }
                .toMutableSet()
                .apply(reducer)
            currentState.onCheckedAppsChanged!!(newCheckedApps)
        }

        when (action) {
            is UpdateRefs -> {
                setState {
                    copy(
                        checkedApps = action.checkedApps,
                        onCheckedAppsChanged = action.onCheckedAppsChanged,
                        appFilter = action.appFilter
                    )
                }
            }
            is ToggleApp -> {
                pushNewCheckedApps {
                    if (!action.app.isChecked) {
                        it += action.app.info.packageName
                    } else {
                        it -= action.app.info.packageName
                    }
                }
            }
            SelectAll -> {
                currentState().apps()?.let { allApps ->
                    pushNewCheckedApps { newApps ->
                        newApps += allApps.map { it.packageName }
                    }
                }
            }
            DeselectAll -> {
                pushNewCheckedApps { it.clear() }
            }
        }.exhaustive
    }
}

@Immutable
data class CheckableApp(
    val info: AppInfo,
    val isChecked: Boolean
)

@Immutable
data class CheckableAppsState(
    val apps: Resource<List<AppInfo>> = Idle,
    val checkedApps: Set<String> = emptySet(),
    val onCheckedAppsChanged: ((Set<String>) -> Unit)? = null,
    val appFilter: AppFilter = DefaultAppFilter,
) {
    val checkableApps = apps
        .map { it.filter(appFilter) }
        .map { apps ->
            apps.map { app ->
                CheckableApp(
                    info = app,
                    isChecked = app.packageName in checkedApps
                )
            }
        }
}

sealed class CheckableAppsAction {
    data class UpdateRefs(
        val checkedApps: Set<String>,
        val onCheckedAppsChanged: (Set<String>) -> Unit,
        val appFilter: AppFilter
    ) : CheckableAppsAction()

    data class ToggleApp(val app: CheckableApp) : CheckableAppsAction()
    object SelectAll : CheckableAppsAction()
    object DeselectAll : CheckableAppsAction()
}
