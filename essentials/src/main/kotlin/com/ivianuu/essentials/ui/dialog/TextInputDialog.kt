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

package com.ivianuu.essentials.ui.dialog

import androidx.compose.Composable
import androidx.compose.onActive
import androidx.compose.state
import androidx.ui.core.Opacity
import androidx.ui.core.Text
import androidx.ui.core.TextField
import androidx.ui.input.KeyboardType
import androidx.ui.material.MaterialTheme
import androidx.ui.res.stringResource
import com.ivianuu.essentials.R
import com.ivianuu.essentials.ui.common.asTextComposable
import com.ivianuu.essentials.ui.common.hideKeyboard
import com.ivianuu.essentials.ui.common.showKeyboard
import com.ivianuu.essentials.ui.navigation.navigator

fun TextInputRoute(
    initial: String = "",
    hint: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    title: String? = null,
    allowEmpty: Boolean = true
) = DialogRoute {
    val navigator = navigator

    val (currentValue, setCurrentValue) = state { initial }

    TextInputDialog(
        value = currentValue,
        onValueChange = setCurrentValue,
        hint = hint,
        keyboardType = keyboardType,
        title = title?.asTextComposable(),
        positiveButton = {
            DialogButton(
                text = stringResource(R.string.es_ok),
                dismissDialogOnClick = false,
                onClick = if (allowEmpty || currentValue.isNotEmpty()) {
                    {
                        navigator.pop(result = currentValue)
                    }
                } else {
                    null
                }
            )
        },
        negativeButton = {
            DialogCloseButton(stringResource(R.string.es_cancel))
        }
    )
}

@Composable
fun TextInputDialog(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    buttonLayout: AlertDialogButtonLayout = AlertDialogButtonLayout.SideBySide,
    icon: (@Composable() () -> Unit)? = null,
    title: (@Composable() () -> Unit)? = null,
    positiveButton: (@Composable() () -> Unit)? = null,
    negativeButton: (@Composable() () -> Unit)? = null,
    neutralButton: (@Composable() () -> Unit)? = null
) {
    val showKeyboard = showKeyboard(TextInputDialogInputId)
    val hideKeyboard = hideKeyboard()

    MaterialDialog(
        buttonLayout = buttonLayout,
        icon = icon,
        title = title,
        content = {
            if (value.isEmpty() && hint != null) {
                Opacity(0.5f) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography().subtitle1
                    )
                }
            }
            TextField(
                value = value,
                onValueChange = onValueChange,
                focusIdentifier = TextInputDialogInputId,
                keyboardType = keyboardType,
                textStyle = MaterialTheme.typography().subtitle1
            )

            onActive {
                showKeyboard()
                onDispose {
                    hideKeyboard()
                }
            }
        },
        positiveButton = positiveButton,
        negativeButton = negativeButton,
        neutralButton = neutralButton
    )
}

private const val TextInputDialogInputId = "TextInputDialogInputFieldId"