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

package com.ivianuu.essentials.permission.intent

import android.content.*
import com.ivianuu.essentials.coroutines.*
import com.ivianuu.essentials.permission.*
import com.ivianuu.essentials.ui.navigation.*
import com.ivianuu.injekt.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

typealias PermissionIntentFactory<P> = (P) -> Intent

@Given
fun <P : Permission> permissionIntentRequestHandler(
    @Given intentFactory: PermissionIntentFactory<P>,
    @Given navigator: Navigator,
    @Given state: Flow<PermissionState<P>>
): PermissionRequestHandler<P> = { permission ->
    raceOf(
        {
            // wait until user navigates back from the permission screen
            navigator.pushForResult(intentFactory(permission).toIntentKey())
        },
        {
            // wait until user granted permission
            while (!state.first()) delay(100)
        }
    )
}
