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

package com.ivianuu.essentials.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.Recomposer
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner
import com.ivianuu.essentials.ui.DecorateUi
import com.ivianuu.injekt.android.ActivityComponent
import com.ivianuu.injekt.android.activityComponent
import com.ivianuu.injekt.merge.MergeInto
import com.ivianuu.injekt.merge.mergeComponent

/**
 * Base activity
 */
abstract class EsActivity : AppCompatActivity() {

    protected open val containerId: Int
        get() = android.R.id.content

    private lateinit var composition: Composition

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container = findViewById<ViewGroup>(containerId)
        ViewTreeLifecycleOwner.set(container, this)
        ViewTreeSavedStateRegistryOwner.set(container, this)
        ViewTreeViewModelStoreOwner.set(container, this)

        composition = container.setContent(Recomposer.current()) {
            activityComponent.mergeComponent<EsActivityComponent>().decorateUi {
                content()
            }
        }
    }

    override fun onDestroy() {
        composition.dispose()
        super.onDestroy()
    }

    @Composable
    protected abstract fun content()
}

@MergeInto(ActivityComponent::class)
interface EsActivityComponent {
    val decorateUi: DecorateUi
}