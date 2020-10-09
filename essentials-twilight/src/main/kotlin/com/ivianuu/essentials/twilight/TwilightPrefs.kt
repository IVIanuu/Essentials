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

package com.ivianuu.essentials.twilight

import com.ivianuu.essentials.datastore.DataStore
import com.ivianuu.essentials.datastore.DiskDataStoreFactory
import com.ivianuu.injekt.Binding
import com.ivianuu.injekt.merge.ApplicationComponent

typealias TwilightModePref = DataStore<TwilightMode>

@Binding(ApplicationComponent::class)
fun twilightModePref(factory: DiskDataStoreFactory): TwilightModePref =
    factory.create("twilight_mode") { TwilightMode.System }

typealias UseBlackInDarkModePref = DataStore<Boolean>

@Binding(ApplicationComponent::class)
fun useBlackInDarkModePref(factory: DiskDataStoreFactory): UseBlackInDarkModePref =
    factory.create("use_black") { false }