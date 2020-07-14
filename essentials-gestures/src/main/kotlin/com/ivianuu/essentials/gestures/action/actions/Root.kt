package com.ivianuu.essentials.gestures.action.actions

import com.ivianuu.essentials.gestures.R
import com.ivianuu.essentials.gestures.action.ActionExecutor
import com.ivianuu.essentials.shell.Shell
import com.ivianuu.essentials.util.Toaster
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.Reader

@Reader
@Given
class RootActionExecutor(
    private val command: String
) : ActionExecutor {
    override suspend fun invoke() {
        try {
            Shell.run(command)
        } catch (e: Exception) {
            e.printStackTrace()
            Toaster.toast(R.string.es_no_root)
        }
    }
}
