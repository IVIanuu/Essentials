package com.ivianuu.essentials.ui.image

import androidx.compose.Composable
import androidx.compose.Immutable
import androidx.compose.remember
import androidx.compose.staticAmbientOf
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.graphics.BlendMode
import androidx.ui.graphics.Color
import androidx.ui.graphics.ColorFilter
import androidx.ui.graphics.DefaultAlpha
import androidx.ui.graphics.ImageAsset
import androidx.ui.graphics.ScaleFit
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.graphics.painter.Painter
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.graphics.vector.VectorPainter
import androidx.ui.res.imageResource
import com.ivianuu.essentials.ui.core.currentOrNull

@Immutable
data class ImageStyle(
    val modifier: Modifier,
    val alignment: Alignment,
    val scaleFit: ScaleFit,
    val alpha: Float,
    val colorFilter: ColorFilter?
)

val ImageStyleAmbient = staticAmbientOf<ImageStyle>()

@Composable
fun DefaultImageStyle(
    modifier: Modifier = Modifier.None,
    alignment: Alignment = Alignment.Center,
    scaleFit: ScaleFit = ScaleFit.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) = ImageStyle(
    modifier = modifier,
    alignment = alignment,
    scaleFit = scaleFit,
    alpha = alpha,
    colorFilter = colorFilter
)

@Composable
fun DefaultImageStyle(
    tint: Color,
    modifier: Modifier = Modifier.None,
    alignment: Alignment = Alignment.Center,
    scaleFit: ScaleFit = ScaleFit.Fit,
    alpha: Float = DefaultAlpha
) = ImageStyle(
    modifier = modifier,
    alignment = alignment,
    scaleFit = scaleFit,
    alpha = alpha,
    colorFilter = ColorFilter(tint, BlendMode.srcIn)
)

@Composable
fun Image(
    painter: Painter,
    modifier: Modifier = Modifier.None,
    style: ImageStyle = ImageStyleAmbient.currentOrNull ?: DefaultImageStyle()
) {
    androidx.ui.foundation.Image(
        painter = painter,
        modifier = modifier,
        alignment = style.alignment,
        scaleFit = style.scaleFit,
        alpha = style.alpha,
        colorFilter = style.colorFilter
    )
}

@Composable
fun Image(
    id: Int,
    modifier: Modifier = Modifier.None,
    style: ImageStyle = ImageStyleAmbient.currentOrNull ?: DefaultImageStyle()
) {
    Image(
        image = imageResource(id),
        modifier = modifier,
        style = style
    )
}

@Composable
fun Image(
    image: ImageAsset,
    modifier: Modifier = Modifier.None,
    style: ImageStyle = ImageStyleAmbient.currentOrNull ?: DefaultImageStyle()
) {
    Image(
        painter = remember(image) { ImagePainter(image) },
        modifier = modifier,
        style = style
    )
}

@Composable
fun Image(
    image: VectorAsset,
    modifier: Modifier = Modifier.None,
    style: ImageStyle = ImageStyleAmbient.currentOrNull ?: DefaultImageStyle()
) {
    Image(
        painter = VectorPainter(image),
        modifier = modifier,
        style = style
    )
}