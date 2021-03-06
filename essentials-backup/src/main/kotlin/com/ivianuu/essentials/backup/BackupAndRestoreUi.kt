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

import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.ui.res.*
import com.github.michaelbull.result.*
import com.ivianuu.essentials.*
import com.ivianuu.essentials.optics.*
import com.ivianuu.essentials.store.*
import com.ivianuu.essentials.ui.core.*
import com.ivianuu.essentials.ui.material.*
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.*
import com.ivianuu.essentials.util.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.coroutines.*
import com.ivianuu.injekt.scope.*
import kotlinx.coroutines.flow.*

object BackupAndRestoreKey : Key<Nothing>

@Provide val backupAndRestoreUi: ModelKeyUi<BackupAndRestoreKey, BackupAndRestoreModel> = {
  Scaffold(
    topBar = { TopAppBar(title = { Text(stringResource(R.string.es_backup_title)) }) }
  ) {
    LazyColumn(contentPadding = localVerticalInsetsPadding()) {
      item {
        ListItem(
          leading = { Icon(painterResource(R.drawable.es_ic_save), null) },
          title = { Text(stringResource(R.string.es_pref_backup)) },
          subtitle = { Text(stringResource(R.string.es_pref_backup_summary)) },
          onClick = model.backupData
        )
      }
      item {
        ListItem(
          leading = { Icon(painterResource(R.drawable.es_ic_restore), null) },
          title = { Text(stringResource(R.string.es_pref_restore)) },
          subtitle = { Text(stringResource(R.string.es_pref_restore_summary)) },
          onClick = model.restoreData
        )
      }
    }
  }
}

@Optics data class BackupAndRestoreModel(
  val backupData: () -> Unit = {},
  val restoreData: () -> Unit = {}
)

@Provide fun backupAndRestoreModel(
  createBackupUseCase: CreateBackupUseCase,
  restoreBackupUseCase: RestoreBackupUseCase,
  scope: InjektCoroutineScope<KeyUiScope>,
  rp: ResourceProvider,
  toaster: Toaster,
): @Scoped<KeyUiScope> StateFlow<BackupAndRestoreModel> =
  scope.state(BackupAndRestoreModel()) {
    action(BackupAndRestoreModel.backupData()) {
      createBackupUseCase()
        .onFailure {
          it.printStackTrace()
          showToast(R.string.es_backup_error)
        }
    }
    action(BackupAndRestoreModel.restoreData()) {
      restoreBackupUseCase()
        .onFailure {
          it.printStackTrace()
          showToast(R.string.es_restore_error)
        }
    }
  }
