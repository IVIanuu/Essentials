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
import androidx.compose.stateFor
import androidx.ui.core.Alignment
import androidx.ui.core.CurrentTextStyleProvider
import androidx.ui.core.IntPx
import androidx.ui.core.Layout
import androidx.ui.core.Size
import androidx.ui.core.Text
import androidx.ui.core.TextField
import androidx.ui.core.dp
import androidx.ui.core.ipx
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.contentColor
import androidx.ui.foundation.shape.border.Border
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.Center
import androidx.ui.layout.Container
import androidx.ui.layout.DpConstraints
import androidx.ui.layout.EdgeInsets
import androidx.ui.layout.LayoutExpandedWidth
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.Table
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TextButtonStyle
import androidx.ui.material.ripple.Ripple
import androidx.ui.res.stringResource
import com.ivianuu.essentials.R
import com.ivianuu.essentials.ui.common.hideKeyboardOnDispose
import com.ivianuu.essentials.ui.layout.Column
import com.ivianuu.essentials.ui.layout.CrossAxisAlignment
import com.ivianuu.essentials.ui.layout.Expand
import com.ivianuu.essentials.ui.layout.MainAxisAlignment
import com.ivianuu.essentials.ui.layout.OverflowBox
import com.ivianuu.essentials.ui.layout.Row
import com.ivianuu.essentials.ui.layout.ScrollableList
import com.ivianuu.essentials.ui.layout.SquaredBox
import com.ivianuu.essentials.ui.layout.SquaredBoxFit
import com.ivianuu.essentials.ui.layout.WidthSpacer
import com.ivianuu.essentials.ui.material.EsSurface
import com.ivianuu.essentials.ui.material.Icon
import com.ivianuu.essentials.ui.material.Slider
import com.ivianuu.essentials.ui.material.SliderPosition
import com.ivianuu.essentials.ui.material.Tab
import com.ivianuu.essentials.ui.material.TabContent
import com.ivianuu.essentials.ui.material.TabController
import com.ivianuu.essentials.ui.material.TabRow
import com.ivianuu.essentials.ui.material.contentColorFor
import com.ivianuu.essentials.ui.material.copy
import com.ivianuu.essentials.ui.material.currentIconStyle
import com.ivianuu.essentials.ui.navigation.navigator
import com.ivianuu.essentials.ui.resources.drawableResource
import com.ivianuu.essentials.util.toColor
import com.ivianuu.essentials.util.toHexString

