
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

package com.ivianuu.essentials.sample.ui

import android.os.Bundle
import com.ivianuu.essentials.app.AppInfo
import com.ivianuu.essentials.sample.ui.counter.CounterKey
import com.ivianuu.essentials.ui.base.BaseActivity
import com.ivianuu.essentials.ui.base.BaseActivityModule
import com.ivianuu.essentials.util.ext.results
import com.ivianuu.scopes.archlifecycle.onDestroy
import com.ivianuu.scopes.rx.disposeBy
import com.ivianuu.timberktx.d
import dagger.Module

class MainActivity : BaseActivity() {

    override val startKey: Any? get() = CounterKey(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        travelerRouter.results<AppInfo>(1)
            .subscribe { d { "app picked $it" } }
            .disposeBy(onDestroy)
    }

}

@Module
abstract class MainActivityModule : BaseActivityModule<MainActivity>()