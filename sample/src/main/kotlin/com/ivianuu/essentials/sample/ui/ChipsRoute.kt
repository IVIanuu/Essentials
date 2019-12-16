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

package com.ivianuu.essentials.sample.ui

import androidx.compose.Composable
import androidx.compose.Pivotal
import androidx.compose.remember
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.contentColor
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.Container
import androidx.ui.layout.EdgeInsets
import androidx.ui.layout.FlowRow
import androidx.ui.layout.Padding
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import com.ivianuu.essentials.ui.compose.dialog.PrimaryColors
import com.ivianuu.essentials.ui.compose.injekt.inject
import com.ivianuu.essentials.ui.compose.material.EsSurface
import com.ivianuu.essentials.ui.compose.material.EsTopAppBar
import com.ivianuu.essentials.ui.compose.material.RippleColorProvider
import com.ivianuu.essentials.ui.compose.material.Scaffold
import com.ivianuu.essentials.ui.compose.material.contentColorFor
import com.ivianuu.essentials.ui.compose.navigation.Route
import com.ivianuu.essentials.util.Toaster

val ChipsRoute = Route {
    Scaffold(
        topAppBar = { EsTopAppBar("Chips") },
        body = {
            Padding(padding = 8.dp) {
                FlowRow(mainAxisSpacing = 8.dp, crossAxisSpacing = 8.dp) {
                    Names.shuffled().forEach { name ->
                        Chip(name = name)
                    }
                }
            }
        }
    )
}

@Composable
private fun Chip(@Pivotal name: String) {
    val toaster = inject<Toaster>()
    val color = remember { PrimaryColors.toList().shuffled().first() }
    EsSurface(
        color = color,
        contentColor = contentColorFor(color),
        shape = RoundedCornerShape(16.dp)
    ) {
        Container(
            height = 32.dp,
            padding = EdgeInsets(
                left = 12.dp,
                right = 12.dp
            )
        ) {
            RippleColorProvider(color = contentColor().copy(alpha = 0.5f)) {
                Ripple(bounded = false) {
                    Clickable(onClick = {
                        toaster.toast("Clicked $name")
                    }) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography().body2
                        )
                    }
                }
            }
        }
    }
}

private val Names = listOf(
    "Hans Dieter Josef",
    "Alex Meier",
    "Kim Jong Hun Ching Chang Chong Meier Richard Anderson",
    "Claude Hardy",
    "Wilhelm Richards",
    "Gustova Da Costa",
    "Lisa Wray",
    "Michael Clark",
    "Yusuf Grimes",
    "Mustafa Müller",
    "Diego Ribas Da Cunha",
    "Jan"
)