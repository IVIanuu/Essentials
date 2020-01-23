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

package com.ivianuu.essentials.ui.prefs

import androidx.compose.Composable
import androidx.compose.Pivotal
import androidx.ui.graphics.Color
import androidx.ui.graphics.Image
import androidx.ui.graphics.SolidColor
import androidx.ui.layout.LayoutSize
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp
import com.ivianuu.essentials.store.Box
import com.ivianuu.essentials.ui.common.asIconComposable
import com.ivianuu.essentials.ui.common.asTextComposable
import com.ivianuu.essentials.ui.dialog.ColorPickerDialog
import com.ivianuu.essentials.ui.dialog.ColorPickerPalette
import com.ivianuu.essentials.ui.material.Surface

@Composable
fun ColorPreference(
    @Pivotal box: Box<Color>,
    enabled: Boolean = true,
    dependencies: List<Dependency<*>>? = null,
    title: String? = null,
    summary: String? = null,
    image: Image? = null,
    dialogTitle: String? = title,
    colorPalettes: List<ColorPickerPalette> = ColorPickerPalette.values().toList(),
    showAlphaSelector: Boolean = true,
    allowCustomArgb: Boolean = true
) {
    ColorPreference(
        valueController = ValueController(box),
        enabled = enabled,
        dependencies = dependencies,
        title = title.asTextComposable(),
        summary = summary.asTextComposable(),
        leading = image.asIconComposable(),
        dialogTitle = dialogTitle.asTextComposable(),
        colorPalettes = colorPalettes,
        showAlphaSelector = showAlphaSelector,
        allowCustomArgb = allowCustomArgb
    )
}

@Composable
fun ColorPreference(
    valueController: ValueController<Color>,
    enabled: Boolean = true,
    dependencies: List<Dependency<*>>? = null,
    title: @Composable (() -> Unit)? = null,
    summary: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    dialogTitle: @Composable (() -> Unit)? = title,
    colorPalettes: List<ColorPickerPalette> = ColorPickerPalette.values().toList(),
    showAlphaSelector: Boolean = true,
    allowCustomArgb: Boolean = true
) {
    DialogPreference(
        valueController = valueController,
        enabled = enabled,
        dependencies = dependencies,
        title = title?.let { { title() } },
        summary = summary?.let { { summary() } },
        leading = leading?.let { { leading() } },
        trailing = { context ->
            Surface(
                modifier = LayoutSize(width = 40.dp, height = 40.dp),
                color = context.currentValue,
                borderWidth = 1.dp,
                borderBrush = SolidColor(MaterialTheme.colors().onSurface)
            ) {}
        },
        dialog = { context, _ ->
            ColorPickerDialog(
                initialColor = context.currentValue,
                onColorSelected = { context.setIfOk(it) },
                colorPalettes = colorPalettes,
                showAlphaSelector = showAlphaSelector,
                allowCustomArgb = allowCustomArgb,
                title = dialogTitle
            )
        }
    )
}
