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

package com.ivianuu.essentials.gestures.action.actions

import android.app.PendingIntent
import android.content.Intent
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.runCatching
import com.google.accompanist.coil.CoilImage
import com.ivianuu.essentials.gestures.R
import com.ivianuu.essentials.gestures.action.ActionIcon
import com.ivianuu.essentials.shell.RunShellCommandUseCase
import com.ivianuu.essentials.util.StringResourceProvider
import com.ivianuu.essentials.util.Toaster
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.android.AppContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal fun coilActionIcon(data: Any): Flow<ActionIcon> = flowOf { CoilImage(data = data, contentDescription = null) }

internal fun singleActionIcon(icon: @Composable () -> Unit): Flow<ActionIcon> = flowOf(icon)

internal fun singleActionIcon(icon: ImageVector) = singleActionIcon { Icon(icon, null) }

internal fun singleActionIcon(id: Int) = singleActionIcon { Icon(painterResource(id), null) }

typealias ActionRootCommandRunner = suspend (String) -> Unit

@Given
fun actionRootCommandRunner(
    @Given runShellCommand: RunShellCommandUseCase,
    @Given stringResource: StringResourceProvider,
    @Given toaster: Toaster
): ActionRootCommandRunner = { command ->
    runCatching { runShellCommand(listOf(command)) }
        .onFailure {
            it.printStackTrace()
            toaster(stringResource(R.string.es_no_root, emptyList()))
        }
}

typealias ActionIntentSender = (Intent) -> Unit

@Given
fun actionIntentSender(
    @Given appContext: AppContext,
    @Given stringResource: StringResourceProvider,
    @Given toaster: Toaster
): ActionIntentSender = { intent ->
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    runCatching {
        PendingIntent.getActivity(
            appContext, 99, intent, 0, null
        ).send()
    }.onFailure {
        it.printStackTrace()
        toaster(stringResource(R.string.es_activity_not_found, emptyList()))
    }
}
