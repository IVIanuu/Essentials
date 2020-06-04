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

package com.ivianuu.essentials.activityresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ivianuu.essentials.util.Logger
import com.ivianuu.injekt.ApplicationScoped
import com.ivianuu.injekt.ForApplication
import kotlinx.coroutines.CompletableDeferred
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@ApplicationScoped
class ActivityResultController(
    private val context: @ForApplication Context,
    private val logger: Logger
) {

    private val resultsByRequestCode = ConcurrentHashMap<Int, CompletableDeferred<ActivityResult>>()

    suspend fun startForResult(
        intent: Intent,
        options: Bundle? = null
    ): ActivityResult {
        val requestCode = requestCodes.incrementAndGet()
        logger.d("start for result $requestCode $intent $options")
        val result = CompletableDeferred<ActivityResult>()
        resultsByRequestCode[requestCode] = result
        ActivityResultActivity.startActivityForResult(context, intent, requestCode, options)
        val resultValue = result.await()
        logger.d("got result for $requestCode = $resultValue")
        return resultValue
    }

    internal fun onActivityResult(result: ActivityResult) {
        resultsByRequestCode.remove(result.requestCode)
            ?.complete(result)
    }

    private companion object {
        private val requestCodes = AtomicInteger()
    }
}
