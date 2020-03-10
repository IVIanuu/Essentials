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

package com.ivianuu.essentials.tile

import android.annotation.TargetApi
import android.service.quicksettings.TileService
import com.ivianuu.essentials.util.ComponentBuilderInterceptor
import com.ivianuu.essentials.util.unsafeLazy
import com.ivianuu.injekt.ComponentOwner
import com.ivianuu.injekt.android.ServiceComponent
import com.ivianuu.scopes.MutableScope
import com.ivianuu.scopes.ReusableScope
import com.ivianuu.scopes.Scope

/**
 * Base tile service
 */
@TargetApi(24)
abstract class EsTileService : TileService(), ComponentOwner,
    ComponentBuilderInterceptor {

    override val component by unsafeLazy {
        ServiceComponent(this) {
            buildComponent()
        }
    }

    private val _scope = MutableScope()
    val scope: Scope get() = _scope

    private val _listeningScope = ReusableScope()
    val listeningScope: Scope get() = _listeningScope

    override fun onDestroy() {
        _scope.close()
        super.onDestroy()
    }

    override fun onStopListening() {
        _listeningScope.clear()
        super.onStopListening()
    }
}
