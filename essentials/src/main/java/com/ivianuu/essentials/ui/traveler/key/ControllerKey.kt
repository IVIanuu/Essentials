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

package com.ivianuu.essentials.ui.traveler.key

import android.os.Bundle
import android.os.Parcelable
import com.ivianuu.director.Controller
import com.ivianuu.director.RouterTransaction
import com.ivianuu.director.traveler.ControllerKey
import com.ivianuu.essentials.ui.traveler.ControllerNavOptions
import com.ivianuu.essentials.ui.traveler.applyToTransaction
import com.ivianuu.essentials.util.ext.safeAsOrElse
import com.ivianuu.essentials.util.ext.unsafeLazy
import com.ivianuu.traveler.Command
import com.ivianuu.traveler.Forward
import com.ivianuu.traveler.Replace

abstract class ControllerKey(
    val factory: () -> Controller,
    open val defaultNavOptions: ControllerNavOptions? = null
) : ControllerKey, Parcelable {

    override fun createController(data: Any?): Controller = factory().also {
        addTo(it.args)
    }

    override fun setupTransaction(
        command: Command,
        currentController: Controller?,
        nextController: Controller,
        transaction: RouterTransaction
    ) {
        val data = when (command) {
            is Forward -> command.data
            is Replace -> command.data
            else -> null
        }

        data.safeAsOrElse { defaultNavOptions }
            ?.applyToTransaction(transaction)
    }

}

fun com.ivianuu.essentials.ui.traveler.key.ControllerKey.addTo(bundle: Bundle) {
    bundle.putParcelable(TRAVELER_KEY, this)
    bundle.putString(TRAVELER_KEY_CLASS, javaClass.name)
}

fun <T : Parcelable> Controller.getKey(): T = args.getParcelable(TRAVELER_KEY)!!

fun <T : Parcelable> Controller.getKeyOrNull(): T? = try {
    getKey()
} catch (e: Exception) {
    null
}

fun <T : Parcelable> Controller.key(): Lazy<T> = unsafeLazy { getKey<T>() }