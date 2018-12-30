/*
 * Copyright 2018 Manuel Wrage
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

import com.ivianuu.essentials.util.ext.coroutinesIo
import com.ivianuu.injekt.codegen.AutoModule
import com.ivianuu.injekt.codegen.Factory
import kotlinx.coroutines.withContext

@AutoModule(moduleName = "esShellModule")
private object AutoModule

/**
 * Shell
 */
@Factory
class Shell {

    suspend fun run(command: String) = run(listOf(command))

    suspend fun run(commands: Collection<String>) =
        run(commands.toTypedArray())

    suspend fun run(commands: Array<String>) = withContext(coroutinesIo) {
        eu.chainfire.libsuperuser.Shell.SU.run(commands).toList()
    }

    suspend fun available() = withContext(coroutinesIo) {
        eu.chainfire.libsuperuser.Shell.SU.available()
    }

}