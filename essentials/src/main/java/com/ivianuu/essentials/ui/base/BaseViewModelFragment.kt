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

package com.ivianuu.essentials.ui.base

import android.arch.lifecycle.ViewModel
import com.ivianuu.essentials.util.DaggerViewModelFactory
import com.ivianuu.essentials.util.ext.unsafeLazy
import com.ivianuu.essentials.util.ext.viewModel
import javax.inject.Inject

/**
 * Essentials view model fragment
 */
abstract class BaseViewModelFragment<VM : ViewModel> : BaseFragment() {
    @Inject lateinit var viewModelFactory: DaggerViewModelFactory<VM>
    protected val viewModel: VM by unsafeLazy { viewModel<ViewModel>(viewModelFactory) as VM }
}