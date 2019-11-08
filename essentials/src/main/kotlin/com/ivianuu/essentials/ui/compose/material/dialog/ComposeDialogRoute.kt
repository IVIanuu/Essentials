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

package com.ivianuu.essentials.ui.compose.material.dialog

import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.unaryPlus
import com.ivianuu.essentials.ui.compose.composeControllerRoute
import com.ivianuu.essentials.ui.compose.dialog.DialogManagerAmbient
import com.ivianuu.essentials.ui.compose.dialog.DismissDialogAmbient
import com.ivianuu.essentials.ui.compose.injekt.inject
import com.ivianuu.essentials.ui.compose.material.Scaffold
import com.ivianuu.essentials.ui.navigation.Navigator
import com.ivianuu.essentials.ui.navigation.director.controllerRouteOptions
import com.ivianuu.essentials.ui.navigation.director.dialog

fun composeDialogRoute(
    dialog: @Composable() (() -> Unit) -> Unit
) = composeControllerRoute(options = controllerRouteOptions().dialog()) {
    Scaffold(
        body = {
            val dialogManager = +ambient(DialogManagerAmbient)
            dialogManager.showDialog { dismissDialog ->
                val navigator = +inject<Navigator>()
                val realDismissDialog = {
                    dismissDialog()
                    navigator.pop()
                }
                DismissDialogAmbient.Provider(realDismissDialog) {
                    dialog(realDismissDialog)
                }
            }
        }
    )
}