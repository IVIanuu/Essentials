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

package com.ivianuu.essentials.app

import com.ivianuu.essentials.util.GlobalScope
import com.ivianuu.essentials.util.Logger
import com.ivianuu.injekt.ApplicationComponent
import com.ivianuu.injekt.Module
import com.ivianuu.injekt.Provider
import com.ivianuu.injekt.Reader
import com.ivianuu.injekt.composition.BindingEffect
import com.ivianuu.injekt.composition.installIn
import com.ivianuu.injekt.get
import com.ivianuu.injekt.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

/**
 * Runs while the app is alive
 */
interface AppWorker {
    suspend fun run()
}

@BindingEffect(ApplicationComponent::class)
annotation class BindAppWorker {
    companion object {
        @Module
        inline operator fun <reified T : AppWorker> invoke() {
            map<KClass<*>, AppWorker> {
                put<T>(T::class)
            }
        }
    }
}

@Module
fun EsAppWorkerModule() {
    installIn<ApplicationComponent>()
    map<KClass<*>, AppWorker>()
}

@Reader
fun runAppWorkers() {
    get<Map<KClass<*>, @Provider () -> AppWorker>>()
        .forEach {
            get<@GlobalScope CoroutineScope>().launch {
                get<Logger>().d(tag = "AppWorkers", message = "run worker ${it.key.java.name}")
                it.value().run()
            }
        }
}
