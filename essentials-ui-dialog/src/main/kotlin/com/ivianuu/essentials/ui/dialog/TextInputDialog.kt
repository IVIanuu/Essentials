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

package com.ivianuu.essentials.ui.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.*

@Composable fun TextInputDialog(
  modifier: Modifier = Modifier,
  value: String,
  onValueChange: (String) -> Unit,
  label: @Composable (() -> Unit)? = null,
  icon: @Composable (() -> Unit)? = null,
  title: @Composable (() -> Unit)? = null,
  buttons: @Composable (() -> Unit)? = null,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
  Dialog(
    modifier = modifier,
    icon = icon,
    title = title,
    content = {
      Box {
        val focusRequester = remember { FocusRequester() }
        var textFieldValue by remember {
          mutableStateOf(TextFieldValue(value, TextRange(value.length)))
        }
        textFieldValue = textFieldValue.copy(value)
        TextField(
          modifier = Modifier.focusRequester(focusRequester),
          value = textFieldValue,
          onValueChange = {
            textFieldValue = it
            onValueChange(it.text)
          },
          keyboardOptions = keyboardOptions,
          textStyle = MaterialTheme.typography.subtitle1,
          label = label ?: {}
        )

        DisposableEffect(true) {
          focusRequester.requestFocus()
          onDispose { }
        }
      }
    },
    buttons = buttons
  )
}
