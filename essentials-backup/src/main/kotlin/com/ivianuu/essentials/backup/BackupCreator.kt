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

import com.ivianuu.essentials.coroutines.IODispatcher
import com.ivianuu.essentials.coroutines.awaitAsync
import com.ivianuu.essentials.data.DataDir
import com.ivianuu.essentials.result.Result
import com.ivianuu.essentials.result.runKatching
import com.ivianuu.essentials.store.Collector
import com.ivianuu.essentials.ui.navigation.NavigationAction
import com.ivianuu.essentials.ui.navigation.NavigationAction.Push
import com.ivianuu.essentials.util.BuildInfo
import com.ivianuu.essentials.util.Logger
import com.ivianuu.essentials.util.ScopeCoroutineScope
import com.ivianuu.essentials.util.d
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.component.AppComponent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

typealias BackupCreator = suspend () -> Result<Unit, Throwable>

@Given
fun backupCreator(
    @Given backupDir: BackupDir,
    @Given backupFiles: Set<BackupFile>,
    @Given buildInfo: BuildInfo,
    @Given dataDir: DataDir,
    @Given ioDispatcher: IODispatcher,
    @Given logger: Logger,
    @Given navigator: Collector<NavigationAction>,
    @Given scope: ScopeCoroutineScope<AppComponent>
): BackupCreator = {
    runKatching {
        scope.awaitAsync(ioDispatcher) {
            val dateFormat = SimpleDateFormat("dd_MM_yyyy_HH_mm_ss")
            val backupFileName =
                "${buildInfo.packageName.replace(".", "_")}_${dateFormat.format(Date())}"

            val backupFile = backupDir.resolve("$backupFileName.zip")
                .also {
                    it.parentFile.mkdirs()
                    it.createNewFile()
                }

            val zipOutputStream = ZipOutputStream(backupFile.outputStream())

            backupFiles
                .flatMap { it.walkTopDown() }
                .filterNot { it.isDirectory }
                .filterNot { it.absolutePath in BACKUP_BLACKLIST }
                .filter { it.exists() }
                .forEach { file ->
                    logger.d { "backup file $file" }
                    val entry = ZipEntry(file.relativeTo(dataDir).toString())
                    zipOutputStream.putNextEntry(entry)
                    file.inputStream().copyTo(zipOutputStream)
                    zipOutputStream.closeEntry()
                }

            zipOutputStream.close()

            navigator(
                Push(
                    ShareBackupFileKey(backupFile.absolutePath)
                )
            )
        }
    }
}

internal val BACKUP_BLACKLIST = listOf(
    "com.google.android.datatransport.events",
    "com.google.android.datatransport.events-journal"
)
