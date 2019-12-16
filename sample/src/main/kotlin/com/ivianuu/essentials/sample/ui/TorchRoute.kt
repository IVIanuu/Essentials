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

import androidx.compose.remember
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.layout.Center
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import com.ivianuu.essentials.gestures.torch.TorchManager
import com.ivianuu.essentials.ui.compose.common.SimpleScreen
import com.ivianuu.essentials.ui.compose.coroutines.collect
import com.ivianuu.essentials.ui.compose.es.ComposeControllerRoute
import com.ivianuu.essentials.ui.compose.injekt.inject
import com.ivianuu.essentials.ui.compose.layout.Column
import com.ivianuu.essentials.ui.compose.layout.CrossAxisAlignment
import com.ivianuu.essentials.ui.compose.layout.HeightSpacer

val TorchRoute = ComposeControllerRoute {
    SimpleScreen(title = "Torch") {
        Center {
            val torchManager = inject<TorchManager>()
            val torchState = collect(remember { torchManager.torchState }, false)

            Column(crossAxisAlignment = CrossAxisAlignment.Center) {
                Text(
                    "Torch is ${if (torchState) "enabled" else "disabled"}",
                    style = MaterialTheme.typography().h4
                )
                HeightSpacer(8.dp)
                Button(
                    text = "Toggle torch",
                    onClick = { torchManager.toggleTorch() }
                )
            }
        }
    }
}