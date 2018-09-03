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

package com.ivianuu.essentials.util.ext

import android.content.Intent
import com.ivianuu.essentials.ui.common.*
import com.ivianuu.essentials.ui.traveler.destination.ResultDestination
import com.ivianuu.traveler.ResultListener
import com.ivianuu.traveler.Router
import io.reactivex.Maybe
import io.reactivex.Observable

fun <T : Any> Router.results(resultCode: Int): Observable<T> = Observable.create { e ->
    val listener = object : ResultListener {
        override fun onResult(result: Any) {
            if (!e.isDisposed) {
                e.onNext(result as T)
            }
        }
    }

    e.setCancellable { removeResultListener(resultCode, listener) }

    if (!e.isDisposed) {
        addResultListener(resultCode, listener)
    }
}

fun <T : Any> Router.navigateToForResult(destination: ResultDestination): Maybe<T> =
    results<T>(destination.resultCode)
        .take(1)
        .singleElement()
        .doOnSubscribe { navigateTo(destination) }

fun Router.navigateToForActivityResult(intent: Intent) =
    navigateToForActivityResult(RequestCodeGenerator.generate(), intent)

fun Router.navigateToForActivityResult(resultCode: Int, intent: Intent): Maybe<ActivityResult> {
    val destination = ActivityResultDestination(resultCode, intent, resultCode)
    return navigateToForResult(destination)
}

fun Router.requestPermissions(
    vararg permissions: String
) = requestPermissions(RequestCodeGenerator.generate(), *permissions)

fun Router.requestPermissions(
    resultCode: Int,
    vararg permissions: String
): Maybe<Boolean> {
    val destination = PermissionDestination(
        resultCode,
        permissions.toList().toTypedArray(),
        resultCode
    )

    return navigateToForResult<PermissionResult>(destination)
        .map { it.allGranted }
}