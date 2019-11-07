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

package com.ivianuu.essentials.securesettings

import androidx.lifecycle.lifecycleScope
import com.ivianuu.epoxyprefs.Preference
import com.ivianuu.essentials.ui.navigation.director.controllerRoute
import com.ivianuu.essentials.ui.navigation.director.defaultControllerRouteOptionsOrNull
import com.ivianuu.essentials.ui.prefs.PrefsController
import com.ivianuu.essentials.util.BuildInfo
import com.ivianuu.essentials.util.StringProvider
import com.ivianuu.essentials.util.Toaster
import com.ivianuu.injekt.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Asks the user for the secure settings permission
 */
val secureSettingsInstructionsRoute =
    controllerRoute<SecureSettingsPcInstructionsController>(
        options = defaultControllerRouteOptionsOrNull()
    )

@Inject
internal class SecureSettingsPcInstructionsController(
    private val buildInfo: BuildInfo,
    private val clipboardAccessor: ClipboardAccessor,
    private val secureSettingsHelper: SecureSettingsHelper,
    private val stringProvider: StringProvider,
    private val toaster: Toaster
) : PrefsController() {

    override val toolbarTitleRes: Int
        get() = R.string.es_title_secure_settings_pc_instructions

    override fun onCreate() {
        super.onCreate()

        lifecycleScope.launch {
            while (true) {
                if (secureSettingsHelper.canWriteSecureSettings()) {
                    navigator.pop()
                    break
                }
                delay(500)
            }
        }
    }

    override fun epoxyController() = epoxyController {
        Preference {
            key("secure_settings_header")
            summaryRes(R.string.es_pref_secure_settings_pc_instructions_header_summary)
        }

        Preference {
            key("secure_settings_step_1")
            titleRes(R.string.es_pref_secure_settings_step_1)
            summaryRes(R.string.es_pref_secure_settings_step_1_summary)
            isClickable(false)
        }

        Preference {
            key("secure_settings_step_two")
            titleRes(R.string.es_pref_secure_settings_step_2)
            summaryRes(R.string.es_pref_secure_settings_step_2_summary)
            isClickable(false)
        }

        Preference {
            key("secure_settings_step_3")
            titleRes(R.string.es_pref_secure_settings_step_3)
        }

        Preference {
            key("secure_settings_link_gadget_hacks")
            iconRes(R.drawable.es_ic_link)
            summaryRes(R.string.es_pref_secure_settings_link_gadget_hacks_summary)
            openUrlOnClick { "https://youtu.be/CDuxcrrWLnY" }
        }

        Preference {
            key("secure_settings_link_lifehacker")
            iconRes(R.drawable.es_ic_link)
            summaryRes(R.string.es_pref_secure_settings_link_lifehacker_summary)
            openUrlOnClick {
                "https://lifehacker.com/the-easiest-way-to-install-androids-adb-and-fastboot-to-1586992378"
            }
        }

        Preference {
            key("secure_settings_link_xda")
            iconRes(R.drawable.es_ic_link)
            summaryRes(R.string.es_pref_secure_settings_link_xda_summary)
            openUrlOnClick {
                "https://www.xda-developers.com/install-adb-windows-macos-linux/"
            }
        }

        Preference {
            key("secure_settings_step_4")
            titleRes(R.string.es_pref_secure_settings_step_4)
            summary(
                stringProvider.getString(
                    R.string.es_pref_secure_settings_step_4_summary,
                    buildInfo.packageName
                )
            )
            onClick {
                clipboardAccessor.clipboardText =
                    "adb shell pm grant ${buildInfo.packageName} android.permission.WRITE_SECURE_SETTINGS"

                toaster.toast(R.string.es_copied_to_clipboard)
                return@onClick true
            }
        }
    }

}