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

import androidx.compose.Composable
import androidx.compose.key
import androidx.compose.remember
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Draw
import androidx.ui.core.IntPx
import androidx.ui.core.Layout
import androidx.ui.core.PxSize
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.ipx
import androidx.ui.engine.geometry.Offset
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.ColoredRect
import androidx.ui.graphics.Canvas
import androidx.ui.graphics.Color
import androidx.ui.graphics.Paint
import androidx.ui.layout.Container
import androidx.ui.layout.LayoutExpandedWidth
import androidx.ui.layout.Padding
import androidx.ui.layout.Table
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Slider
import androidx.ui.material.SliderPosition
import androidx.ui.material.TextButtonStyle
import androidx.ui.material.ripple.Ripple
import androidx.ui.res.stringResource
import com.ivianuu.essentials.R
import com.ivianuu.essentials.ui.layout.Column
import com.ivianuu.essentials.ui.layout.CrossAxisAlignment
import com.ivianuu.essentials.ui.layout.Row
import com.ivianuu.essentials.ui.layout.ScrollableList
import com.ivianuu.essentials.ui.layout.SquaredBox
import com.ivianuu.essentials.ui.layout.SquaredBoxFit
import com.ivianuu.essentials.ui.material.Tab
import com.ivianuu.essentials.ui.material.TabContent
import com.ivianuu.essentials.ui.material.TabController
import com.ivianuu.essentials.ui.material.TabRow
import com.ivianuu.essentials.ui.material.contentColorFor
import com.ivianuu.essentials.ui.material.copy
import com.ivianuu.essentials.ui.navigation.navigator

fun ColorPickerRoute(
    initialColor: Color,
    allowCustomArgb: Boolean = true,
    showAlphaSelector: Boolean = false,
    title: String? = null
) = DialogRoute {
    val navigator = navigator
    ColorPickerDialog(
        initialColor = initialColor,
        onColorSelected = { navigator.pop(result = it) },
        allowCustomArgb = allowCustomArgb,
        showAlphaSelector = showAlphaSelector,
        dismissOnSelection = false,
        title = {
            Text(title ?: stringResource(R.string.es_dialog_title_color_picker))
        }
    )
}

@Composable
fun ColorPickerDialog(
    colors: List<Color> = PrimaryColors,
    initialColor: Color,
    onColorSelected: (Color) -> Unit,
    allowCustomArgb: Boolean = true,
    showAlphaSelector: Boolean = false,
    dismissOnSelection: Boolean = true,
    icon: (@Composable() () -> Unit)? = null,
    title: (@Composable() () -> Unit)? = null
) {
    val (currentColor, setCurrentColor) = state { initialColor }

    MaterialDialog(
        icon = icon,
        title = title,
        applyContentPadding = false,
        positiveButton = {
            ColoredDialogButton(
                text = "OK",
                color = currentColor,
                dismissOnSelection = dismissOnSelection,
                onClick = { onColorSelected(currentColor) }
            )
        },
        negativeButton = { DialogCloseButton(text = "Cancel") },
        content = {
            ColorPickerContent(
                colors = colors,
                allowCustomArgb = allowCustomArgb,
                showAlphaSelector = showAlphaSelector,
                color = currentColor,
                onColorChanged = setCurrentColor
            )
        }
    )
}

