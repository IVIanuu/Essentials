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

import androidx.compose.runtime.Composable
import com.ivianuu.essentials.backup.BackupAndRestoreAction.BackupData
import com.ivianuu.essentials.backup.BackupAndRestoreAction.RestoreData
import com.ivianuu.essentials.store.DispatchAction
import com.ivianuu.essentials.ui.common.InsettingScrollableColumn
import com.ivianuu.essentials.ui.core.Text
import com.ivianuu.essentials.ui.material.ListItem
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.KeyUiBinding
import com.ivianuu.essentials.ui.store.UiState
import com.ivianuu.injekt.FunBinding

@KeyUiBinding<BackupAndRestoreKey>
@FunBinding
@Composable
fun BackupAndRestorePage(
    dispatch: DispatchAction<BackupAndRestoreAction>,
    state: @UiState BackupAndRestoreState
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(R.string.es_backup_title) }) }
    ) {
        InsettingScrollableColumn {
            ListItem(
                title = { Text(R.string.es_pref_backup) },
                subtitle = { Text(R.string.es_pref_backup_summary) },
                onClick = { dispatch(BackupData) }
            )

            ListItem(
                title = { Text(R.string.es_pref_restore) },
                subtitle = { Text(R.string.es_pref_restore_summary) },
                onClick = { dispatch(RestoreData) }
            )
        }
    }
}
