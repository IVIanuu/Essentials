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

package com.ivianuu.essentials.android.ui.core

import android.view.inputmethod.InputMethodManager
import androidx.compose.Composable
import androidx.compose.Stable
import androidx.compose.onDispose
import androidx.compose.staticAmbientOf
import androidx.ui.core.AndroidComposeView
import androidx.ui.core.input.FocusManager

@Stable
class KeyboardManager(
    private val focusManager: FocusManager,
    private val composeView: AndroidComposeView,
    private val inputMethodManager: InputMethodManager
) {
    fun showKeyboard(id: String) {
        focusManager.requestFocusById(id)
    }

    fun hideKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(composeView.windowToken, 0)
    }
}

val KeyboardManagerAmbient =
    staticAmbientOf<KeyboardManager> { error("No keyboard manager found") }

@Composable
fun hideKeyboardOnDispose() {
    val keyboardManager = KeyboardManagerAmbient.current
    onDispose { keyboardManager.hideKeyboard() }
}