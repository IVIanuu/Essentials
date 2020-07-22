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

package com.ivianuu.essentials.boot

import com.ivianuu.injekt.ApplicationComponent
import com.ivianuu.injekt.Distinct
import com.ivianuu.injekt.Effect
import com.ivianuu.injekt.SetElements
import com.ivianuu.injekt.given

@Effect
annotation class BootListener {
    companion object {
        @SetElements(ApplicationComponent::class)
        operator fun <T : () -> Unit> invoke(): BootListeners = setOf(given<T>())
    }
}

@Distinct
typealias BootListeners = Set<() -> Unit>