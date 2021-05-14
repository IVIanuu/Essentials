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

package com.ivianuu.essentials.ui.dialog

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp

@Composable fun SimpleDialogListItem(
  leading: @Composable (() -> Unit)? = null,
  title: @Composable () -> Unit,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier.fillMaxWidth()
      .heightIn(min = 48.dp)
      .clickable(onClick = onClick),
    contentAlignment = Alignment.CenterStart
  ) {
    Row(
      modifier = Modifier.padding(start = 24.dp, end = 24.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
        if (leading != null) {
          leading()
          Spacer(Modifier.width(24.dp))
        }

        title()
      }
    }
  }
}
