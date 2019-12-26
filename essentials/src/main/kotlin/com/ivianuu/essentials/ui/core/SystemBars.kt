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

package com.ivianuu.essentials.ui.core

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.Ambient
import androidx.compose.Composable
import androidx.compose.Immutable
import androidx.compose.ambient
import androidx.compose.frames.modelListOf
import androidx.compose.onPreCommit
import androidx.compose.remember
import androidx.ui.graphics.Color
import androidx.ui.graphics.toArgb
import androidx.ui.material.MaterialTheme
import com.github.ajalt.timberkt.d
import com.ivianuu.essentials.ui.common.framed
import com.ivianuu.essentials.util.addFlag
import com.ivianuu.essentials.util.isLight
import com.ivianuu.essentials.util.setFlag

@Immutable
data class SystemBarStyle(
    val statusBarColor: Color = Color.Black.copy(alpha = 0.2f),
    val lightStatusBar: Boolean = statusBarColor.isLight,
    val navigationBarColor: Color = Color.Black.copy(alpha = 0.4f),
    val lightNavigationBar: Boolean = navigationBarColor.isLight,
    val navigationBarDividerColor: Color = Color.Transparent
)

@Composable
fun ColorOverlaySystemBarStyle(
    statusBarColor: Color = Color.Black.copy(alpha = 0.2f),
    lightStatusBar: Boolean = statusBarColor.isLight,
    navigationBarColor: Color = Color.Black.copy(alpha = 0.2f),
    lightNavigationBar: Boolean = navigationBarColor.isLight
) = SystemBarStyle(
    statusBarColor = statusBarColor,
    lightStatusBar = lightStatusBar,
    navigationBarColor = navigationBarColor,
    lightNavigationBar = lightNavigationBar
)

@Composable
fun TintedSystemBarStyle(
    statusBarColor: Color = MaterialTheme.colors().primary,
    lightStatusBar: Boolean = statusBarColor.isLight,
    navigationBarColor: Color = MaterialTheme.colors().primary,
    lightNavigationBar: Boolean = navigationBarColor.isLight
) = SystemBarStyle(
    statusBarColor = statusBarColor,
    lightStatusBar = lightStatusBar,
    navigationBarColor = navigationBarColor,
    lightNavigationBar = lightNavigationBar
)

@Composable
fun SurfaceSystemBarStyle(
    statusBarAlpha: Float = 0.4f,
    lightStatusBar: Boolean = MaterialTheme.colors().surface.isLight,
    navigationBarAlpha: Float = 0.4f,
    lightNavigationBar: Boolean =  MaterialTheme.colors().surface.isLight
) = SystemBarStyle(
    statusBarColor = MaterialTheme.colors().surface.copy(alpha = statusBarAlpha),
    lightStatusBar = lightStatusBar,
    navigationBarColor = MaterialTheme.colors().surface.copy(alpha = navigationBarAlpha),
    lightNavigationBar = lightNavigationBar
)

@Composable
fun TransparentSystemBarStyle(
    lightStatusBar: Boolean = false,
    lightNavigationBar: Boolean = false
) = SystemBarStyle(
    statusBarColor = Color.Transparent,
    lightStatusBar = lightStatusBar,
    navigationBarColor = Color.Transparent,
    lightNavigationBar = lightNavigationBar
)

@Composable
fun ProvideSystemBarStyle(
    value: SystemBarStyle,
    children: @Composable() () -> Unit
) {
    val systemBarManager = ambient(SystemBarManagerAmbient)
    onPreCommit(value) {
        systemBarManager.registerStyle(value)
        onDispose { systemBarManager.unregisterStyle(value) }
    }
    SystemBarStyleAmbient.Provider(
        value = value,
        children = children
    )
}

@Composable
fun ambientSystemBarStyle(): SystemBarStyle =
    ambient(SystemBarStyleAmbient)

@Composable
fun SystemBarManager(children: @Composable() () -> Unit) {
    val activity = ambient(ActivityAmbient)
    val systemBarManager = remember { SystemBarManager(activity) }
    SystemBarManagerAmbient.Provider(value = systemBarManager, children = children)
}

private val SystemBarStyleAmbient = Ambient.of { SystemBarStyle() }

internal class SystemBarManager(private val activity: Activity) {

    private val styles = modelListOf<SystemBarStyle>()
    var currentStyle: SystemBarStyle by framed(SystemBarStyle())

    init {
        activity.window.decorView.systemUiVisibility =
            activity.window.decorView.systemUiVisibility.addFlag(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            )
    }

    fun registerStyle(style: SystemBarStyle) {
        styles += style
        update()
    }

    fun unregisterStyle(style: SystemBarStyle) {
        styles -= style
        update()
    }

    private fun update() {
        currentStyle = styles.lastOrNull() ?: SystemBarStyle()

        d { "apply system bar style $currentStyle" }

        activity.window.statusBarColor = currentStyle.statusBarColor.toArgb()
        activity.window.decorView.systemUiVisibility =
            activity.window.decorView.systemUiVisibility.setFlag(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR,
                currentStyle.lightStatusBar
            )
        activity.window.navigationBarColor = currentStyle.navigationBarColor.toArgb()
        if (Build.VERSION.SDK_INT >= 26) {
            activity.window.decorView.systemUiVisibility =
                activity.window.decorView.systemUiVisibility.setFlag(
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR,
                    currentStyle.lightNavigationBar
                )
        }

        if (Build.VERSION.SDK_INT >= 28) {
            activity.window.navigationBarDividerColor = currentStyle.navigationBarDividerColor.toArgb()
        }
    }
}

internal val SystemBarManagerAmbient = Ambient.of<SystemBarManager>()
