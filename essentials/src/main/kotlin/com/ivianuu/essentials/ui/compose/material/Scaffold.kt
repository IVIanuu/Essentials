package com.ivianuu.essentials.ui.compose.material

import androidx.compose.Ambient
import androidx.compose.Composable
import androidx.compose.State
import androidx.compose.memo
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.Alignment
import androidx.ui.core.LayoutCoordinates
import androidx.ui.core.OnPositioned
import androidx.ui.core.dp
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.Padding
import androidx.ui.layout.Stack
import androidx.ui.material.surface.Surface
import com.ivianuu.essentials.ui.compose.core.composable

@Composable
fun Scaffold(
    appBar: @Composable() () -> Unit,
    content: @Composable() () -> Unit,
    fabConfiguration: Scaffold.FabConfiguration? = null
) = composable("Scaffold") {
    val overlays = +state { emptyList<Overlay>() }
    val scaffold = +memo { Scaffold(overlays) }

    ScaffoldAmbient.Provider(value = scaffold) {
        Stack {
            OnPositioned { scaffold.coordinates = it }

            Column {
                Container(modifier = Inflexible) {
                    Surface(elevation = 4.dp) {
                        appBar()
                    }
                }

                Container(
                    alignment = Alignment.TopLeft,
                    modifier = Flexible(1f)
                ) {
                    Surface {
                        content()
                    }
                }
            }

            if (fabConfiguration != null) {
                aligned(
                    when (fabConfiguration.position) {
                        Scaffold.FabPosition.Center -> Alignment.BottomCenter
                        Scaffold.FabPosition.End -> Alignment.BottomRight
                    }
                ) {
                    Padding(padding = 16.dp) {
                        fabConfiguration.fab()
                    }
                }
            }

            overlays.value.forEach { overlay ->
                overlay.composable {
                    scaffold.removeOverlay(overlay)
                }
            }
        }
    }
}

val ScaffoldAmbient = Ambient.of<Scaffold>()

internal data class Overlay(val composable: @Composable() (() -> Unit) -> Unit)

class Scaffold internal constructor(
    private val overlays: State<List<Overlay>>
) {

    var coordinates: LayoutCoordinates? = null

    fun addOverlay(block: (() -> Unit) -> Unit) {
        val newOverlays = overlays.value.toMutableList()
        newOverlays += Overlay(block)
        overlays.value = newOverlays
    }

    internal fun removeOverlay(overlay: Overlay) {
        val newOverlays = overlays.value.toMutableList()
        newOverlays -= overlay
        overlays.value = newOverlays
    }

    data class FabConfiguration(
        val position: FabPosition,
        val fab: @Composable() () -> Unit
    )

    enum class FabPosition {
        Center, End
    }
}