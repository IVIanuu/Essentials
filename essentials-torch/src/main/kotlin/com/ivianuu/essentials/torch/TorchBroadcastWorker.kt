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

package com.ivianuu.essentials.torch

import com.ivianuu.essentials.app.*
import com.ivianuu.essentials.broadcast.*
import com.ivianuu.essentials.coroutines.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.scope.*
import kotlinx.coroutines.flow.*

@Provide fun torchBroadcastWorker(
  broadcastsFactory: BroadcastsFactory,
  torchState: MutableStateFlow<TorchState>
): ScopeWorker<AppScope> = {
  broadcastsFactory(ACTION_DISABLE_TORCH).collect { torchState.update { false } }
}

const val ACTION_DISABLE_TORCH = "com.ivianuu.essentials.torch.DISABLE_TORCH"
