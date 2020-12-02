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

package com.ivianuu.essentials.sample.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ivianuu.essentials.ui.animatedstack.animation.SharedElement
import com.ivianuu.essentials.ui.animatedstack.animation.SharedElementStackTransition
import com.ivianuu.essentials.ui.layout.center
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.KeyUiBinding
import com.ivianuu.essentials.ui.navigation.NavigationOptions
import com.ivianuu.essentials.ui.navigation.NavigationOptionsFactoryBinding
import com.ivianuu.injekt.FunApi
import com.ivianuu.injekt.FunBinding

@HomeItemBinding("Shared element")
class SharedElementKey(val color: Color)

@KeyUiBinding<SharedElementKey>
@FunBinding
@Composable
fun SharedElementScreen(key: SharedElementKey) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Shared Elements") }) }
    ) {
        Box {
            SharedElement(tag = "b", modifier = Modifier.center()) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(key.color, CircleShape)
                )
            }
        }
    }
}

@NavigationOptionsFactoryBinding<SharedElementKey>
@FunBinding
fun createSharedElementNavigationOptions(@FunApi key: SharedElementKey) = NavigationOptions(
    enterTransition = SharedElementStackTransition("Shared element" to "b"),
    exitTransition = SharedElementStackTransition("Shared element" to "b")
)