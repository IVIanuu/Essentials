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

package com.ivianuu.essentials.sample.injection

import com.ivianuu.essentials.sample.data.MyOtherWorker
import com.ivianuu.essentials.sample.data.MyWorker
import com.ivianuu.essentials.work.InjectWorkerFactory
import com.ivianuu.essentials.work.WorkerKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Manuel Wrage (IVIanuu)
 */
@Module
abstract class WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(MyWorker::class)
    abstract fun bindMyWorker(factory: MyWorker.Factory): InjectWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(MyOtherWorker::class)
    abstract fun bindMyOtherWorker(factory: MyOtherWorker.Factory): InjectWorkerFactory

}