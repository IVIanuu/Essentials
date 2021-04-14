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

package com.ivianuu.essentials.shell

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.runCatching
import com.ivianuu.essentials.coroutines.IODispatcher
import com.ivianuu.injekt.Given
import eu.chainfire.libsuperuser.Shell.SU
import kotlinx.coroutines.withContext

typealias IsShellAvailableUseCase = suspend () -> Boolean

@Given
fun isShellAvailableUseCase(@Given dispatcher: IODispatcher): IsShellAvailableUseCase = {
    withContext(dispatcher) {
        runCatching { SU.available() }.getOrElse { false }
    }
}

typealias RunShellCommandUseCase = suspend (List<String>) -> Result<List<String>, Throwable>

@Given
fun runShellCommandUseCase(@Given dispatcher: IODispatcher): RunShellCommandUseCase = { commands ->
    withContext(dispatcher) { runCatching { SU.run(commands)!! } }
}
