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

import android.content.Intent
import androidx.core.content.FileProvider
import com.ivianuu.essentials.ui.navigation.KeyIntentFactoryBinding
import com.ivianuu.essentials.ui.navigation.NavigationAction
import com.ivianuu.essentials.util.BuildInfo
import com.ivianuu.injekt.FunApi
import com.ivianuu.injekt.FunBinding
import com.ivianuu.injekt.android.ApplicationContext
import java.io.File

data class ShareBackupFileKey(val backupFilePath: String)

@KeyIntentFactoryBinding
@FunBinding
fun createShareBackupFileKeyIntent(
    applicationContext: ApplicationContext,
    buildInfo: BuildInfo,
    @FunApi key: ShareBackupFileKey,
): Intent {
    val uri = FileProvider.getUriForFile(
        applicationContext,
        buildInfo.packageName,
        File(key.backupFilePath)
    )
    return Intent.createChooser(
        Intent(Intent.ACTION_SEND).apply {
            type = "application/zip"
            data = uri
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        },
        "Share File"
    )
}