package com.ivianuu.essentials.sample.ui

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import com.ivianuu.essentials.ui.core.isLight
import com.ivianuu.essentials.ui.core.systemBarStyle
import com.ivianuu.essentials.ui.dialog.ColorPickerPalette
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.injekt.FunBinding

@FunBinding
@Composable
fun DynamicSystemBarsPage() {
    Box {
        ScrollableColumn {
            val colors: List<Color> = rememberSavedInstanceState {
                ColorPickerPalette.values()
                    .filter { it != ColorPickerPalette.Black && it != ColorPickerPalette.White }
                    .flatMap { it.colors }
                    .shuffled()
            }

            colors.forEach { color ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(300.dp)
                        .background(color)
                        .layoutId(color)
                        .systemBarStyle(
                            bgColor = Color.Black.copy(alpha = 0.2f),
                            lightIcons = color.isLight
                        )
                )
            }
        }

        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            title = { Text("Dynamic system bars") }
        )
    }
}
