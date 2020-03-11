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

package com.ivianuu.essentials.android.ui.common

import androidx.compose.Composable
import androidx.compose.remember
import androidx.ui.core.PointerEventPass
import androidx.ui.core.PointerId
import androidx.ui.core.PointerInput
import androidx.ui.core.changedToDown
import androidx.ui.core.consumeDownChange

@Composable
fun AbsorbPointer(
    absorb: Boolean = true,
    children: @Composable () -> Unit
) {
    val consumedIds = remember { mutableSetOf<PointerId>() }
    PointerInput(
        pointerInputHandler = { changes, pass, _ ->
            if (absorb && (pass == PointerEventPass.InitialDown ||
                        pass == PointerEventPass.PreDown ||
                        pass == PointerEventPass.PostDown)
            ) {
                changes.map { change ->
                    if (change.changedToDown()) {
                        change.consumeDownChange().also { consumedChange ->
                            consumedIds += consumedChange.id
                        }
                    } else {
                        change
                    }
                }
            } else {
                changes.map { change ->
                    if (change.id in consumedIds) {
                        change.copy(consumed = change.consumed.copy(downChange = false))
                            .also { consumedIds -= change.id }
                    } else {
                        change
                    }
                }
            }
        },
        cancelHandler = { consumedIds.clear() },
        children = children
    )
}