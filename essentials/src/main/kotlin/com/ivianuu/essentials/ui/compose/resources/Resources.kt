package com.ivianuu.essentials.ui.compose.resources

import androidx.compose.ambient
import androidx.compose.effectOf
import androidx.core.graphics.drawable.toBitmap
import androidx.ui.core.ContextAmbient
import androidx.ui.graphics.Color
import androidx.ui.graphics.Image
import androidx.ui.graphics.toArgb
import com.ivianuu.essentials.ui.compose.image.BitmapImage
import com.ivianuu.essentials.util.drawable

fun colorResource(resId: Int) = effectOf<Color> {
    val context = +ambient(ContextAmbient)
    Color(context.getColor(resId))
}

fun drawableResource(resId: Int) = effectOf<Image> {
    val context = +ambient(ContextAmbient)
    val drawable = context.drawable(resId)
    return@effectOf BitmapImage(drawable.toBitmap())
}

fun drawableResource(resId: Int, tint: Color? = null) = effectOf<Image> {
    val context = +ambient(ContextAmbient)
    val drawable = context.drawable(resId).mutate()
    if (tint != null) drawable.setTint(tint.toArgb())
    return@effectOf BitmapImage(drawable.toBitmap())
}