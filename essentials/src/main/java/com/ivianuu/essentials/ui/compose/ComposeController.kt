package com.ivianuu.essentials.ui.compose

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.ViewComposition
import androidx.compose.composer
import androidx.compose.disposeComposition
import androidx.compose.setViewContent
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Density
import androidx.ui.core.DensityAmbient
import com.ivianuu.director.requireActivity
import com.ivianuu.essentials.ui.base.EsController
import com.ivianuu.essentials.util.cast

abstract class ComposeController : EsController() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return FrameLayout(requireActivity()).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }.also { setContentView(it) }
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        view.cast<ViewGroup>().setViewContent {
            with(composer) {
                wrapWithDefaults {
                    build()
                }
            }
        }
    }

    override fun onDestroyView(view: View) {
        view.cast<ViewGroup>().disposeComposition()
        super.onDestroyView(view)
    }

    protected abstract fun ViewComposition.build()

    protected open fun ViewComposition.wrapWithDefaults(children: ViewComposition.() -> Unit) {
        ContextAmbient.Provider(requireActivity()) {
            DensityAmbient.Provider(Density(requireActivity())) {
                children()
            }
        }
    }

}
