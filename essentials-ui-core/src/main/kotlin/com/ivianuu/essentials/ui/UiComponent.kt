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

package com.ivianuu.essentials.ui

import androidx.compose.runtime.ambientOf
import androidx.compose.runtime.staticAmbientOf
import com.ivianuu.injekt.android.ActivityComponent
import com.ivianuu.injekt.merge.MergeChildComponent
import com.ivianuu.injekt.merge.MergeInto

@MergeChildComponent
interface UiComponent

@MergeInto(ActivityComponent::class)
interface UiComponentFactoryOwner {
    val uiComponentFactory: () -> UiComponent
}

val AmbientUiComponent = staticAmbientOf<UiComponent> { error("No UiComponent installed") }
