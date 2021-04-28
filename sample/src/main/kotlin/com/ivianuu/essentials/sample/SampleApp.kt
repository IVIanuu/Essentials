@file:Suppress("UnusedImport")

package com.ivianuu.essentials.sample

import com.ivianuu.essentials.app.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.android.*
import com.ivianuu.injekt.scope.*

@GivenImports(
    "com.ivianuu.essentials.*",
    "com.ivianuu.essentials.about.*",
    "com.ivianuu.essentials.accessibility.*",
    "com.ivianuu.essentials.android.settings.*",
    "com.ivianuu.essentials.app.*",
    "com.ivianuu.essentials.apps.*",
    "com.ivianuu.essentials.apps.coil.*",
    "com.ivianuu.essentials.apps.ui.*",
    "com.ivianuu.essentials.apps.ui.apppicker.*",
    "com.ivianuu.essentials.apps.ui.checkableapps.*",
    "com.ivianuu.essentials.backup.*",
    "com.ivianuu.essentials.boot.*",
    "com.ivianuu.essentials.broadcast.*",
    "com.ivianuu.essentials.clipboard.*",
    "com.ivianuu.essentials.coil.*",
    "com.ivianuu.essentials.colorpicker.*",
    "com.ivianuu.essentials.data.*",
    "com.ivianuu.essentials.donation.*",
    "com.ivianuu.essentials.foreground.*",
    "com.ivianuu.essentials.gestures.action.*",
    "com.ivianuu.essentials.gestures.action.actions.*",
    "com.ivianuu.essentials.gestures.action.ui.picker.*",
    "com.ivianuu.essentials.hidenavbar.*",
    "com.ivianuu.essentials.hidenavbar.ui.*",
    "com.ivianuu.essentials.license.domain.*",
    "com.ivianuu.essentials.license.ui.*",
    "com.ivianuu.essentials.logging.*",
    "com.ivianuu.essentials.logging.android.*",
    "com.ivianuu.essentials.notificationlistener.*",
    "com.ivianuu.essentials.permission.*",
    "com.ivianuu.essentials.permission.accessibility.*",
    "com.ivianuu.essentials.permission.deviceadmin.*",
    "com.ivianuu.essentials.permission.ignorebatteryoptimizations.*",
    "com.ivianuu.essentials.permission.installunknownapps.*",
    "com.ivianuu.essentials.permission.intent.*",
    "com.ivianuu.essentials.permission.notificationlistener.*",
    "com.ivianuu.essentials.permission.packageusagestats.*",
    "com.ivianuu.essentials.permission.root.*",
    "com.ivianuu.essentials.permission.runtime.*",
    "com.ivianuu.essentials.permission.systemoverlay.*",
    "com.ivianuu.essentials.permission.ui.*",
    "com.ivianuu.essentials.permission.writesecuresettings.*",
    "com.ivianuu.essentials.permission.writesettings.*",
    "com.ivianuu.essentials.processrestart.*",
    "com.ivianuu.essentials.rate.data.*",
    "com.ivianuu.essentials.rate.domain.*",
    "com.ivianuu.essentials.rate.ui.*",
    "com.ivianuu.essentials.recentapps.*",
    "com.ivianuu.essentials.sample.tile.*",
    "com.ivianuu.essentials.sample.ui.*",
    "com.ivianuu.essentials.sample.work.*",
    "com.ivianuu.essentials.screenstate.*",
    "com.ivianuu.essentials.serialization.*",
    "com.ivianuu.essentials.shell.*",
    "com.ivianuu.essentials.shortcutpicker.*",
    "com.ivianuu.essentials.systemoverlay.blacklist.*",
    "com.ivianuu.essentials.systemoverlay.*",
    "com.ivianuu.essentials.tile.*",
    "com.ivianuu.essentials.time.*",
    "com.ivianuu.essentials.torch.*",
    "com.ivianuu.essentials.twilight.data.*",
    "com.ivianuu.essentials.twilight.domain.*",
    "com.ivianuu.essentials.twilight.ui.*",
    "com.ivianuu.essentials.ui.*",
    "com.ivianuu.essentials.ui.animation.*",
    "com.ivianuu.essentials.ui.core.*",
    "com.ivianuu.essentials.ui.dialog.*",
    "com.ivianuu.essentials.ui.material.*",
    "com.ivianuu.essentials.ui.navigation.*",
    "com.ivianuu.essentials.ui.popup.*",
    "com.ivianuu.essentials.unlock.*",
    "com.ivianuu.essentials.util.*",
    "com.ivianuu.essentials.web.ui.*",
    "com.ivianuu.essentials.work.*",
    "com.ivianuu.injekt.android.*",
    "com.ivianuu.injekt.android.work.*",
    "com.ivianuu.injekt.common.*",
    "com.ivianuu.injekt.coroutines.*",
    "com.ivianuu.injekt.scope.*"
)
class SampleApp : EsApp() {
    fun aha() {
    }
    override fun buildAppGivenScope(): AppGivenScope = createAppGivenScope()
}
