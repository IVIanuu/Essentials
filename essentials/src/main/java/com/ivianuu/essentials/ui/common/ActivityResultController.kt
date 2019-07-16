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

package com.ivianuu.essentials.ui.common

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ivianuu.director.common.addActivityResultListener
import com.ivianuu.director.common.startActivityForResult
import com.ivianuu.director.requireActivity
import com.ivianuu.essentials.ui.base.EsController
import com.ivianuu.essentials.ui.traveler.NavOptions
import com.ivianuu.essentials.ui.traveler.ResultKey
import com.ivianuu.essentials.ui.traveler.dialog
import com.ivianuu.essentials.ui.traveler.key.ControllerKey
import com.ivianuu.essentials.ui.traveler.popWithResult
import com.ivianuu.injekt.inject

data class ActivityResultKey(
    val intent: Intent,
    override var resultCode: Int = 0
) : ControllerKey(::ActivityResultController, NavOptions().dialog()),
    ResultKey<ActivityResult>

/**
 * Activity result controller
 */
class ActivityResultController : EsController() {

    private val key by inject<ActivityResultKey>()

    override fun onCreate() {
        super.onCreate()

        addActivityResultListener(key.resultCode) { requestCode, resultCode, data ->
            travelerRouter.popWithResult(
                key.resultCode,
                ActivityResult(requestCode, resultCode, data)
            )
        }

        startActivityForResult(key.intent, key.resultCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup
    ): View = View(requireActivity()) // dummy

}

data class ActivityResult(
    val requestCode: Int,
    val resultCode: Int,
    val data: Intent?
)

val ActivityResult.isOk: Boolean get() = resultCode == Activity.RESULT_OK
val ActivityResult.isCanceled: Boolean get() = resultCode == Activity.RESULT_CANCELED
val ActivityResult.isFirstUser: Boolean get() = resultCode == Activity.RESULT_FIRST_USER