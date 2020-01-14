package com.ivianuu.essentials.gestures.action.actions

import com.ivianuu.essentials.gestures.R
import com.ivianuu.essentials.gestures.action.ActionExecutor
import com.ivianuu.essentials.gestures.action.ActionIcon
import com.ivianuu.essentials.gestures.action.ActionIconProvider
import com.ivianuu.essentials.gestures.action.bindAction
import com.ivianuu.essentials.torch.TorchManager
import com.ivianuu.essentials.util.ResourceProvider
import com.ivianuu.injekt.Factory
import com.ivianuu.injekt.Module
import com.ivianuu.injekt.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal val EsTorchActionModule = Module {
    // todo check if device has a torch packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    bindAction(
        key = "torch",
        title = { getStringResource(R.string.es_action_torch) },
        iconProvider = { get<TorchActionIconProvider>() },
        executor = { get<TorchActionExecutor>() }
    )
}

@Factory
internal class TorchActionExecutor(
    private val torchManager: TorchManager
) : ActionExecutor {
    override suspend fun invoke() {
        torchManager.toggleTorch()
    }
}

@Factory
internal class TorchActionIconProvider(
    private val resourceProvider: ResourceProvider,
    private val torchManager: TorchManager
) : ActionIconProvider {
    override val icon: Flow<ActionIcon>
        get() = torchManager.torchState
            .map {
                if (it) R.drawable.es_ic_torch_on else R.drawable.es_ic_torch_off
            }
            .map { resourceProvider.getDrawable(it) }
            .map { ActionIcon(it) }
}