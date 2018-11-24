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

package com.ivianuu.essentials.app

import android.app.Dialog
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.ivianuu.essentials.ui.base.BaseDialogController
import com.ivianuu.essentials.ui.traveler.NavOptions
import com.ivianuu.essentials.ui.traveler.dialog
import com.ivianuu.essentials.ui.traveler.key.ControllerKey
import com.ivianuu.essentials.ui.traveler.key.ResultKey
import com.ivianuu.essentials.ui.traveler.key.key
import com.ivianuu.essentials.util.RequestCodeGenerator
import com.ivianuu.essentials.util.string
import com.ivianuu.traveler.goBack
import com.ivianuu.traveler.result.goBackWithResult
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.launch
import javax.inject.Inject

@Parcelize
data class AppPickerKey(
    val title: String? = null,
    val launchableOnly: Boolean = false,
    override val resultCode: Int = RequestCodeGenerator.generate()
) : ControllerKey(AppPickerDialog::class, NavOptions().dialog()), ResultKey<AppInfo>

/**
 * App picker
 */
class AppPickerDialog : BaseDialogController() {

    @Inject lateinit var appStore: AppStore

    override fun onCreateDialog(savedViewState: Bundle?): Dialog {
        val apps = mutableListOf<com.ivianuu.essentials.app.AppInfo>()

        val key = key<AppPickerKey>()

        val dialog = MaterialDialog(activity)
            .title(text = key.title ?: string(R.string.dialog_title_app_picker))
            .positiveButton(R.string.action_ok)
            .negativeButton(R.string.action_cancel) { travelerRouter.goBack() }
            .noAutoDismiss()

        coroutineScope.launch {
            val newApps = if (key.launchableOnly) {
                appStore.launchableApps()
            } else {
                appStore.installedApps()
            }
            apps.clear()
            apps.addAll(newApps)

            dialog.listItems(
                items = apps.map { it.appName },
                waitForPositiveButton = false
            ) { _, index, _ ->
                val app = apps[index]
                travelerRouter.goBackWithResult(key.resultCode, app)
            }
        }

        return dialog
    }
}