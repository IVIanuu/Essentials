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

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import com.ivianuu.essentials.sample.R
import com.ivianuu.essentials.ui.animation.*
import com.ivianuu.essentials.ui.core.*
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.*
import com.ivianuu.injekt.*

@Provide val bottomNavigationHomeItem = HomeItem("Bottom navigation") { BottomNavigationKey }

object BottomNavigationKey : Key<Nothing>

@Provide val bottomNavigationUi: KeyUi<BottomNavigationKey> = {
  var selectedItem by remember { mutableStateOf(BottomNavItem.values().first()) }

  Scaffold(
    topBar = { TopAppBar(title = { Text("Bottom navigation") }) },
    bottomBar = {
      Surface(
        modifier = Modifier.systemBarStyle(
          bgColor = overlaySystemBarBgColor(MaterialTheme.colors.primary),
          lightIcons = MaterialTheme.colors.primary.isLight,
          elevation = 8.dp
        ),
        elevation = 8.dp,
        color = MaterialTheme.colors.primary
      ) {
        InsetsPadding(left = false, top = false, right = false) {
          BottomNavigation(
            backgroundColor = MaterialTheme.colors.primary,
            elevation = 0.dp
          ) {
            BottomNavItem.values().forEach { item ->
              BottomNavigationItem(
                alwaysShowLabel = false,
                selected = item == selectedItem,
                onClick = { selectedItem = item },
                icon = { Icon(painterResource(item.icon), null) },
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
  HOME(
    title = "Home",
    icon = R.drawable.ic_home,
    color = Color.Yellow
  ),
  MAILS(
    title = "Mails",
    icon = R.drawable.ic_email,
    color = Color.Red
  ),
  SEARCH(
    title = "Search",
    icon = R.drawable.ic_search,
    color = Color.Blue
  ),
  SCHEDULE(
    title = "Schedule",
    icon = R.drawable.ic_view_agenda,
    color = Color.Cyan
  ),
  SETTINGS(
    title = "Settings",
    icon = R.drawable.ic_settings,
    color = Color.Green
  )
}
