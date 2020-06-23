package com.ivianuu.essentials.util

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.onActive
import com.ivianuu.essentials.ui.common.registerActivityResultCallback
import com.ivianuu.essentials.ui.navigation.Navigator
import com.ivianuu.essentials.ui.navigation.Route
import com.ivianuu.injekt.Transient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Transient
class StartActivityForResult(
    private val navigator: Navigator,
    private val startUi: StartUi
) {

    suspend operator fun invoke(intent: Intent): ActivityResult =
        invoke(ActivityResultContracts.StartActivityForResult(), intent)

    suspend operator fun <I, O> invoke(
        contract: ActivityResultContract<I, O>,
        input: I
    ): O {
        startUi()
        return suspendCancellableCoroutine { continuation ->
            navigator.push(
                Route(opaque = true) {
                    val launcher = registerActivityResultCallback(
                        contract,
                        ActivityResultCallback {
                            navigator.popTop()
                            continuation.resume(it)
                        }
                    )

                    onActive { launcher.launch(input) }
                }
            )
            continuation.invokeOnCancellation { navigator.popTop() }
        }
    }

}
