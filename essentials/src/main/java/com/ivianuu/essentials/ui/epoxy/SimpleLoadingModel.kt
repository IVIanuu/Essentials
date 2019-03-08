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

package com.ivianuu.essentials.ui.epoxy

import android.content.res.ColorStateList
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.ivianuu.essentials.R2
import com.ivianuu.essentials.util.color
import kotlinx.android.synthetic.main.es_item_simple_loading.es_progress_bar

/**
 * Simple loading model
 */
@EpoxyModelClass(layout = R2.layout.es_item_simple_loading)
abstract class SimpleLoadingModel : SimpleEpoxyModel() {

    @EpoxyAttribute var progressColor = 0
    @EpoxyAttribute var progressColorRes = 0

    override fun bind(holder: EsEpoxyHolder) {
        super.bind(holder)
        if (progressColor != 0 || progressColorRes != 0) {
            val progressColor = when {
                progressColor != 0 -> progressColor
                progressColorRes != 0 -> color(progressColorRes)
                else -> 0
            }

            val colorStateList = ColorStateList.valueOf(progressColor)

            with(holder.es_progress_bar) {
                progressBackgroundTintList = colorStateList
                secondaryProgressTintList = colorStateList
                indeterminateTintList = colorStateList
            }
        }
    }

}