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

package com.ivianuu.essentials.sample.tile

import com.ivianuu.essentials.store.Actions
import com.ivianuu.essentials.store.DispatchAction
import com.ivianuu.essentials.store.state
import com.ivianuu.essentials.tile.TileAction
import com.ivianuu.essentials.tile.TileBinding
import com.ivianuu.essentials.tile.TileState
import com.ivianuu.essentials.twilight.data.TwilightMode
import com.ivianuu.essentials.twilight.data.TwilightPrefsAction
import com.ivianuu.essentials.twilight.data.TwilightPrefsAction.*
import com.ivianuu.essentials.twilight.data.TwilightPrefsState
import com.ivianuu.essentials.ui.store.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance

@TileBinding(1)
fun TestTile(
    scope: CoroutineScope,
    actions: Actions<TileAction>,
    dispatch: DispatchAction<TwilightPrefsAction>,
    twilightPrefsState: StateFlow<TwilightPrefsState>
) = scope.state(twilightPrefsState.value.toTileState()) {
    twilightPrefsState.reduce { it.toTileState() }
    actions.filterIsInstance<TileAction>().effect {
        dispatch(
            UpdateTwilightMode(
                if (twilightPrefsState.value.twilightMode == TwilightMode.Light) TwilightMode.Dark
                else TwilightMode.Light
            )
        )
    }
}

private fun TwilightPrefsState.toTileState() = TileState(
    label = twilightMode.name,
    status = if (twilightMode == TwilightMode.Light) TileState.Status.Active
    else TileState.Status.Inactive
)
