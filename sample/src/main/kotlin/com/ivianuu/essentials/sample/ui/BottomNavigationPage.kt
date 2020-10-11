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

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ivianuu.essentials.sample.R
import com.ivianuu.essentials.ui.animatedstack.AnimatedBox
import com.ivianuu.essentials.ui.core.InsetsPadding
import com.ivianuu.essentials.ui.core.overlaySystemBarBgColor
import com.ivianuu.essentials.ui.core.systemBarStyle
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.util.isLight
import com.ivianuu.injekt.FunBinding

@FunBinding
@Composable
fun BottomNavigationPage() {
    var selectedItem by savedInstanceState { BottomNavItem.values().first() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Bottom navigation") }) },
        bottomBar = {
            Surface(
                modifier = Modifier.systemBarStyle(
                    bgColor = overlaySystemBarBgColor(MaterialTheme.colors.primary),
                    lightIcons = MaterialTheme.colors.primary.isLight
                ),
                elevation = 8.dp,
                color = MaterialTheme.colors.primary
            ) {
                InsetsPadding(start = false, top = false, end = false) {
                    BottomNavigation(
                        backgroundColor = MaterialTheme.colors.primary,
                        elevation = 0.dp
                    ) {
                        BottomNavItem.values().forEach { item ->
                            BottomNavigationItem(
                                selected = item == selectedItem,
                                onClick = { selectedItem = item },
                                icon = { Icon(vectorResource(item.icon)) },
                                label = { Text(item.title) }
                            )
                        }
                    }
                }
            }
        }
    ) {
        AnimatedBox(current = selectedItem) { item ->
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(item.color)
            )
        }
    }
}


private enum class BottomNavItem(
    val title: String,
    val icon: Int,
    val color: Color
) {
    Home(
        title = "Home",
        icon = R.drawable.ic_home,
        color = Color.Yellow
    ),
    Mails(
        title = "Mails",
        icon = R.drawable.ic_email,
        color = Color.Red
    ),
    Search(
        title = "Search",
        icon = R.drawable.ic_search,
        color = Color.Blue
    ),
    Schedule(
        title = "Schedule",
        icon = R.drawable.ic_view_agenda,
        color = Color.Cyan
    ),
    Settings(
        title = "Settings",
        icon = R.drawable.ic_settings,
        color = Color.Green
    )
}