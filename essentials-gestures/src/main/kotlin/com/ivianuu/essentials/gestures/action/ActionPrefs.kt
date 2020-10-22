/*
 * Copyright 2020 Manuel Wrage
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

package com.ivianuu.essentials.gestures.action

import com.ivianuu.essentials.datastore.DataStore
import com.ivianuu.essentials.datastore.DiskDataStoreFactory
import com.ivianuu.injekt.Assisted
import com.ivianuu.injekt.Binding
import com.ivianuu.injekt.FunBinding
import com.ivianuu.injekt.merge.ApplicationComponent

@FunBinding
suspend fun setActionMediaApp(
    pref: ActionMediaAppPref,
    value: @Assisted String
) {
    pref.updateData { value }
}

internal typealias ActionMediaAppPref = DataStore<String?>

@Binding(ApplicationComponent::class)
fun actionMediaAppPref(factory: DiskDataStoreFactory): ActionMediaAppPref =
    factory.create<String?>("action_media_app") { null }
