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

package com.ivianuu.essentials.ui.base

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.ui.core.setContent
import com.ivianuu.essentials.injection.RetainedActivityComponent
import com.ivianuu.essentials.injection.initRetainedActivityComponentIfNeeded
import com.ivianuu.essentials.injection.retainedActivityComponent
import com.ivianuu.essentials.ui.core.AndroidComposeViewContainer
import com.ivianuu.essentials.ui.core.EsEnvironment
import com.ivianuu.essentials.ui.navigation.Navigator
import com.ivianuu.essentials.ui.navigation.NavigatorState
import com.ivianuu.essentials.ui.navigation.Route
import com.ivianuu.essentials.util.getViewModel
import com.ivianuu.essentials.util.unsafeLazy
import com.ivianuu.injekt.Component
import com.ivianuu.injekt.InjektTrait
import com.ivianuu.injekt.Module
import com.ivianuu.injekt.android.ActivityModule
import com.ivianuu.injekt.android.ActivityScope
import com.ivianuu.injekt.get

/**
 * Base activity
 */
abstract class EsActivity : AppCompatActivity(), InjektTrait {

    override val component by unsafeLazy {
        Component {
            scopes(ActivityScope)
            dependencies(retainedActivityComponent)
            modules(ActivityModule())
            modules(EsActivityModule(this@EsActivity))
            modules(this@EsActivity.modules())
        }
    }

    protected open val layoutRes: Int get() = 0

    protected open val containerId: Int
        get() = android.R.id.content

    private val composeViewContainer by unsafeLazy {
        AndroidComposeViewContainer(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRetainedActivityComponentIfNeeded { createRetainedComponent() }

        if (layoutRes != 0) {
            setContentView(layoutRes)
        }

        val container = findViewById<ViewGroup>(containerId)
        container.addView(composeViewContainer)
        composeViewContainer.setContent {
            WrapContentWithEnvironment(composeViewContainer) {
                content()
            }
        }
    }

    override fun onDestroy() {
        // todo use disposeComposition once fixed
        composeViewContainer.setContent { }
        super.onDestroy()
    }

    @Composable
    protected open fun WrapContentWithEnvironment(
        container: AndroidComposeViewContainer,
        content: @Composable() () -> Unit
    ) {
        EsEnvironment(
            activity = this,
            container = container,
            component = component,
            coroutineContext = lifecycleScope.coroutineContext,
            children = content
        )
    }

    @Composable
    protected abstract fun content()

    protected open fun modules(): List<Module> = emptyList()

    protected open fun retainedModules(): List<Module> = emptyList()

    protected open fun createRetainedComponent(): Component = RetainedActivityComponent {
        modules(retainedModules())
    }

    @Composable
    protected fun Navigator(startRoute: Route) {
        val state = get<NavigatorState>()
        if (state.backStack.isEmpty()) {
            state.push(startRoute)
        }
        Navigator(state = state)
    }
}

private fun EsActivityModule(activity: EsActivity) = Module {
    single {
        NavigatorState(
            coroutineScope = activity.getViewModel { CoroutineScopeViewModel() }.viewModelScope
        )
    }
}

// todo remove
private class CoroutineScopeViewModel : EsViewModel()