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

package com.ivianuu.essentials.ui.resource

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ivianuu.essentials.ui.animatedstack.AnimatedBox
import com.ivianuu.essentials.ui.animatedstack.StackTransition
import com.ivianuu.essentials.ui.animatedstack.animation.FadeStackTransition
import com.ivianuu.essentials.ui.common.InsettingLazyColumnFor
import com.ivianuu.essentials.ui.core.rememberState
import com.ivianuu.essentials.ui.layout.center

@Composable
fun <T> ResourceLazyColumnFor(
    resource: Resource<List<T>>,
    modifier: Modifier = Modifier,
    transition: StackTransition = FadeStackTransition(),
    fail: @Composable (Throwable) -> Unit = { throw it },
    loading: @Composable () -> Unit = {
        CircularProgressIndicator(modifier = Modifier.center())
    },
    idle: @Composable () -> Unit = loading,
    successEmpty: @Composable () -> Unit = {},
    successItemContent: @Composable LazyItemScope.(T) -> Unit,
) {
    ResourceBox(
        resource = resource,
        modifier = modifier,
        transition = transition,
        error = fail,
        loading = loading,
        idle = idle
    ) { items ->
        if (items.isNotEmpty()) {
            InsettingLazyColumnFor(
                items = items,
                itemContent = successItemContent
            )
        } else {
            successEmpty()
        }
    }
}

@Composable
fun <T> ResourceLazyRowFor(
    resource: Resource<List<T>>,
    modifier: Modifier = Modifier,
    transition: StackTransition = FadeStackTransition(),
    error: @Composable (Throwable) -> Unit = { throw it },
    loading: @Composable () -> Unit = {
        CircularProgressIndicator(modifier = Modifier.center())
    },
    idle: @Composable () -> Unit = loading,
    successEmpty: @Composable () -> Unit = {},
    successItemContent: @Composable LazyItemScope.(T) -> Unit,
) {
    ResourceBox(
        resource = resource,
        modifier = modifier,
        transition = transition,
        error = error,
        loading = loading,
        idle = idle
    ) { items ->
        if (items.isNotEmpty()) {
            LazyRowFor(
                items = items,
                itemContent = successItemContent
            )
        } else {
            successEmpty()
        }
    }
}

@Composable
fun <T> ResourceBox(
    resource: Resource<T>,
    modifier: Modifier = Modifier,
    transition: StackTransition = FadeStackTransition(),
    error: @Composable (Throwable) -> Unit = {},
    loading: @Composable () -> Unit = {
        CircularProgressIndicator(
            modifier = Modifier.center()
        )
    },
    idle: @Composable () -> Unit = loading,
    success: @Composable (T) -> Unit
) {
    // we only wanna animate if the resource type has changed
    val resourceState = rememberState(resource::class) { resource }
    resourceState.value = resource

    AnimatedBox(
        current = resourceState,
        modifier = modifier,
        transition = transition
    ) { currentState ->
        when (val currentValue = currentState.value) {
            is Idle -> idle()
            is Loading -> loading()
            is Success -> success(currentValue.value)
            is Error -> error(currentValue.error)
        }
    }
}