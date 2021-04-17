package com.ivianuu.essentials.ui.dialog

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.*
import com.ivianuu.essentials.ui.navigation.*
import com.ivianuu.injekt.*

data class SingleChoiceListKey<T : Any>(
    val items: List<Item<T>>,
    val selectedItem: T,
    val title: String
) : Key<T> {
    data class Item<T : Any>(val value: T, val title: String)
}

@Given
fun singleChoiceListUi(
    @Given key: SingleChoiceListKey<Any>,
    @Given navigator: Navigator
): KeyUi<SingleChoiceListKey<Any>> = {
    DialogScaffold {
        SingleChoiceListDialog(
            items = remember {
                key.items
                    .map { it.value }
            },
            selectedItem = key.selectedItem,
            onSelectionChanged = { navigator.pop(key, it) },
            item = { item ->
                Text(key.items.single { it.value == item }.title)
            },
            title = { Text(key.title) },
            negativeButton = {
                TextButton(onClick = { navigator.pop(key, null) }) {
                    Text(stringResource(R.string.es_cancel))
                }
            }
        )
    }
}

@Given
val singleChoiceListUiOptionsFactory = DialogKeyUiOptionsFactory<SingleChoiceListKey<Any>>()

