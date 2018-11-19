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
import android.content.pm.PackageManager
import com.ivianuu.essentials.util.EssentialsPreferenceManager
import com.ivianuu.kprefs.KPrefs
import com.ivianuu.ksettings.KSettings
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Essentials app module
 */
@Module
abstract class BaseAppModule<T : BaseApp> {

    @Binds
    abstract fun bindBaseApp(t: T): BaseApp

    @Binds
    abstract fun bindApplication(baseApp: BaseApp): Application

    @Binds
    abstract fun bindContext(app: Application): Context

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideSharedPrefs(context: Context): SharedPreferences =
            EssentialsPreferenceManager.getDefaultSharedPreferences(context)

        @JvmStatic
        @Singleton
        @Provides
        fun provideKSharedPrefs(prefs: SharedPreferences) =
            KPrefs(prefs)

        @JvmStatic
        @Singleton
        @Provides
        fun provideKSystemSettings(context: Context) =
            KSettings(context)

        @JvmStatic
        @Provides
        fun providePackageManager(context: Context): PackageManager = context.packageManager

    }

}