fun ColorPickerRoute(
    initialColor: Color,
    colorPalettes: List<ColorPickerPalette> = ColorPickerPalette.values().toList(),
    allowCustomArgb: Boolean = true,
    showAlphaSelector: Boolean = false,
    title: String? = null
) = DialogRoute {
    val navigator = navigator
    ColorPickerDialog(
        initialColor = initialColor,
        colorPalettes = colorPalettes,
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
    initialColor: Color,
    colorPalettes: List<ColorPickerPalette> = ColorPickerPalette.values().toList(),
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
                colorPalettes = colorPalettes,
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
    colorPalettes: List<ColorPickerPalette>,
    allowCustomArgb: Boolean,
    showAlphaSelector: Boolean,
    color: Color,
    onColorChanged: (Color) -> Unit
) {
    val colorGrid: @Composable() () -> Unit = {
        ColorGrid(
            colorPalettes = colorPalettes,
            onColorSelected = onColorChanged
        )
    }

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


                Container(modifier = LayoutPadding(left = 24.dp, right = 24.dp)) {
                    TabContent<ColorPickerPage>(keepState = true) { _, page ->
                        Container(height = 300.dp) {
                            when (page) {
                                ColorPickerPage.Colors -> colorGrid()
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
        Container(
            modifier = LayoutPadding(left = 24.dp, right = 24.dp),
            children = colorGrid
        )
    }
}

@Composable
private fun ColorGrid(
    colorPalettes: List<ColorPickerPalette>,
    onColorSelected: (Color) -> Unit
) {
    val (currentPalette, setCurrentPalette) = state<ColorPickerPalette?> { null }
    val items = remember(currentPalette) {
        currentPalette?.colors
            ?.map { ColorGridItem.Color(it) }
            ?.let { listOf(ColorGridItem.Back) + it }
            ?: colorPalettes.map { ColorGridItem.Color(it.front) }
    }

    key(currentPalette) {
        ScrollableList(modifier = LayoutPadding(all = 4.dp)) {
            Table(
                columns = 4,
                alignment = { Alignment.Center }
            ) {
                val chunkedItems = items.chunked(4)
                chunkedItems.forEach { rowItems ->
                    tableRow {
                        rowItems.forEach { item ->
                            key(item) {
                                when (item) {
                                    is ColorGridItem.Back -> ColorGridBackButton(
                                        onClick = { setCurrentPalette(null) }
                                    )
                                    is ColorGridItem.Color -> ColorGridItem(
                                        color = item.color,
                                        onClick = {
                                            if (currentPalette == null) {
                                                setCurrentPalette(colorPalettes.first { it.front == item.color })
                                            } else {
                                                onColorSelected(item.color)
                                            }
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
}

private sealed class ColorGridItem {
    object Back : ColorGridItem()
    data class Color(val color: androidx.ui.graphics.Color) : ColorGridItem()
}

@Composable
private fun ColorGridItem(
    color: Color,
    onClick: () -> Unit
) {
    BaseColorGridItem(onClick = onClick) {
        Expand {
            EsSurface(
                color = color,
                contentColor = contentColorFor(color),
                shape = RoundedCornerShape(50),
                border = Border(
                    color = MaterialTheme.colors().onSurface,
                    width = 1.dp
                ),
                elevation = 0.dp
            ) {
            }
        }
    }
}

@Composable
private fun ColorGridBackButton(onClick: () -> Unit) {
    BaseColorGridItem(onClick = onClick) {
        Icon(
            image = drawableResource(R.drawable.es_ic_arrow_back),
            style = currentIconStyle().copy(size = Size(width = 36.dp, height = 36.dp))
        )
    }
}

@Composable
private fun BaseColorGridItem(
    onClick: () -> Unit,
    child: @Composable() () -> Unit
) {
    Ripple(bounded = true) {
        Clickable(onClick = onClick) {
            SquaredBox(
                fit = SquaredBoxFit.MatchWidth,
                modifier = LayoutPadding(all = 4.dp)
            ) {
                Center(children = child)
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
        ColorEditorHeader(
            color = color,
            showAlphaSelector = showAlphaSelector,
            onColorChanged = onColorChanged
        )

        ColorComponent.values()
            .filter { it != ColorComponent.Alpha || showAlphaSelector }
            .forEach { component ->
                key(component) {
                    ColorComponentItem(
                        component = component,
                        value = component.extract(color),
                        onChanged = { onColorChanged(component.apply(color, it)) }
                    )
                }
            }
    }
}

@Composable
private fun ColorEditorHeader(
    color: Color,
    showAlphaSelector: Boolean,
    onColorChanged: (Color) -> Unit
) {
    CurrentTextStyleProvider(value = MaterialTheme.typography().subtitle1) {
        EsSurface(color = color) {
            Container(
                height = 72.dp,
                padding = EdgeInsets(all = 8.dp),
                modifier = LayoutExpandedWidth
            ) {
                Center {
                    Row(
                        mainAxisAlignment = MainAxisAlignment.Center,
                        crossAxisAlignment = CrossAxisAlignment.Center
                    ) {
                        val (hexInput, setHexInput) = stateFor(color) {
                            color.toHexString(includeAlpha = showAlphaSelector)
                        }
                        hideKeyboardOnDispose()
                        Text("#")
                        OverflowBox {
                            TextField(
                                value = hexInput,
                                onValueChange = { newValue ->
                                    if ((showAlphaSelector && newValue.length > 8) ||
                                        (!showAlphaSelector && newValue.length > 6)
                                    ) return@TextField

                                    setHexInput(newValue)

                                    if ((showAlphaSelector && newValue.length < 8) ||
                                        (!showAlphaSelector && newValue.length < 6)
                                    ) return@TextField

                                    val newColor = try {
                                        newValue.toColor()
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                        null
                                    }

                                    if (newColor != null) onColorChanged(newColor)
                                }
                            )
                        }
                    }
                }
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

            WidthSpacer(8.dp)

            Container(
                constraints = DpConstraints(
                    minWidth = 56.dp
                )
            ) {
                Text(
                    text = (255 * value).toInt().toString(),
                    modifier = Inflexible,
                    style = MaterialTheme.typography().subtitle1
                )
            }
        }
    }
}

private enum class ColorComponent(
    val title: String,
    val color: @Composable() () -> Color
) {
    Alpha(
        title = "A",
        color = { contentColor() }
    ) {
        override fun extract(color: Color) = color.alpha
        override fun apply(color: Color, value: Float) = color.copy(alpha = value)
    },
    Red(
        title = "R",
        color = { Color.Red }
    ) {
        override fun extract(color: Color) = color.red
        override fun apply(color: Color, value: Float) = color.copy(red = value)
    },
    Green(
        title = "G",
        color = { Color.Green }
    ) {
        override fun extract(color: Color) = color.green
        override fun apply(color: Color, value: Float) = color.copy(green = value)
    },
    Blue(
        title = "B",
        color = { Color.Blue }
    ) {
        override fun extract(color: Color) = color.blue
        override fun apply(color: Color, value: Float) = color.copy(blue = value)
    };

    abstract fun extract(color: Color): Float
    abstract fun apply(color: Color, value: Float): Color
}

// todo title convert to resource
private enum class ColorPickerPage(
    val title: String
) {
    Colors("Colors"), Editor("Color Editor")
}

@Composable
private fun TightColumn(children: @Composable() () -> Unit) {
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
            placeables.forEach { placeable ->
                placeable.place(IntPx.Zero, offsetY)
                offsetY += placeable.height
            }
        }
    }
}
