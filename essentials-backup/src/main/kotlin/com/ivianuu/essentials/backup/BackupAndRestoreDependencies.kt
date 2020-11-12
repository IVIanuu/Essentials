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

package com.ivianuu.essentials.backup

import com.ivianuu.essentials.data.DataDir
import com.ivianuu.essentials.data.PrefsDir
import com.ivianuu.injekt.Binding
import com.ivianuu.injekt.Effect
import com.ivianuu.injekt.SetElements
import java.io.File

@Effect
annotation class BackupFile {
    companion object {
        @SetElements
        fun <T : File> intoSet(instance: T): BackupFiles = setOf(instance)
    }
}

typealias BackupDir = File

@Binding
fun backupDir(dataDir: DataDir): BackupDir = dataDir.resolve("files/backups")

typealias BackupFiles = Set<File>

@BackupFile
fun backupPrefs(prefsDir: PrefsDir) = prefsDir

@BackupFile
fun backupDatabases(dataDir: DataDir) = dataDir.resolve("databases")
