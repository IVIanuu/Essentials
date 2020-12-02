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

package com.ivianuu.essentials.sample.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.onActive
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focusRequester
import androidx.compose.ui.text.input.TextFieldValue
import com.ivianuu.essentials.ui.common.InsettingLazyColumnFor
import com.ivianuu.essentials.ui.layout.center
import com.ivianuu.essentials.ui.material.ListItem
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.KeyUiBinding
import com.ivianuu.injekt.FunBinding

@HomeItemBinding("Text input")
class TextInputKey

@KeyUiBinding<TextInputKey>
@FunBinding
@Composable
fun TextInputScreen() {
    val state = remember { TextInputState() }

    if (!state.searchVisible) {
        state.inputValue = TextFieldValue()
    }

    val items = AllItems.filter {
        state.inputValue.text.isEmpty() || state.inputValue.text in it.toLowerCase().trim()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (state.searchVisible) {
                        val focusRequester = remember { FocusRequester() }

                        Box(
                            modifier = Modifier.fillMaxSize()
                                .clickable { focusRequester.requestFocus() }
                        ) {
                            if (state.inputValue.text.isEmpty()) {
                                Text(
                                    text = "Search..",
                                    style = MaterialTheme.typography.subtitle1,
                                    modifier = Modifier.alpha(0.5f)
                                        .align(Alignment.CenterStart)
                                )
                            }
                            BasicTextField(
                                value = state.inputValue,
                                onValueChange = { state.inputValue = it },
                                textStyle = MaterialTheme.typography.subtitle1,
                                modifier = Modifier.align(Alignment.CenterStart)
                                    .focusRequester(focusRequester)
                            )
                        }

                        onActive { focusRequester.requestFocus() }
                    } else {
                        Text("Text input")
                    }
                }
            )
        },
        floatingActionButton = {
            if (!state.searchVisible) {
                ExtendedFloatingActionButton(
                    text = { Text("Search") },
                    onClick = { state.searchVisible = true }
                )
            }
        }
    ) {
        if (items.isNotEmpty()) {
            /*val animationClock = AnimationClockAmbient.current
            val flingConfig = FlingConfig()
            val scrollerPosition = retain(items) { ScrollerPosition() }

            val lastScrollPosition = holderFor(scrollerPosition) { scrollerPosition.value }

            if (lastScrollPosition.value < scrollerPosition.value) {
                //keyboardManager.hideKeyboard()
                if (state.searchVisible && state.inputValue.text.isEmpty()) {
                    state.searchVisible = false
                }
            }*/

            InsettingLazyColumnFor(items = items) { item ->
                ListItem(
                    title = { Text(item) }
                )
            }
        } else {
            Text(
                text = "No results",
                modifier = Modifier.center()
            )
        }
    }
}

private class TextInputState {
    var searchVisible by mutableStateOf(false)
    var inputValue by mutableStateOf(TextFieldValue())
}

private val AllItems = (0..100).map { "Item: $it" }