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

package com.ivianuu.essentials.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.remember
import androidx.lifecycle.ViewModel
import androidx.ui.core.ContextAmbient
import androidx.ui.material.MaterialTheme
import com.ivianuu.essentials.ui.compose.es.ComposeActivity
import com.ivianuu.essentials.ui.compose.injekt.MaterialThemeProvider
import com.ivianuu.essentials.util.injectViewModel
import com.ivianuu.injekt.Factory
import com.ivianuu.injekt.inject

class PermissionActivity : ComposeActivity() {

    private val manager: PermissionManager by inject()
    private val requestUi: PermissionRequestUi by inject()
    private val viewModel: CallbackViewModel by injectViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestId = intent.getStringExtra(KEY_REQUEST_ID)
        if (requestId == null) {
            finish()
            return
        }

        val request = manager.getRequest(requestId)
        if (request == null) {
            finish()
            return
        }

        viewModel.currentActivity = this

        if (savedInstanceState != null) return

        viewModel.request = request

        val finalRequest = request.copy(
            onComplete = viewModel.onComplete
        )

        requestUi.performRequest(this@PermissionActivity, manager, finalRequest)
    }

    override fun wrapContent(content: () -> Unit) {
        ContextAmbient.Provider(
            value = remember { ContextThemeWrapper(this, applicationInfo.theme) },
            children = {
                val materialThemeProvider =
                    com.ivianuu.essentials.ui.compose.injekt.inject<MaterialThemeProvider>()
                MaterialTheme(
                    colors = materialThemeProvider.colors(),
                    typography = materialThemeProvider.typography(),
                    children = content
                )
            }
        )
    }

    override fun onDestroy() {
        viewModel.currentActivity = null
        super.onDestroy()
    }

    internal companion object {
        private const val KEY_REQUEST_ID = "request_id"

        fun request(
            context: Context,
            requestId: String
        ) {
            context.startActivity(
                Intent(
                    context,
                    PermissionActivity::class.java
                ).apply {
                    putExtra(KEY_REQUEST_ID, requestId)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        }
    }
}

@Factory
internal class CallbackViewModel : ViewModel() {
    var currentActivity: Activity? = null
    lateinit var request: PermissionRequest
    val onComplete: () -> Unit = {
        currentActivity?.finish()
        request.onComplete()
    }
}
