/*
 * Copyright 2018 Manuel Wrage
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

package com.ivianuu.essentials.util.ext

import com.ivianuu.autodispose.LifecycleScopeProvider
import com.ivianuu.autodispose.OutsideLifecycleException
import com.ivianuu.autodispose.ScopeProvider
import io.reactivex.Maybe

fun <E> LifecycleScopeProvider<E>.toScopeProvider(): ScopeProvider {
    val currentEvent = peekLifecycle()
            ?: throw OutsideLifecycleException("lifecycle has not started yet")

    val untilEvent = correspondingEvents().apply(currentEvent)

    return toScopeProvider(untilEvent)
}

fun <E> LifecycleScopeProvider<E>.toScopeProvider(untilEvent: E): ScopeProvider {
    return object : ScopeProvider {
        override fun requestScope(): Maybe<*> {
            return lifecycle()
                .filter { it == untilEvent }
                .take(1)
                .singleElement()
        }
    }
}