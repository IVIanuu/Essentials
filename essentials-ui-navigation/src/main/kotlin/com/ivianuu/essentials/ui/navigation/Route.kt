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

package com.ivianuu.essentials.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.onDispose
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.UiSavedStateRegistry
import androidx.compose.runtime.savedinstancestate.UiSavedStateRegistryAmbient
import androidx.compose.runtime.staticAmbientOf
import com.ivianuu.essentials.ui.UiComponent
import com.ivianuu.essentials.ui.UiComponentAmbient
import com.ivianuu.essentials.ui.animatedstack.AnimatedStackChild
import com.ivianuu.essentials.ui.animatedstack.StackTransition
import com.ivianuu.essentials.ui.common.RetainedObjects
import com.ivianuu.essentials.ui.common.RetainedObjectsAmbient
import com.ivianuu.injekt.merge.MergeInto
import com.ivianuu.injekt.merge.mergeComponent
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

@Immutable
class Route(
    val enterTransition: StackTransition? = null,
    val exitTransition: StackTransition? = null,
    val opaque: Boolean = false,
    private val content: @Composable () -> Unit
) {

    internal val stackChild = AnimatedStackChild(
        key = this,
        opaque = opaque,
        enterTransition = enterTransition,
        exitTransition = exitTransition
    ) {
        val compositionKey = currentComposer.currentCompoundKeyHash

        val savedStateRegistry = remember {
            UiSavedStateRegistry(
                restoredValues = savedState.remove(compositionKey),
                canBeSaved = { true }
            )
        }
        Providers(
            RouteAmbient provides this,
            UiSavedStateRegistryAmbient provides savedStateRegistry,
            RetainedObjectsAmbient provides retainedObjects
        ) {
            val uiComponent = UiComponentAmbient.current
            remember(uiComponent) { uiComponent.mergeComponent<RouteComponent>() }
                .decorateRoute(this, content)
            onDispose {
                savedState[compositionKey] = savedStateRegistry.performSave()
            }
        }
    }

    private val _result = CompletableDeferred<Any?>()
    internal val result: Deferred<Any?> get() = _result
    private var resultToSend: Any? = null
    fun setResult(result: Any?) {
        resultToSend = result
    }

    private var savedState =
        mutableMapOf<Any, Map<String, List<Any?>>>()

    private val retainedObjects = RetainedObjects()

    constructor(
        transition: StackTransition? = null,
        opaque: Boolean = false,
        content: @Composable () -> Unit
    ) : this(transition, transition, opaque, content)

    internal fun detach() {
        _result.complete(resultToSend)
        retainedObjects.dispose()
    }
}

val RouteAmbient = staticAmbientOf<Route>()

@MergeInto(UiComponent::class)
interface RouteComponent {
    val decorateRoute: DecorateRoute
}