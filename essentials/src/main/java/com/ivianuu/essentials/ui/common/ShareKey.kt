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

package com.ivianuu.essentials.ui.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ShareCompat
import com.ivianuu.traveler.keys.ActivityKey

/**
 * Shares the [text]
 */
data class ShareKey(val text: String) : ActivityKey() {
    override fun createIntent(context: Context): Intent {
        return ShareCompat.IntentBuilder
            .from(context as Activity)
            .setType("text/plain")
            .setText(text)
            .createChooserIntent()
    }
}