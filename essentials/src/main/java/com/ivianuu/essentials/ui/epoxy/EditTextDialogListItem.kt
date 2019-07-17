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

package com.ivianuu.essentials.ui.epoxy

import android.graphics.drawable.Drawable
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.airbnb.epoxy.EpoxyController
import com.ivianuu.essentials.R

fun EpoxyController.EditTextDialogListItem(
    id: Any?,

    onInputCompleted: (String) -> Unit,

    title: String? = null,
    titleRes: Int? = null,

    text: String? = null,
    textRes: Int? = null,

    icon: Drawable? = null,
    iconRes: Int? = null,

    avatar: Drawable? = null,
    avatarRes: Int? = null,

    dialogTitle: String? = title,
    dialogTitleRes: Int? = titleRes,

    prefill: String? = null,

    hint: String? = null,
    hintRes: Int? = null,

    positiveDialogButtonText: String? = null,
    positiveDialogButtonTextRes: Int = R.string.es_ok,
    negativeDialogButtonText: String? = null,
    negativeDialogButtonTextRes: Int = R.string.es_cancel,

    dialogBlock: (MaterialDialog.() -> Unit)? = null,
    builderBlock: (FunModelBuilder.() -> Unit)? = null
) = DialogListItem(
    id = id,
    buildDialog = {
        title(res = dialogTitleRes, text = dialogTitle)
        positiveButton(res = positiveDialogButtonTextRes, text = positiveDialogButtonText)
        negativeButton(res = negativeDialogButtonTextRes, text = negativeDialogButtonText)

        input(
            hintRes = hintRes,
            hint = hint ?: "",
            prefill = prefill
        ) { _, input -> onInputCompleted(input.toString()) }

        dialogBlock?.invoke(this)

        show()
    },
    title = title,
    titleRes = titleRes,
    text = text,
    textRes = textRes,
    icon = icon,
    iconRes = iconRes,
    avatar = avatar,
    avatarRes = avatarRes,
    builderBlock = {
        state(prefill)
        state(dialogTitle, dialogTitleRes)
        state(negativeDialogButtonText, negativeDialogButtonTextRes)
        state(dialogBlock != null)

        builderBlock?.invoke(this)
    }
)