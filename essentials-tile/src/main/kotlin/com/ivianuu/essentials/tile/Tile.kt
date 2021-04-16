/*
 * Copyright 2020 Manuel Wrage
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

package com.ivianuu.essentials.tile

import android.graphics.drawable.*
import com.ivianuu.essentials.optics.*
import com.ivianuu.essentials.util.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.android.*
import com.ivianuu.injekt.common.*
import com.ivianuu.injekt.scope.*
import kotlinx.coroutines.flow.*

@Optics
data class TileState<out T : AbstractFunTileService>(
    val icon: Icon? = null,
    val iconRes: Int? = null,
    val label: String? = null,
    val labelRes: Int? = null,
    val description: String? = null,
    val descriptionRes: Int? = null,
    val status: Status = Status.UNAVAILABLE,
    val onTileClicked: () -> Unit = {}
) {
    enum class Status {
        UNAVAILABLE, ACTIVE, INACTIVE
    }
}

fun Boolean.toTileStatus() = if (this) TileState.Status.ACTIVE else TileState.Status.INACTIVE

@Given
fun <@Given T : StateFlow<TileState<S>>, S : AbstractFunTileService> tileStateElement(
    @Given serviceKey: TypeKey<S>,
    @Given provider: () -> T
): Pair<TypeKey<AbstractFunTileService>, () -> StateFlow<TileState<*>>> =
    serviceKey to provider.cast()

typealias TileGivenScope = DefaultGivenScope

@Given
val tileGivenScopeModule = ChildGivenScopeModule1<ServiceGivenScope, TypeKey<AbstractFunTileService>, TileGivenScope>()