@Composable
private fun ColorPickerContent(
    colors: List<Color>,
    allowCustomArgb: Boolean,
    showAlphaSelector: Boolean,
    color: Color,
    onColorChanged: (Color) -> Unit
) {
    if (allowCustomArgb) {
        TabController(items = ColorPickerPage.values().toList()) {
            TightColumn {
                val currentColors = MaterialTheme.colors()

                MaterialTheme(
                    colors = currentColors.copy(
                        primary = currentColors.surface,
                        onPrimary = currentColors.onSurface
                    ),
                    typography = MaterialTheme.typography()
                ) {
                    TabRow<ColorPickerPage> { _, page ->
                        Tab(text = page.title)
                    }
                }

                Padding(
                    left = 24.dp,
                    right = 24.dp
                ) {
                    TabContent<ColorPickerPage>(keepState = true) { _, page ->
                        Container(height = 300.dp) {
                            when (page) {
                                ColorPickerPage.Colors -> {
                                    ColorGrid(
                                        colors = colors,
                                        onColorSelected = onColorChanged
                                    )
                                }
                                ColorPickerPage.Editor -> {
                                    ColorEditor(
                                        color = color,
                                        onColorChanged = onColorChanged,
                                        showAlphaSelector = showAlphaSelector
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    } else {
        Padding(
            left = 24.dp,
            right = 24.dp
        ) {
            ColorGrid(
                colors = colors,
                onColorSelected = onColorChanged
            )
        }
    }
}

@Composable
private fun ColorGrid(
    colors: List<Color>,
    onColorSelected: (Color) -> Unit
) {
    ScrollableList {
        Padding(padding = 4.dp) {
            Table(
                columns = 4,
                alignment = { Alignment.Center }
            ) {
                val chunkedColors = colors.chunked(4)
                chunkedColors.forEach { rowColors ->
                    tableRow {
                        rowColors.forEach { color ->
                            key(color) {
                                ColorGridItem(
                                    color = color,
                                    onClick = {
                                        onColorSelected(color)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorGridItem(
    color: Color,
    onClick: () -> Unit
) {
    Ripple(bounded = true) {
        Clickable(onClick = onClick) {
            Padding(padding = 4.dp) {
                SquaredBox(fit = SquaredBoxFit.MatchWidth) {
                    val paint = remember { Paint() }
                    paint.color = color
                    Draw { canvas: Canvas, parentSize: PxSize ->
                        canvas.drawCircle(
                            Offset(parentSize.width.value / 2, parentSize.height.value / 2),
                            parentSize.width.value / 2,
                            paint
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorEditor(
    color: Color,
    onColorChanged: (Color) -> Unit,
    showAlphaSelector: Boolean
) {
    Column {
        Container(
            height = 72.dp,
            modifier = LayoutExpandedWidth
        ) {
            // todo use surface once fixed
            ColoredRect(color)
            Text(
                text = color.toString(),
                style = MaterialTheme.typography().subtitle1.copy(
                    color = contentColorFor(color)
                )
            )
        }

        ColorComponent.values()
            .filter {
                it != ColorComponent.Alpha || showAlphaSelector
            }
            .forEach { component ->
                key(component) {
                    ColorComponentItem(
                        component = component,
                        value = component.read(color),
                        onChanged = { onColorChanged(component.apply(color, it)) }
                    )
                }
            }
    }
}

@Composable
private fun ColoredDialogButton(
    text: String,
    color: Color,
    dismissOnSelection: Boolean,
    onClick: () -> Unit
) {
    DialogButton(
        text = text,
        dismissDialogOnClick = dismissOnSelection,
        style = TextButtonStyle(contentColor = color),
        onClick = onClick
    )
}

@Composable
private fun ColorComponentItem(
    component: ColorComponent,
    value: Float,
    onChanged: (Float) -> Unit
) {
    Container(
        height = 48.dp,
        modifier = LayoutExpandedWidth
    ) {
        Row(
            crossAxisAlignment = CrossAxisAlignment.Center
        ) {
            Text(
                text = component.title,
                modifier = Inflexible,
                style = MaterialTheme.typography().subtitle1
            )

            val position = remember {
                SliderPosition(initial = value)
            }

            Slider(
                position = position,
                modifier = Flexible(flex = 1f),
                onValueChange = {
                    position.value = it
                    onChanged(it)
                },
                color = component.color()
            )

            Text(
                text = (255 * value).toInt().toString(),
                modifier = Inflexible,
                style = MaterialTheme.typography().subtitle1
            )
        }
    }
}

private enum class ColorComponent(
    val title: String,
    val color: @Composable() () -> Color
) {
    Alpha(
        title = "A",
        color = { androidx.ui.foundation.contentColor() }
    ) {
        override fun read(color: Color) = color.alpha
        override fun apply(color: Color, value: Float) = color.copy(alpha = value)
    },
    Red(
        title = "R",
        color = { Color.Red }
    ) {
        override fun read(color: Color) = color.red
        override fun apply(color: Color, value: Float) = color.copy(red = value)
    },
    Green(
        title = "G",
        color = { Color.Green }
    ) {
        override fun read(color: Color) = color.green
        override fun apply(color: Color, value: Float) = color.copy(green = value)
    },
    Blue(
        title = "B",
        color = { Color.Blue }
    ) {
        override fun read(color: Color) = color.blue
        override fun apply(color: Color, value: Float) = color.copy(blue = value)
    };

    abstract fun read(color: Color): Float
    abstract fun apply(color: Color, value: Float): Color
}

// todo title convert to resource
private enum class ColorPickerPage(
    val title: String
) {
    Colors("Colors"), Editor("Color Editor")
}

@Composable
private fun TightColumn(
    children: @Composable() () -> Unit
) {
    Layout(children = children) { measureables, constraints ->
        var childConstraints = constraints
        val placeables = measureables.map {
            val placeable = it.measure(childConstraints)
            childConstraints =
                childConstraints.copy(maxHeight = childConstraints.maxHeight - placeable.height)
            placeable
        }

        val height = placeables.sumBy { it.height.value }.ipx

        layout(width = constraints.maxWidth, height = height) {
            var offsetY = IntPx.Zero
            placeables.forEach {
                it.place(IntPx.Zero, offsetY)
                offsetY += it.height
            }
        }
    }
}