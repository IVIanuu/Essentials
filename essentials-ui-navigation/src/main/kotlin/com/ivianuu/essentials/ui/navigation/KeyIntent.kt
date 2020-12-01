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
import com.ivianuu.injekt.Decorator
import com.ivianuu.injekt.Effect
import com.ivianuu.injekt.ForEffect
import com.ivianuu.injekt.FunBinding
import com.ivianuu.injekt.MapEntries
import com.ivianuu.injekt.android.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

@Effect
annotation class KeyIntentFactoryBinding {
    companion object {
        @Suppress("UNCHECKED_CAST")
        @MapEntries
        inline fun <T : KeyIntentFactory<K>, reified K : Key> bind(
            noinline factoryProvider: () -> @ForEffect T,
        ): KeyIntentFactories = mapOf(
            K::class to factoryProvider as () -> KeyIntentFactory<Key>
        )
    }
}

typealias KeyIntentFactory<K> = (K) -> Intent

typealias KeyIntentFactories = Map<KClass<out Key>, () -> KeyIntentFactory<Key>>