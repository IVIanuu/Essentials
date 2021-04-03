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

import android.content.Intent
import com.ivianuu.essentials.util.cast
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.android.AppContext
import kotlin.reflect.KClass

interface IntentKey : Key<Nothing>

class IntentKeyModule<K : IntentKey>(private val keyClass: KClass<K>) {
    @Given
    fun keyIntentFactory(
        @Given intentFactory: KeyIntentFactory<K>
    ): Pair<KClass<IntentKey>, KeyIntentFactory<IntentKey>> = (keyClass to intentFactory).cast()

    companion object {
        inline operator fun <reified K : IntentKey> invoke() = IntentKeyModule(K::class)
    }
}


typealias KeyIntentFactory<T> = (T) -> Intent

typealias IntentKeyHandler = (Key<*>) -> Boolean

@Given
fun intentKeyHandler(
    @Given appContext: AppContext,
    @Given intentFactories: Map<KClass<IntentKey>, KeyIntentFactory<IntentKey>>
): IntentKeyHandler = handler@ { key ->
    if (key !is IntentKey) return@handler false
    val intentFactory = intentFactories[key::class]
    if (intentFactory != null) {
        val intent = intentFactory(key)
        appContext.startActivity(
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
    intentFactory != null
}
