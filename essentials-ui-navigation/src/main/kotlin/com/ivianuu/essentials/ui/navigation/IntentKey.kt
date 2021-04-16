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

package com.ivianuu.essentials.ui.navigation

import android.content.*
import androidx.activity.*
import androidx.activity.result.*
import androidx.activity.result.contract.*
import com.ivianuu.essentials.coroutines.*
import com.ivianuu.essentials.util.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.android.*
import com.ivianuu.injekt.scope.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.*
import kotlin.reflect.*

interface IntentKey : Key<ActivityResult>

@Given
fun <@Given T : KeyIntentFactory<K>, K : Key<*>> keyIntentFactoryElement(
    @Given intentFactory: T,
    @Given keyClass: KClass<K>
): Pair<KClass<IntentKey>, KeyIntentFactory<IntentKey>> = (keyClass to intentFactory).cast()

typealias KeyIntentFactory<T> = (T) -> Intent

typealias IntentAppUiStarter = suspend () -> ComponentActivity

typealias IntentKeyHandler = (Key<*>, ((ActivityResult) -> Unit)?) -> Boolean

@Given
fun intentKeyHandler(
    @Given appContext: AppContext,
    @Given appUiStarter: IntentAppUiStarter,
    @Given dispatcher: MainDispatcher,
    @Given intentFactories: Map<KClass<IntentKey>, KeyIntentFactory<IntentKey>>,
    @Given scope: ScopeCoroutineScope<AppGivenScope>
): IntentKeyHandler = handler@ { key, onResult ->
    if (key !is IntentKey) return@handler false
    val intentFactory = intentFactories[key::class]
    if (intentFactory != null) {
        val intent = intentFactory(key)
        if (onResult != null) {
            scope.launch {
                val activity = appUiStarter()
                withContext(dispatcher) {
                    val result = suspendCancellableCoroutine<ActivityResult> { continuation ->
                        val launcher = activity.activityResultRegistry.register(
                            UUID.randomUUID().toString(),
                            ActivityResultContracts.StartActivityForResult()
                        ) { continuation.resume(it) }
                        launcher.launch(intent)
                        continuation.invokeOnCancellation { launcher.unregister() }
                    }
                    onResult(result)
                }
            }
        } else {
            appContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }
    intentFactory != null
}
