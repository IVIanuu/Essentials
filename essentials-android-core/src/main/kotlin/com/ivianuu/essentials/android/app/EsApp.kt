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

package com.ivianuu.essentials.android.app

import android.app.Application
import android.content.pm.ApplicationInfo
import com.github.ajalt.timberkt.d
import com.ivianuu.essentials.android.data.EsDataModule
import com.ivianuu.essentials.android.ui.core.EsUiInitializerModule
import com.ivianuu.essentials.android.util.EsAndroidUtilModule
import com.ivianuu.essentials.app.AppComponentHolder
import com.ivianuu.essentials.app.AppInitializer
import com.ivianuu.essentials.app.AppInitializers
import com.ivianuu.essentials.app.AppService
import com.ivianuu.essentials.app.AppServices
import com.ivianuu.essentials.app.ApplicationScope
import com.ivianuu.essentials.app.EsAppInitializerModule
import com.ivianuu.essentials.app.EsAppServiceModule
import com.ivianuu.essentials.util.ComponentBuilderInterceptor
import com.ivianuu.essentials.util.EsUtilModule
import com.ivianuu.essentials.util.containsFlag
import com.ivianuu.injekt.Component
import com.ivianuu.injekt.ComponentOwner
import com.ivianuu.injekt.InjektPlugins
import com.ivianuu.injekt.Provider
import com.ivianuu.injekt.android.AndroidLogger
import com.ivianuu.injekt.android.ApplicationComponent
import com.ivianuu.injekt.android.SystemServiceModule
import com.ivianuu.injekt.get
import com.ivianuu.injekt.getLazy

/**
 * App
 */
abstract class EsApp : Application(), ComponentOwner,
    ComponentBuilderInterceptor {

    override val component: Component
        get() {
            if (!AppComponentHolder.isInitialized) {
                configureInjekt()
                initializeComponent()
            }

            return AppComponentHolder.component
        }

    private val appServices: Map<String, Provider<AppService>> by getLazy(qualifier = AppServices)

    override fun onCreate() {
        super.onCreate()
        invokeInitializers()
        startAppServices()
    }

    protected open fun configureInjekt() {
        if (applicationInfo.flags.containsFlag(ApplicationInfo.FLAG_DEBUGGABLE)) {
            InjektPlugins.logger = AndroidLogger()
        }
    }

    protected open fun initializeComponent() {
        AppComponentHolder.init(
            ApplicationComponent(this) {
                scopes(ApplicationScope)

                modules(
                    EsAndroidUtilModule,
                    EsAppModule,
                    EsAppInitializerModule,
                    EsAppServiceModule,
                    EsDataModule,
                    SystemServiceModule(),
                    EsUiInitializerModule,
                    EsUtilModule
                )
                buildComponent()
            }
        )
    }

    protected open fun invokeInitializers() {
        get<Map<String, Provider<AppInitializer>>>(qualifier = AppInitializers)
            .forEach {
                if (applicationInfo.flags.containsFlag(ApplicationInfo.FLAG_DEBUGGABLE)) {
                    println("initialize ${it.key}")
                }
                it.value()
            }
    }

    protected open fun startAppServices() {
        appServices
            .forEach {
                d { "start service ${it.key}" }
                it.value()
            }
    }
}