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

package com.ivianuu.essentials.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.ivianuu.director.requireActivity
import com.ivianuu.essentials.ui.navigation.director.ControllerRoute
import com.ivianuu.essentials.ui.navigation.director.controllerRoute
import com.ivianuu.essentials.ui.navigation.director.dialog
import com.ivianuu.essentials.util.Properties

fun dialogRoute(
    extras: Properties = Properties(),
    options: ControllerRoute.Options? = ControllerRoute.Options().dialog(),
    buildDialog: MaterialDialog.(DialogContext) -> Unit
): ControllerRoute {
    return controllerRoute(
        extras = extras,
        options = options,
        factory = { MdDialogController(buildDialog) }
    )
}

private class MdDialogController(
    private val buildDialog: MaterialDialog.(DialogContext) -> Unit
) : EsDialogController() {
    private val context = DialogContext(this)
    override fun onCreateDialog(inflater: LayoutInflater, container: ViewGroup) =
        MaterialDialog(requireActivity())
            .apply { buildDialog(this, this@MdDialogController.context) }
}