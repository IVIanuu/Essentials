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

package com.ivianuu.essentials.ui.compose

import android.os.Bundle
import androidx.compose.Composable
import androidx.ui.core.setContent
import com.ivianuu.essentials.ui.base.EsActivity
import com.ivianuu.essentials.ui.compose.navigation.Navigator
import com.ivianuu.essentials.ui.compose.navigation.Route

abstract class ComposeActivity : EsActivity() {

    protected abstract val startComposeRoute: Route

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { compose() }
    }

    override fun onDestroy() {
        super.onDestroy()
        setContent { }
    }

    @Composable
    protected open fun compose() {
        Navigator { startComposeRoute }
    }
}