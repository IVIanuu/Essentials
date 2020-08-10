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

package com.ivianuu.essentials.ui.dialog

import androidx.compose.foundation.Box
import androidx.compose.foundation.ContentGravity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.EmphasisAmbient
import androidx.compose.material.ProvideEmphasis
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleDialogListItem(
    leading: @Composable (() -> Unit)? = null,
    title: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .heightIn(minHeight = 48.dp)
            .clickable(onClick = onClick),
        gravity = ContentGravity.CenterStart
    ) {
        Row(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp),
            verticalGravity = Alignment.CenterVertically
        ) {
            ProvideEmphasis(emphasis = EmphasisAmbient.current.high) {
                if (leading != null) {
                    leading()
                    Spacer(Modifier.width(24.dp))
                }

                title()
            }
        }
    }
}
