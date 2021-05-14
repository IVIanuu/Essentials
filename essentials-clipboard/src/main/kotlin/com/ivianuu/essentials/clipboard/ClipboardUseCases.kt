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

package com.ivianuu.essentials.clipboard

import android.content.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.android.*
import com.ivianuu.injekt.coroutines.*
import com.ivianuu.injekt.scope.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*

typealias ClipboardText = String?

@Given fun clipboardText(
  @Given clipboardManager: @SystemService ClipboardManager,
  @Given scope: GivenCoroutineScope<AppGivenScope>
) = callbackFlow<ClipboardText> {
  val listener = ClipboardManager.OnPrimaryClipChangedListener {
    val current = clipboardManager.primaryClip?.getItemAt(0)?.text?.toString()
    offer(current)
  }
  awaitClose { clipboardManager.removePrimaryClipChangedListener(listener) }
}.shareIn(scope, SharingStarted.WhileSubscribed(), 1)

typealias UpdateClipboardTextUseCase = suspend (String) -> Unit

@Given fun updateClipboardTextUseCase(
  @Given clipboardManager: @SystemService ClipboardManager
): UpdateClipboardTextUseCase = { value ->
  clipboardManager.setPrimaryClip(ClipData.newPlainText("", value))
}
