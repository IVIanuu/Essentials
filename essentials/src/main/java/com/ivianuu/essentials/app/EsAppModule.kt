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

package com.ivianuu.essentials.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ivianuu.essentials.util.EsPreferenceManager
import com.ivianuu.injekt.*
import com.ivianuu.kprefs.KPrefs
import com.ivianuu.ksettings.KSettings

fun Module.appContext() = get<Context>(APP_CONTEXT)

const val APP_CONTEXT = "appContext"

/**
 * Basic app dependencies such as preferences or package manager
 */
inline fun <reified T : EsApp> esAppModule(esApp: T) = module(name = "EsAppModule") {
    // app
    factory { esApp } bind Application::class
    factory(name = APP_CONTEXT) { esApp as Context }

    // prefs
    factory { EsPreferenceManager.getDefaultSharedPreferences(appContext()) }
    single { KPrefs(get<SharedPreferences>()) }

    single { KSettings(appContext()) }

    factory { appContext().packageManager!! }
}