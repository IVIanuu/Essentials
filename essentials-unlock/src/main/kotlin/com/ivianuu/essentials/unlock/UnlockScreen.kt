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

package com.ivianuu.essentials.unlock

import android.app.KeyguardManager
import com.ivianuu.essentials.coroutines.DefaultDispatcher
import com.ivianuu.essentials.util.Logger
import com.ivianuu.essentials.util.d
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.GivenFun
import com.ivianuu.injekt.android.AppContext
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.withContext
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@GivenFun
suspend fun unlockScreen(
    @Given appContext: AppContext,
    @Given defaultDispatcher: DefaultDispatcher,
    @Given logger: Logger,
    @Given keyguardManager: KeyguardManager,
): Boolean = withContext(defaultDispatcher) {
    if (!keyguardManager.isKeyguardLocked) return@withContext true

    val result = CompletableDeferred<Boolean>()
    val requestId = UUID.randomUUID().toString()
    requestsById[requestId] = result

    logger.d { "unlock screen $requestId" }

    UnlockScreenActivity.unlock(appContext, requestId)

    return@withContext result.await().also {
        logger.d { "unlock result $requestId -> $it" }
    }
}

private val requestsById = ConcurrentHashMap<String, CompletableDeferred<Boolean>>()

internal fun onUnlockScreenResult(requestId: String, success: Boolean) {
    requestsById.remove(requestId)?.complete(success)
}
