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

package com.ivianuu.essentials.injection

import com.ivianuu.essentials.app.EsAppInitializersModule
import com.ivianuu.essentials.app.EsAppServicesModule
import com.ivianuu.essentials.ui.compose.injekt.EsComposeModule
import com.ivianuu.essentials.util.EsStoreModule
import com.ivianuu.essentials.util.EsUtilModule
import com.ivianuu.injekt.Module
import com.ivianuu.injekt.android.SystemServiceModule

/**
 * Core modules
 */
val EsModule = Module {
    include(EsComposeModule)
    include(EsAppInitializersModule)
    include(EsAppServicesModule)
    include(EsStoreModule)
    include(EsUtilModule)
    include(SystemServiceModule)
}
