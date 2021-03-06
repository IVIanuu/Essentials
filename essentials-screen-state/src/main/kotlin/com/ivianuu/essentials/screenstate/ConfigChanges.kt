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

package com.ivianuu.essentials.screenstate

import android.content.*
import android.content.res.*
import com.ivianuu.essentials.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.android.*
import com.ivianuu.injekt.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*

typealias ConfigChange = Unit

@Provide fun configChanges(
  context: AppContext,
  mainDispatcher: MainDispatcher,
): Flow<ConfigChange> = callbackFlow<ConfigChange> {
  val callbacks = object : ComponentCallbacks2 {
    override fun onConfigurationChanged(newConfig: Configuration) {
      trySend(ConfigChange)
    }

    override fun onLowMemory() {
    }

    override fun onTrimMemory(level: Int) {
    }
  }
  context.registerComponentCallbacks(callbacks)
  awaitClose { context.unregisterComponentCallbacks(callbacks) }
}.flowOn(mainDispatcher)
