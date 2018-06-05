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

package com.ivianuu.essentials.util

import android.content.Context
import android.content.Intent
import com.ivianuu.essentials.injection.ForApp
import com.ivianuu.essentials.util.ext.intentFilterOf
import com.ivianuu.essentials.util.ext.registerReceiver
import com.ivianuu.essentials.util.ext.unregisterReceiverSafe
import io.reactivex.Observable
import javax.inject.Inject

/**
 * A factory for broadcast receiver observables
 */
class BroadcastFactory @Inject constructor(@ForApp private val context: Context) {

    fun create(vararg actions: String): Observable<Intent> {
        return Observable.create { e ->
            val receiver = context.registerReceiver(intentFilterOf(*actions)) {
                e.onNext(it)
            }

            e.setCancellable { context.unregisterReceiverSafe(receiver) }
        }
    }

}