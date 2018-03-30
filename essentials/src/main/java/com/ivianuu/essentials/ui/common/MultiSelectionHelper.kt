/*
 * Copyright 2018 Manuel Wrage
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

package com.ivianuu.essentials.ui.common

/**
 * Simple helper class for multi selection screens
 */
class MultiSelectionHelper<T>(private val onChanged: () -> Unit) {

    val selectedItems: Set<T>
        get() = _selectedItems
    private val _selectedItems = mutableSetOf<T>()

    fun setItemSelected(item: T, selected: Boolean) {
        if (selected) {
            _selectedItems.add(item)
        } else {
            _selectedItems.remove(item)
        }
        onChanged.invoke()
    }

    fun setItemsSelected(items: List<T>, selected: Boolean) {
        if (selected) {
            _selectedItems.addAll(items)
        } else {
            _selectedItems.removeAll(items)
        }
        onChanged.invoke()
    }

    fun deselectAll() {
        _selectedItems.clear()
        onChanged.invoke()
    }

    fun isSelected(item: T) = selectedItems
}