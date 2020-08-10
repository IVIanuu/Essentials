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

package com.ivianuu.essentials.sample.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.remember
import com.ivianuu.essentials.twilight.TwilightTheme
import com.ivianuu.essentials.ui.animatedstack.DefaultStackTransitionAmbient
import com.ivianuu.essentials.ui.animatedstack.animation.HorizontalStackTransition
import com.ivianuu.essentials.ui.core.AppUi
import com.ivianuu.essentials.ui.navigation.navigator
import com.ivianuu.injekt.Reader

@AppUi
@Reader
@Composable
fun SampleUi() {
    TwilightTheme {
        Providers(
            DefaultStackTransitionAmbient provides remember { HorizontalStackTransition() }
        ) {
            if (!navigator.hasRoot) {
                navigator.setRoot { HomePage() }
            }
            navigator.content()
        }
    }
}
