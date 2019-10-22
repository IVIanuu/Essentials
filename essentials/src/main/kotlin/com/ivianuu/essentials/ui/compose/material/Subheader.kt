package com.ivianuu.essentials.ui.compose.material

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Alignment
import androidx.ui.core.CurrentTextStyleProvider
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.layout.Container
import androidx.ui.layout.Padding
import androidx.ui.material.themeColor
import androidx.ui.material.themeTextStyle
import com.ivianuu.essentials.ui.compose.core.composable

@Composable
fun Subheader(text: String) = composable("Subheader", inputs = arrayOf(text)) {
    Subheader { Text(text) }
}

@Composable
fun Subheader(text: @Composable() () -> Unit) = composable("Subheader") {
    Container(
        height = 48.dp,
        expanded = true,
        alignment = Alignment.CenterLeft
    ) {
        Padding(left = 16.dp, right = 16.dp) {
            val textStyle = (+themeTextStyle { body2 }).copy(
                color = +themeColor { secondary }
            )
            CurrentTextStyleProvider(value = textStyle) {
                text()
            }
        }
    }
}