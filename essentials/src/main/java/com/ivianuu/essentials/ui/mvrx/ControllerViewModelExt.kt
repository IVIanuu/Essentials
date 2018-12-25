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

package com.ivianuu.essentials.ui.mvrx

import androidx.lifecycle.ViewModelStoreOwner
import com.ivianuu.essentials.ui.base.EsController
import com.ivianuu.essentials.util.ext.requireParentController
import com.ivianuu.essentials.util.ext.requireTargetController
import com.ivianuu.kommon.lifecycle.defaultViewModelKey

inline fun <reified VM : MvRxViewModel<*>> EsController.activityViewModel(
    crossinline keyProvider: () -> String = { VM::class.defaultViewModelKey },
    noinline factory: () -> VM
) = viewModelLazy { getActivityViewModel(keyProvider(), factory) }

inline fun <reified VM : MvRxViewModel<*>> EsController.existingActivityViewModel(
    crossinline keyProvider: () -> String = { VM::class.defaultViewModelKey }
) = viewModelLazy {
    getExistingActivityViewModel<VM>(keyProvider())
}

inline fun <reified VM : MvRxViewModel<*>> EsController.parentViewModel(
    crossinline keyProvider: () -> String = { VM::class.defaultViewModelKey },
    noinline factory: () -> VM
) = viewModelLazy {
    getParentViewModel(keyProvider(), factory)
}

inline fun <reified VM : MvRxViewModel<*>> EsController.existingParentViewModel(
    crossinline keyProvider: () -> String = { VM::class.defaultViewModelKey }
) = viewModelLazy {
    getExistingParentViewModel<VM>(keyProvider())
}

inline fun <reified VM : MvRxViewModel<*>> EsController.targetViewModel(
    crossinline keyProvider: () -> String = { VM::class.defaultViewModelKey },
    noinline factory: () -> VM
) = viewModelLazy {
    getTargetViewModel(keyProvider(), factory)
}

inline fun <reified VM : MvRxViewModel<*>> EsController.existingTargetViewModel(
    crossinline keyProvider: () -> String = { VM::class.defaultViewModelKey }
) = viewModelLazy {
    getExistingTargetViewModel<VM>(keyProvider())
}

inline fun <reified VM : MvRxViewModel<*>> EsController.getActivityViewModel(
    key: String = VM::class.defaultViewModelKey,
    noinline factory: () -> VM
) = activity.viewModelProvider(factory)
    .get(key, VM::class.java).setupViewModel(this)

inline fun <reified VM : MvRxViewModel<*>> EsController.getExistingActivityViewModel(
    key: String = VM::class.defaultViewModelKey
) = activity.viewModelProvider<VM>(ExistingViewModelFactory())
    .get(key, VM::class.java)
    .setupViewModel(this)

inline fun <reified VM : MvRxViewModel<*>> EsController.getParentViewModel(
    key: String = VM::class.defaultViewModelKey,
    noinline factory: () -> VM
) = viewModelLazy {
    (requireParentController() as ViewModelStoreOwner).viewModelProvider(factory).get(
        key,
        VM::class.java
    ).setupViewModel(this)
}

inline fun <reified VM : MvRxViewModel<*>> EsController.getExistingParentViewModel(
    key: String = VM::class.defaultViewModelKey
) = viewModelLazy {
    (requireParentController() as ViewModelStoreOwner).viewModelProvider<VM>(
        ExistingViewModelFactory()
    ).get(
        key,
        VM::class.java
    ).setupViewModel(this)
}

inline fun <reified VM : MvRxViewModel<*>> EsController.getTargetViewModel(
    key: String = VM::class.defaultViewModelKey,
    noinline factory: () -> VM
) = viewModelLazy {
    (requireTargetController() as ViewModelStoreOwner).viewModelProvider(factory).get(
        key,
        VM::class.java
    ).setupViewModel(this)
}

inline fun <reified VM : MvRxViewModel<*>> EsController.getExistingTargetViewModel(
    key: String = VM::class.defaultViewModelKey
) = viewModelLazy {
    (requireTargetController() as ViewModelStoreOwner).viewModelProvider<VM>(
        ExistingViewModelFactory()
    ).get(
        key,
        VM::class.java
    ).setupViewModel(this)
}