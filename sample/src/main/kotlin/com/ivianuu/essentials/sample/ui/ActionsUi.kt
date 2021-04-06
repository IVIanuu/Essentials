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

package com.ivianuu.essentials.sample.ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import com.ivianuu.essentials.coroutines.ScopeCoroutineScope
import com.ivianuu.essentials.gestures.action.executeAction
import com.ivianuu.essentials.gestures.action.ui.picker.ActionPickerKey
import com.ivianuu.essentials.store.Collector
import com.ivianuu.essentials.ui.UiGivenScope
import com.ivianuu.essentials.ui.layout.center
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.Key
import com.ivianuu.essentials.ui.navigation.KeyUi
import com.ivianuu.essentials.ui.navigation.NavigationAction
import com.ivianuu.essentials.ui.navigation.pushForResult
import com.ivianuu.injekt.Given
import kotlinx.coroutines.launch

@Given
val actionsHomeItem = HomeItem("Actions") { ActionsKey() }

class ActionsKey : Key<Nothing>

@Given
fun actionsUi(
    @Given executeAction: executeAction,
    @Given navigator: Collector<NavigationAction>,
    @Given scope: ScopeCoroutineScope<UiGivenScope>,
): KeyUi<ActionsKey> = {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Actions") }) }
    ) {
        Button(
            modifier = Modifier.center(),
            onClick = {
                scope.launch {
                    val action = navigator.pushForResult(ActionPickerKey())
                        ?.let { it as? ActionPickerKey.Result.Action }
                        ?.actionId ?: return@launch

                    executeAction(action)
                }
            }
        ) { Text("Pick action") }
    }
}
