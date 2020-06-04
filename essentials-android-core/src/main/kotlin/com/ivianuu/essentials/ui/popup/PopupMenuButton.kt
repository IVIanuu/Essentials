package com.ivianuu.essentials.ui.popup

import androidx.compose.Composable
import androidx.ui.core.LayoutCoordinates
import androidx.ui.core.Modifier
import androidx.ui.core.boundsInRoot
import androidx.ui.core.onPositioned
import androidx.ui.foundation.Clickable
import androidx.ui.layout.Stack
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.MoreVert
import androidx.ui.unit.IntPxBounds
import androidx.ui.unit.ipx
import com.ivianuu.essentials.ui.common.holder
import com.ivianuu.essentials.ui.core.currentOrElse
import com.ivianuu.essentials.ui.image.Icon
import com.ivianuu.essentials.ui.navigation.NavigatorAmbient

@Composable
fun PopupMenuButton(
    items: List<PopupMenu.Item>,
    onCancel: (() -> Unit)? = null,
    popupStyle: PopupStyle = PopupStyleAmbient.currentOrElse { DefaultPopupStyle() }
) {
    PopupMenuButton(items = items, onCancel = onCancel, popupStyle = popupStyle) {
        Icon(Icons.Default.MoreVert)
    }
}

@Composable
fun PopupMenuButton(
    items: List<PopupMenu.Item>,
    onCancel: (() -> Unit)? = null,
    popupStyle: PopupStyle = PopupStyleAmbient.currentOrElse { DefaultPopupStyle() },
    children: @Composable () -> Unit
) {
    val navigator = NavigatorAmbient.current

    val coordinatesHolder =
        holder<LayoutCoordinates?> { null }

    Stack(modifier = Modifier.onPositioned { coordinatesHolder.value = it }) {
        Clickable(onClick = {
            navigator.push(
                PopupRoute(
                    position = coordinatesHolder.value!!.boundsInRoot.let {
                        IntPxBounds(
                            left = it.left.toInt().ipx,
                            top = it.top.toInt().ipx,
                            right = it.right.toInt().ipx,
                            bottom = it.bottom.toInt().ipx
                        )
                    },
                    onCancel = onCancel
                ) {
                    PopupMenu(
                        items = items,
                        style = popupStyle
                    )
                }
            )
        }, children = children)
    }
}