/*
 * Copyright 2018 Manuel Wrage
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

package com.ivianuu.essentials.hidenavbar

import com.ivianuu.kprefs.KPrefs

/**
 * @author Manuel Wrage (IVIanuu)
 */
class NavBarPrefs(prefs: KPrefs) {
    val manageNavBar = prefs.boolean("manage_nav_bar")
    val fullOverscan = prefs.boolean("full_overscan")
    val navBarHidden = prefs.boolean("nav_bar_hidden")
    val rot270Fix = prefs.boolean("rot270_fix")
    val showNavBarScreenOff = prefs.boolean("show_nav_bar_screen_off", true)
    val tabletMode = prefs.boolean("tablet_mode")

    internal val wasNavBarHidden = prefs.boolean("was_nav_bar_hidden")
}