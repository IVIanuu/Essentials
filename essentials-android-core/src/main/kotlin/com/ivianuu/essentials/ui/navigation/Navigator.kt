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

package com.ivianuu.essentials.ui.navigation

import androidx.compose.Composable
import androidx.compose.Providers
import androidx.compose.Stable
import androidx.compose.frames.modelListOf
import androidx.compose.getValue
import androidx.compose.mutableStateOf
import androidx.compose.setValue
import androidx.compose.staticAmbientOf
import com.ivianuu.essentials.ui.animatedstack.AnimatedStack
import com.ivianuu.essentials.ui.animatedstack.AnimatedStackChild
import com.ivianuu.essentials.ui.common.onBackPressed
import com.ivianuu.essentials.ui.core.RetainedObjects
import com.ivianuu.essentials.ui.core.RetainedObjectsAmbient
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

@Stable
class Navigator {

    var handleBack by mutableStateOf(true)
    var popsLastRoute by mutableStateOf(false)

    private val _backStack = modelListOf<RouteState>()
    val backStack: List<Route> get() = _backStack.map { it.route }

    val hasRoot: Boolean get() = _backStack.isNotEmpty()

    @Composable
    operator fun invoke() {
        val backPressEnabled = handleBack &&
                backStack.isNotEmpty() &&
                (popsLastRoute || backStack.size > 1)
        onBackPressed(enabled = backPressEnabled) {
            if (_backStack.none { it.stackChild.isAnimating }) popTop()
        }

        Providers(NavigatorAmbient provides this) {
            AnimatedStack(children = _backStack.map { it.stackChild })
        }
    }

    fun setRoot(content: @Composable () -> Unit) {
        setRoot(Route(content = content))
    }

    fun setRoot(route: Route) {
        setBackStackInternal(listOf(RouteState(route)))
    }

    fun push(content: @Composable () -> Unit) {
        push<Unit>(Route(content = content))
    }

    fun push(route: Route) {
        push<Unit>(route)
    }

    @JvmName("pushForResult")
    fun <T : Any> push(content: @Composable () -> Unit): Deferred<T?> {
        return push<T>(Route(content = content))
    }

    @JvmName("pushForResult")
    fun <T : Any> push(route: Route): Deferred<T?> {
        val routeState = RouteState(route)
        val newBackStack = _backStack.toMutableList()
        newBackStack += routeState
        setBackStackInternal(newBackStack)
        return routeState.result as Deferred<T?>
    }

    fun replace(content: @Composable () -> Unit) {
        replace<Unit>(Route(content = content))
    }

    fun replace(route: Route) {
        replace<Unit>(route)
    }

    @JvmName("replaceForResult")
    fun <T : Any> replace(content: @Composable () -> Unit): Deferred<T?> {
        return replace<T>(Route(content = content))
    }

    @JvmName("replaceForResult")
    fun <T : Any> replace(route: Route): Deferred<T?> {
        val routeState = RouteState(route)
        val newBackStack = _backStack.toMutableList()
            .also { it.removeAt(it.lastIndex) }
        newBackStack += routeState
        setBackStackInternal(newBackStack)
        return routeState.result as Deferred<T?>
    }

    fun popTop(result: Any? = null) {
        popInternal(route = _backStack.last(), result = result)
    }

    fun popToRoot() {
        val newTopRoute = _backStack.first()
        setBackStackInternal(listOf(newTopRoute))
    }

    fun popTo(route: Route) {
        val index = _backStack.indexOfFirst { it.route == route }
        val newBackStack = _backStack.subList(0, index)
        setBackStackInternal(newBackStack)
    }

    fun pop(route: Route, result: Any? = null) {
        popInternal(route = _backStack.first { it.route == route }, result = result)
    }

    private fun popInternal(route: RouteState, result: Any? = null) {
        route.setResult(result)
        val newBackStack = _backStack.toMutableList()
        newBackStack -= route
        setBackStackInternal(newBackStack)
    }

    fun setBackStack(newBackStack: List<Route>) {
        setBackStackInternal(
            newBackStack.map { route ->
                _backStack.firstOrNull { it.route == route } ?: RouteState(route)
            }
        )
    }

    private fun setBackStackInternal(newBackStack: List<RouteState>) {
        val oldBackStack = _backStack.toList()
        _backStack.clear()
        _backStack += newBackStack
        oldBackStack
            .filterNot { it in newBackStack }
            .forEach { it.detach() }
    }

    private class RouteState(val route: Route) {

        val stackChild = AnimatedStackChild(
            key = this,
            opaque = route.opaque,
            enterTransition = route.enterTransition,
            exitTransition = route.exitTransition
        ) {
            Providers(
                RetainedObjectsAmbient provides retainedObjects,
                RouteAmbient provides route
            ) {
                route()
            }
        }

        private val retainedObjects = RetainedObjects()

        private val _result = CompletableDeferred<Any?>()
        val result: Deferred<Any?> get() = _result

        fun detach() {
            if (!_result.isCompleted) setResult(null)
            retainedObjects.dispose()
        }

        fun setResult(result: Any?) {
            _result.complete(result)
        }

    }

}

val NavigatorAmbient =
    staticAmbientOf<Navigator>()
