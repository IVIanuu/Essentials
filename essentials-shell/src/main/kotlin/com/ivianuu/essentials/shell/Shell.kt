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

package com.ivianuu.essentials.shell

import com.ivianuu.essentials.util.IODispatcher
import com.ivianuu.injekt.Assisted
import com.ivianuu.injekt.FunBinding
import eu.chainfire.libsuperuser.Shell.SU
import kotlinx.coroutines.withContext

@FunBinding
suspend fun runShellCommand(
    runShellCommands: runShellCommands,
    command: @Assisted String,
): List<String> = runShellCommands(listOf(command))

@FunBinding
suspend fun runShellCommands(
    ioDispatcher: IODispatcher,
    commands: @Assisted List<String>,
): List<String> = withContext(ioDispatcher) { SU.run(commands) }

@FunBinding
suspend fun isShellAvailable(
    ioDispatcher: IODispatcher,
): Boolean = withContext(ioDispatcher) { SU.available() }
