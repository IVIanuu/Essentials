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

package com.ivianuu.essentials.gestures.action

import androidx.compose.runtime.Composable
import com.ivianuu.essentials.gestures.action.ui.picker.ActionPickerResult
import com.ivianuu.essentials.permission.Permission
import com.ivianuu.essentials.ui.navigation.Key
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.GivenSetElement
import com.ivianuu.injekt.Macro
import com.ivianuu.injekt.Qualifier
import kotlinx.coroutines.flow.Flow

data class Action(
    val id: String,
    val title: String,
    val permissions: List<com.ivianuu.injekt.common.Key<Permission>> = emptyList(),
    val unlockScreen: Boolean = false,
    val enabled: Boolean = true,
    val icon: Flow<ActionIcon>,
) {
    constructor(
        id: ActionId,
        title: String,
        permissions: List<com.ivianuu.injekt.common.Key<Permission>> = emptyList(),
        unlockScreen: Boolean = false,
        enabled: Boolean = true,
        icon: Flow<ActionIcon>
    ) : this(id.value, title, permissions, unlockScreen, enabled, icon)
}

typealias ActionIcon = @Composable () -> Unit

abstract class ActionId(val value: String)

@Qualifier
annotation class ActionBinding<I : ActionId>

@Macro
@GivenSetElement
fun <T : @ActionBinding<I> Action, I : ActionId> actionBindingImpl(
    @Given id: I,
    @Given provider: () -> T,
): Pair<String, () -> Action> = id.value to provider

typealias ActionExecutor = suspend () -> Unit

@Qualifier
annotation class ActionExecutorBinding<I : ActionId>

@Macro
@GivenSetElement
fun <T : @ActionExecutorBinding<I> suspend () -> Unit, I : ActionId> actionExecutorBindingImpl(
    @Given id: I,
    @Given instance: T
): Pair<String, ActionExecutor> = id.value to instance

internal operator fun com.ivianuu.injekt.common.Key<Permission>.plus(other: com.ivianuu.injekt.common.Key<Permission>) = listOf(this, other)

interface ActionFactory {
    suspend fun handles(id: String): Boolean
    suspend fun createAction(id: String): Action
    suspend fun createExecutor(id: String): ActionExecutor
}

@Qualifier
annotation class ActionSettingsKeyBinding<I : ActionId>

typealias ActionSettingsKey = Key

@Macro
@GivenSetElement
fun <T : @ActionSettingsKeyBinding<I> S, S : Any, I : ActionId> actionSettingsKeyBindingImpl(
    @Given id: I,
    @Given instance: T,
): Pair<String, ActionSettingsKey> = id.value to instance

@Qualifier
annotation class ActionFactoryBinding

@Macro
@GivenSetElement
fun <T : @ActionFactoryBinding S, S : ActionFactory> actionFactoryBindingImpl(
    @Given instance: T): ActionFactory = instance

interface ActionPickerDelegate {
    val title: String
    val icon: @Composable () -> Unit
    val settingsUi: @Composable (() -> Unit)? get() = null
    suspend fun getResult(): ActionPickerResult?
}

@Qualifier
annotation class ActionPickerDelegateBinding

@Macro
@GivenSetElement
fun <T : @ActionPickerDelegateBinding S, S : ActionPickerDelegate> actionPickerDelegateBindingImpl(
    @Given instance: T): ActionPickerDelegate = instance
