package com.ivianuu.essentials.ui.common

import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.Composable
import androidx.compose.currentComposer
import androidx.compose.key
import androidx.compose.onDispose
import androidx.compose.remember

@Composable
fun <I, O> registerActivityResultCallback(
    contract: ActivityResultContract<I, O>,
    callback: ActivityResultCallback<O>
): ActivityResultLauncher<I> {
    val key = currentComposer.currentCompoundKeyHash

    val registry = compositionActivity.activityResultRegistry

    val launcher = remember(contract, callback, registry) {
        registry.register(
            key.toString(),
            contract,
            callback
        )
    }

    key(launcher) {
        onDispose {
            launcher.unregister()
        }
    }

    return launcher
}