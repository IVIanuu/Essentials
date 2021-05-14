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

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.*
import com.ivianuu.essentials.processrestart.*
import com.ivianuu.essentials.ui.layout.*
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.coroutines.*
import kotlinx.coroutines.*

@Given val restartProcessHomeItem = HomeItem("Restart process") { RestartProcessKey }

object RestartProcessKey : Key<Nothing>

@Given fun restartProcessUi(
  @Given processRestarter: ProcessRestarter,
  @Given scope: GivenCoroutineScope<KeyUiGivenScope>
): KeyUi<RestartProcessKey> = {
  Scaffold(
    topBar = { TopAppBar(title = { Text("Restart process") }) }
  ) {
    Button(
      modifier = Modifier.center(),
      onClick = {
        scope.launch {
          processRestarter()
        }
      }
    ) {
      Text("Restart process")
    }
  }
}
