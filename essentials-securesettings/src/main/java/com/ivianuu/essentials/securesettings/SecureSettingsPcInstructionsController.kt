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

package com.ivianuu.essentials.securesettings

import com.ivianuu.director.activity
import com.ivianuu.epoxyprefs.Preference
import com.ivianuu.essentials.ui.prefs.PrefsController
import com.ivianuu.essentials.ui.traveler.key.ControllerKey
import com.ivianuu.essentials.ui.traveler.key.UrlKey
import com.ivianuu.essentials.util.Toaster
import com.ivianuu.essentials.util.string
import com.ivianuu.injekt.inject
import com.ivianuu.traveler.navigate
import kotlinx.android.parcel.Parcelize

@Parcelize
class SecureSettingsPcInstructionsKey : ControllerKey(::SecureSettingsPcInstructionsController)

/**
 * Asks the user for the secure settings permission
 */
class SecureSettingsPcInstructionsController : PrefsController() {

    private val clipboardHelper by inject<ClipboardAccessor>()
    private val toaster by inject<Toaster>()

    override val toolbarTitleRes: Int
        get() = R.string.es_title_secure_settings_pc_instructions

    override fun epoxyController() = epoxyController {
        Preference {
            key("secure_settings_header")
            summaryRes(R.string.es_pref_summary_secure_settings_pc_instructions_header)
        }

        Preference {
            key("secure_settings_step_1")
            titleRes(R.string.es_pref_title_secure_settings_step_1)
            summaryRes(R.string.es_pref_summary_secure_settings_step_1)
            isClickable(false)
        }

        Preference {
            key("secure_settings_step_two")
            titleRes(R.string.es_pref_title_secure_settings_step_2)
            summaryRes(R.string.es_pref_summary_secure_settings_step_2)
            isClickable(false)
        }

        Preference {
            key("secure_settings_step_3")
            titleRes(R.string.es_pref_title_secure_settings_step_3)
        }

        Preference {
            key("secure_settings_link_gadget_hacks")
            iconRes(R.drawable.es_ic_link)
            summaryRes(R.string.es_pref_summary_secure_settings_link_gadget_hacks)
            onClick {
                travelerRouter.navigate(UrlKey("https://youtu.be/CDuxcrrWLnY"))
                return@onClick true
            }
        }

        Preference {
            key("secure_settings_link_lifehacker")
            iconRes(R.drawable.es_ic_link)
            summaryRes(R.string.es_pref_summary_secure_settings_link_lifehacker)
            onClick {
                travelerRouter.navigate(UrlKey("https://lifehacker.com/the-easiest-way-to-install-androids-adb-and-fastboot-to-1586992378"))
                return@onClick true
            }
        }

        Preference {
            key("secure_settings_link_xda")
            iconRes(R.drawable.es_ic_link)
            summaryRes(R.string.es_pref_summary_secure_settings_link_xda)
            onClick {
                travelerRouter.navigate(UrlKey("https://www.xda-developers.com/install-adb-windows-macos-linux/"))
                return@onClick true
            }
        }

        Preference {
            key("secure_settings_step_4")
            titleRes(R.string.es_pref_title_secure_settings_step_4)
            summary(string(R.string.es_pref_summary_secure_settings_step_4, activity.packageName))
            onClick {
                clipboardHelper.clipboardText =
                    "adb shell pm grant ${activity.packageName} android.permission.WRITE_SECURE_SETTINGS"

                toaster.toast(R.string.es_msg_secure_settings_copied_to_clipboard)
                return@onClick true
            }
        }
    }

}