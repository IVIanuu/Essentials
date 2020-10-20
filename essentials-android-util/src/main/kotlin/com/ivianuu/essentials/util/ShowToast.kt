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

package com.ivianuu.essentials.util

import android.widget.Toast
import com.ivianuu.essentials.coroutines.GlobalScope
import com.ivianuu.essentials.coroutines.MainDispatcher
import com.ivianuu.injekt.FunBinding
import com.ivianuu.injekt.android.ApplicationContext
import kotlinx.coroutines.launch

typealias showToast = (String) -> Unit
@FunBinding
fun showToast(
    applicationContext: ApplicationContext,
    globalScope: GlobalScope,
    mainDispatcher: MainDispatcher,
    message: String
) {
    globalScope.launch(mainDispatcher) {
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}

typealias showToastRes = (Int) -> Unit
@FunBinding
fun showToastRes(
    showToast: showToast,
    stringResource: stringResource,
    messageRes: Int
) {
    showToast(stringResource(messageRes))
}
