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
import androidx.compose.Ambient
import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.onDispose
import androidx.ui.core.input.FocusManager
import com.ivianuu.essentials.util.hideInputMethod

class KeyboardManager(
    private val focusManager: FocusManager,
    private val activity: Activity
) {
    fun showKeyboard(id: String) {
        focusManager.requestFocusById(id)
    }

    fun hideKeyboard() {
        activity.hideInputMethod()
    }
}

val KeyboardManagerAmbient = Ambient.of<KeyboardManager>()

@Composable
fun hideKeyboardOnDispose() {
    val keyboardManager = ambient(KeyboardManagerAmbient)
    onDispose { keyboardManager.hideKeyboard() }
}