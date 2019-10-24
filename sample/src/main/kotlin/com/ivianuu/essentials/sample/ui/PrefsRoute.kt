package com.ivianuu.essentials.sample.ui

import androidx.compose.unaryPlus
import androidx.ui.core.Text
import com.ivianuu.essentials.ui.compose.composeControllerRoute
import com.ivianuu.essentials.ui.compose.injekt.inject
import com.ivianuu.essentials.ui.compose.material.EsTopAppBar
import com.ivianuu.essentials.ui.compose.prefs.CheckboxPreference
import com.ivianuu.essentials.ui.compose.prefs.Dependency
import com.ivianuu.essentials.ui.compose.prefs.MultiSelectListPreference
import com.ivianuu.essentials.ui.compose.prefs.PreferenceSubheader
import com.ivianuu.essentials.ui.compose.prefs.PrefsScreen
import com.ivianuu.essentials.ui.compose.prefs.RadioButtonPreference
import com.ivianuu.essentials.ui.compose.prefs.SingleItemListPreference
import com.ivianuu.essentials.ui.compose.prefs.SliderPreference
import com.ivianuu.essentials.ui.compose.prefs.SwitchPreference
import com.ivianuu.essentials.ui.compose.prefs.TextInputPreference
import com.ivianuu.essentials.ui.navigation.director.controllerRouteOptions
import com.ivianuu.essentials.ui.navigation.director.vertical
import com.ivianuu.kprefs.KPrefs
import com.ivianuu.kprefs.boolean
import com.ivianuu.kprefs.int
import com.ivianuu.kprefs.string
import com.ivianuu.kprefs.stringSet

val prefsRoute = composeControllerRoute(
    options = controllerRouteOptions().vertical()
) {
    PrefsScreen(
        appBar = { EsTopAppBar(title = "Prefs") },
        prefs = {
            val prefs = +inject<KPrefs>()

            SwitchPreference(
                pref = prefs.boolean("switch"),
                title = { Text("Switch") }
            )

            val dependencies = listOf(
                Dependency(
                    pref = prefs.boolean("switch"),
                    value = true
                )
            )

            PreferenceSubheader(text = "Category", dependencies = dependencies)

            CheckboxPreference(
                pref = prefs.boolean("checkbox"),
                title = { Text("Checkbox") },
                summary = { Text("This is a checkbox preference") },
                dependencies = dependencies
            )

            RadioButtonPreference(
                pref = prefs.boolean("radio_button"),
                title = { Text("Radio Button") },
                summary = { Text("This is a radio button preference") },
                dependencies = dependencies
            )

            SliderPreference(
                pref = prefs.int("slider"),
                title = { Text("Slider") },
                summary = { Text("This is a slider preference") },
                dependencies = dependencies
            )

            PreferenceSubheader(text = "Dialogs", dependencies = dependencies)

            TextInputPreference(
                pref = prefs.string("text_input"),
                title = { Text("Text input") },
                summary = { Text("This is a text input preference") },
                dependencies = dependencies
            )

            MultiSelectListPreference(
                pref = prefs.stringSet("multi_select_list", setOf("A", "B")),
                title = { Text("Multi select list") },
                summary = { Text("This is a multi select list preference") },
                entries = listOf(
                    MultiSelectListPreference.Item("A"),
                    MultiSelectListPreference.Item("B"),
                    MultiSelectListPreference.Item("C")
                ),
                dependencies = dependencies
            )

            SingleItemListPreference(
                pref = prefs.string("single_item_list", "C"),
                title = { Text("Single item list") },
                summary = { Text("This is a single item list preference") },
                entries = listOf(
                    SingleItemListPreference.Item("A"),
                    SingleItemListPreference.Item("B"),
                    SingleItemListPreference.Item("C")
                ),
                dependencies = dependencies
            )
        }
    )
}