/*
 * Copyright 2018 Manuel Wrage
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

package com.ivianuu.essentials.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ivianuu.essentials.injection.bindInstanceModule
import com.ivianuu.essentials.injection.componentName
import com.ivianuu.essentials.util.ContextAware
import com.ivianuu.essentials.util.asMainCoroutineScope
import com.ivianuu.essentials.util.ext.unsafeLazy
import com.ivianuu.injekt.ComponentHolder
import com.ivianuu.injekt.Module
import com.ivianuu.injekt.component
import com.ivianuu.scopes.MutableScope
import com.ivianuu.scopes.Scope

/**
 * Base worker
 */
abstract class EsWorker(
    context: Context, workerParams: WorkerParameters
) : Worker(context, workerParams), ComponentHolder, ContextAware {

    override val component by unsafeLazy {
        component(
            modules = implicitModules() + modules(),
            dependencies = dependencies(),
            name = componentName()
        )
    }

    override val providedContext: Context
        get() = applicationContext

    val scope: Scope get() = _scope
    private val _scope = MutableScope()

    val coroutineScope = scope.asMainCoroutineScope()

    override fun onStopped() {
        _scope.close()
        super.onStopped()
    }

    protected open fun dependencies() =
        (applicationContext as? ComponentHolder)?.component?.let { listOf(it) } ?: emptyList()

    protected open fun modules() = emptyList<Module>()

    protected open fun implicitModules() = listOf(bindInstanceModule(this))
}