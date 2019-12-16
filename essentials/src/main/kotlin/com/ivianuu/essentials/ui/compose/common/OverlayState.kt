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

package com.ivianuu.essentials.ui.compose.common

import androidx.compose.Ambient
import androidx.compose.Composable
import androidx.compose.Immutable
import androidx.compose.Observe
import androidx.compose.frames.modelListOf
import androidx.compose.key
import androidx.compose.remember
import androidx.ui.core.Layout
import androidx.ui.core.ParentData
import androidx.ui.core.PxPosition
import androidx.ui.core.tightMax

@Composable
fun Overlay(state: OverlayState = remember { OverlayState() }) {
    OverlayAmbient.Provider(value = state) {
        OverlayLayout {
            val visibleEntries = state.entries.filterVisible()

            state.entries
                .filter { it in visibleEntries || it.keepState }
                .map {
                    OverlayEntryParentData(
                        isVisible = it in visibleEntries,
                        entry = it
                    )
                }
                .forEach {
                    key(it.entry) {
                        Observe {
                            ParentData(data = it) {
                                it.entry.content()
                            }
                        }
                    }
                }
        }
    }
}

class OverlayState(initialEntries: List<OverlayEntry> = emptyList()) {

    private val _entries = modelListOf<OverlayEntry>()
    val entries: List<OverlayEntry>
        get() = _entries

    init {
        if (_entries.isEmpty()) {
            _entries += initialEntries
        }
    }

    fun add(entry: OverlayEntry) {
        _entries.add(entry)
    }

    fun add(index: Int, entry: OverlayEntry) {
        _entries.add(index, entry)
    }

    fun remove(entry: OverlayEntry) {
        _entries.remove(entry)
    }

}

@Immutable
data class OverlayEntry(
    val opaque: Boolean = false,
    val keepState: Boolean = false,
    val content: @Composable() () -> Unit
)

val OverlayAmbient = Ambient.of<OverlayState>()

@Composable
private fun OverlayLayout(
    children: @Composable() () -> Unit
) {
    Layout(children = children) { measureables, constraints ->
        // force children to fill the whole space
        val childConstraints = constraints.tightMax()

        // get only visible routes
        val placeables = measureables
            .filter { (it.parentData as OverlayEntryParentData).isVisible }
            .map { it.measure(childConstraints) }

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { it.place(PxPosition.Origin) }
        }
    }
}

private data class OverlayEntryParentData(
    val isVisible: Boolean,
    val entry: OverlayEntry
)

private fun List<OverlayEntry>.filterVisible(): List<OverlayEntry> {
    val visibleEntries = mutableListOf<OverlayEntry>()

    for (entry in reversed()) {
        visibleEntries += entry
        if (!entry.opaque) break
    }

    return visibleEntries
}