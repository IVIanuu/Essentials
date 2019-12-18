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

package com.ivianuu.essentials.ui.material

import android.os.Build
import android.view.View
import androidx.compose.Ambient
import androidx.compose.Composable
import androidx.compose.Immutable
import androidx.compose.ambient
import androidx.ui.graphics.Color
import androidx.ui.graphics.toArgb
import com.ivianuu.essentials.ui.core.ActivityAmbient
import com.ivianuu.essentials.util.addFlag
import com.ivianuu.essentials.util.isLight
import com.ivianuu.essentials.util.removeFlag

@Immutable
data class SystemBarConfig(
    val statusBarColor: Color = Color.Black,
    val lightStatusBar: Boolean = statusBarColor.isLight,
    val navigationBarColor: Color = Color.Black,
    val lightNavigationBar: Boolean = navigationBarColor.isLight
)

@Composable
fun SystemBarTheme(
    config: SystemBarConfig = SystemBarConfig(),
    children: @Composable() () -> Unit
) {
    applySystemBarConfig(config)
    SystemBarConfigAmbient.Provider(value = config, children = children)
}

@Composable
private fun applySystemBarConfig(config: SystemBarConfig) {
    val activity = ambient(ActivityAmbient)
    val window = activity.window
    val decorView = window.decorView
    window.statusBarColor = config.statusBarColor.toArgb()
    decorView.systemUiVisibility =
        if (config.lightStatusBar)
            decorView.systemUiVisibility.addFlag(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        else
            decorView.systemUiVisibility.removeFlag(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

    window.navigationBarColor = config.navigationBarColor.toArgb()
    if (Build.VERSION.SDK_INT >= 26) {
        decorView.systemUiVisibility =
            if (config.lightNavigationBar)
                decorView.systemUiVisibility.addFlag(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
            else
                decorView.systemUiVisibility.removeFlag(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
    }
}

private val SystemBarConfigAmbient = Ambient.of<SystemBarConfig>()