package com.ivianuu.essentials.ui.popup

import androidx.compose.Composable
import androidx.ui.core.LayoutCoordinates
import androidx.ui.core.OnPositioned
import androidx.ui.core.boundsInRoot
import androidx.ui.layout.Wrap
import com.ivianuu.essentials.material.icons.Icons
import com.ivianuu.essentials.material.icons.filled.MoreVert
import com.ivianuu.essentials.ui.common.holder
import com.ivianuu.essentials.ui.core.round
import com.ivianuu.essentials.ui.material.IconButton
import com.ivianuu.essentials.ui.navigation.NavigatorAmbient
import com.ivianuu.essentials.ui.painter.Renderable
import com.ivianuu.essentials.ui.painter.VectorRenderable

@Composable
fun PopupMenuButton(
    image: Renderable = VectorRenderable(Icons.Default.MoreVert),
    popupStyle: PopupStyle = PopupStyleAmbient.current,
    onCancel: (() -> Unit)? = null,
    items: List<PopupMenu.Item>
) {
    val navigator = NavigatorAmbient.current

    Wrap {
        val coordinatesHolder =
            holder<LayoutCoordinates?> { null }
        OnPositioned { coordinatesHolder.value = it }

        IconButton(
            onClick = {
                navigator.push(
                    PopupRoute(
                        position = coordinatesHolder.value!!.boundsInRoot.round(),
                        onCancel = onCancel
                    ) {
                        PopupMenu(
                            items = items,
                            style = popupStyle
                        )
                    }
                )
            },
            image = image
        )
    }
}

@Composable
fun <T> PopupMenuButton(
    image: Renderable = VectorRenderable(Icons.Default.MoreVert),
    popupStyle: PopupStyle = PopupStyleAmbient.current,
    onCancel: (() -> Unit)? = null,
    items: List<T>,
    onSelected: (T) -> Unit,
    selectedItem: T? = null,
    item: @Composable (T, Boolean) -> Unit
) {
    val navigator = NavigatorAmbient.current

    Wrap {
        val coordinatesHolder =
            holder<LayoutCoordinates?> { null }
        OnPositioned { coordinatesHolder.value = it }

        IconButton(
            onClick = {
                navigator.push(
                    PopupRoute(
                        position = coordinatesHolder.value!!.boundsInRoot.round(),
                        onCancel = onCancel
                    ) {
                        PopupMenu(
                            items = items,
                            selectedItem = selectedItem,
                            onSelected = onSelected,
                            item = item,
                            style = popupStyle
                        )
                    }
                )
            },
            image = image
        )
    }
}