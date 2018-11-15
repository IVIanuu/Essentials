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
import com.ivianuu.androidktx.fragment.app.string
import com.ivianuu.essentials.R
import com.ivianuu.essentials.ui.base.BaseDialogFragment
import com.ivianuu.essentials.ui.traveler.key.BaseFragmentKey
import com.ivianuu.essentials.ui.traveler.key.ResultKey
import com.ivianuu.essentials.ui.traveler.key.key
import com.ivianuu.essentials.util.RequestCodeGenerator
import com.ivianuu.traveler.goBack
import com.ivianuu.traveler.result.goBackWithResult
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.launch
import javax.inject.Inject

@Parcelize
data class AppPickerKey(
    val title: CharSequence? = null,
    val launchableOnly: Boolean = false,
    override val resultCode: Int = RequestCodeGenerator.generate()
) : BaseFragmentKey(AppPickerDialog::class), ResultKey<AppInfo>

/**
 * App picker
 */
class AppPickerDialog : BaseDialogFragment() {

    @Inject lateinit var appStore: AppStore

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val apps = mutableListOf<com.ivianuu.essentials.app.AppInfo>()

        val key = key<AppPickerKey>()

        val dialog = MaterialDialog.Builder(requireContext())
            .title(key.title ?: string(R.string.dialog_title_app_picker))
            .negativeText(R.string.action_cancel)
            .autoDismiss(false)
            .onNegative { _, _ -> router.goBack() }
            .items()
            .itemsCallback { _, _, position, _ ->
                val app = apps[position]
                router.goBackWithResult(key.resultCode, app)
            }
            .build()

        coroutineScope.launch {
            val newApps = if (key.launchableOnly) {
                appStore.launchableApps()
            } else {
                appStore.installedApps()
            }
            apps.clear()
            apps.addAll(newApps)
            dialog.setItems(*apps.map { it.appName }.toTypedArray())
        }

        return dialog
    }
}