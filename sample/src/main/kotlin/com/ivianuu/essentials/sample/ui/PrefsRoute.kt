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

import androidx.ui.graphics.Color
import com.ivianuu.essentials.store.prefs.PrefBoxFactory
import com.ivianuu.essentials.store.prefs.boolean
import com.ivianuu.essentials.store.prefs.color
import com.ivianuu.essentials.store.prefs.int
import com.ivianuu.essentials.store.prefs.string
import com.ivianuu.essentials.store.prefs.stringSet
import com.ivianuu.essentials.ui.injekt.inject
import com.ivianuu.essentials.ui.layout.ScrollableList
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.Route
import com.ivianuu.essentials.ui.prefs.CheckboxPreference
import com.ivianuu.essentials.ui.prefs.ClipboardPreference
import com.ivianuu.essentials.ui.prefs.ColorPreference
import com.ivianuu.essentials.ui.prefs.Dependency
import com.ivianuu.essentials.ui.prefs.MultiChoiceListPreference
import com.ivianuu.essentials.ui.prefs.PreferenceSubheader
import com.ivianuu.essentials.ui.prefs.RadioButtonPreference
import com.ivianuu.essentials.ui.prefs.SingleChoiceListPreference
import com.ivianuu.essentials.ui.prefs.SliderPreference
import com.ivianuu.essentials.ui.prefs.SwitchPreference
import com.ivianuu.essentials.ui.prefs.TextInputPreference

val PrefsRoute = Route {
    Scaffold(
        topAppBar = { TopAppBar(title = "Prefs") },
        body = {
            ScrollableList {
                val boxFactory = inject<PrefBoxFactory>()

                SwitchPreference(
                    box = boxFactory.boolean("switch"),
                    title = "Switch"
                )

                val dependencies = listOf(
                    Dependency(
                        box = boxFactory.boolean("switch"),
                        value = true
                    )
                )

                PreferenceSubheader(text = "Category", dependencies = dependencies)

                CheckboxPreference(
                    box = boxFactory.boolean("checkbox"),
                    dependencies = dependencies,
                    title = "Checkbox",
                    summary = "This is a checkbox preference"
                )

                RadioButtonPreference(
                    box = boxFactory.boolean("radio_button"),
                    dependencies = dependencies,
                    title = "Radio Button",
                    summary = "This is a radio button preference"
                )

                SliderPreference(
                    box = boxFactory.int("slider"),
                    dependencies = dependencies,
                    title = "Slider",
                    steps = 10,
                    valueRange = 0..100,
                    summary = "This is a slider preference"
                )

                PreferenceSubheader(text = "Dialogs", dependencies = dependencies)

                TextInputPreference(
                    box = boxFactory.string("text_input"),
                    dependencies = dependencies,
                    title = "Text input",
                    summary = "This is a text input preference",
                    allowEmpty = false
                )

                ColorPreference(
                    box = boxFactory.color("color", Color.Red),
                    dependencies = dependencies,
                    title = "Color",
                    summary = "This is a color preference"
                )

                MultiChoiceListPreference(
                    box = boxFactory.stringSet("multi_select_list", setOf("A", "B", "C")),
                    dependencies = dependencies,
                    title = "Multi select list",
                    summary = "This is a multi select list preference",
                    items = listOf(
                        MultiChoiceListPreference.Item("A", "A"),
                        MultiChoiceListPreference.Item("B", "B"),
                        MultiChoiceListPreference.Item("C", "C")
                    )
                )

                SingleChoiceListPreference(
                    box = boxFactory.string("single_item_list", "C"),
                    dependencies = dependencies,
                    title = "Single item list",
                    summary = "This is a single item list preference",
                    items = listOf(
                        SingleChoiceListPreference.Item("A", "A"),
                        SingleChoiceListPreference.Item("B", "B"),
                        SingleChoiceListPreference.Item("C", "C")
                    )
                )

                ClipboardPreference(
                    title = "Clipboard",
                    summary = "This is a clipboard preference",
                    clipboardText = { "cool clip" },
                    dependencies = dependencies
                )
            }
        }
    )
}