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

package com.ivianuu.essentials.gestures

import com.ivianuu.essentials.accessibility.AccessibilityComponent
import com.ivianuu.essentials.accessibility.AccessibilityConfig
import com.ivianuu.essentials.accessibility.BindAccessibilityComponent
import com.ivianuu.essentials.util.AppCoroutineDispatchers
import com.ivianuu.injekt.ApplicationScoped

/**
 * Simulates device inputs such as back or home
 */
@BindAccessibilityComponent
@ApplicationScoped
class GlobalActions(
    dispatchers: AppCoroutineDispatchers
) : AccessibilityComponent(dispatchers.computation) {

    override val config: AccessibilityConfig
        get() = AccessibilityConfig()

    fun performAction(action: Int): Boolean =
        service?.performGlobalAction(action) ?: false
}
