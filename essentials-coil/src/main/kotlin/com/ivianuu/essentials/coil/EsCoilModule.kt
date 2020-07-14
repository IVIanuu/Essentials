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

package com.ivianuu.essentials.coil

import coil.CoilAccessor
import coil.ImageLoader
import coil.decode.Decoder
import com.ivianuu.essentials.app.applicationContext
import com.ivianuu.injekt.ApplicationComponent
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.Reader
import com.ivianuu.injekt.SetElements
import com.ivianuu.injekt.given

object EsCoilModule {

    @SetElements(ApplicationComponent::class)
    fun decoders() = emptySet<Decoder>()

    @SetElements(ApplicationComponent::class)
    fun fetchers() = emptySet<FetcherBinding<*>>()

    @SetElements(ApplicationComponent::class)
    fun mappers() = emptySet<MapperBinding<*>>()

    @SetElements(ApplicationComponent::class)
    fun measuredMappers() = emptySet<MeasuredMapperBinding<*>>()

    @Given(ApplicationComponent::class)
    @Reader
    fun imageLoader() = ImageLoader.Builder(applicationContext)
        .componentRegistry {
            given<Set<Decoder>>().forEach { add(it) }
            given<Set<FetcherBinding<*>>>()
                .forEach { binding ->
                    CoilAccessor.add(this, binding.type.java, binding.fetcher)
                }
            given<Set<MapperBinding<*>>>()
                .forEach { binding ->
                    CoilAccessor.add(this, binding.type.java, binding.mapper)
                }
            given<Set<MeasuredMapperBinding<*>>>()
                .forEach { binding ->
                    CoilAccessor.add(this, binding.type.java, binding.mapper)
                }
        }
        .build()

}
