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

import com.ivianuu.essentials.injection.PerFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoSet

/**
 * @author Manuel Wrage (IVIanuu)
 */
@Module
abstract class EssentialsAppModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindAppPickerDialog(): AppPickerDialog

    @Binds
    @IntoSet
    abstract fun bindAppGlideInitializer(appGlideInitializer: AppGlideInitializer): AppService
}