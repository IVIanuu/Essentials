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

package com.ivianuu.essentials.unlock

import android.app.KeyguardManager
import com.ivianuu.essentials.app.androidApplicationContext
import com.ivianuu.essentials.util.d
import com.ivianuu.essentials.util.dispatchers
import com.ivianuu.injekt.Reader
import com.ivianuu.injekt.given
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.withContext
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Reader
suspend fun unlockScreen(): Boolean = withContext(dispatchers.default) {
    if (!given<KeyguardManager>().isKeyguardLocked) return@withContext true

    val result = CompletableDeferred<Boolean>()
    val requestId = UUID.randomUUID().toString()
    requestsById[requestId] = result

    d { "unlock screen $requestId" }

    UnlockScreenActivity.unlock(androidApplicationContext, requestId)

    return@withContext result.await().also {
        d { "unlock result $requestId -> $it" }
    }
}

private val requestsById = ConcurrentHashMap<String, CompletableDeferred<Boolean>>()

internal fun onUnlockScreenResult(requestId: String, success: Boolean) {
    requestsById.remove(requestId)?.complete(success)
}
