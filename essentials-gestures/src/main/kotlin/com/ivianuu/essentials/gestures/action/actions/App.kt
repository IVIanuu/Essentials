package com.ivianuu.essentials.gestures.action.actions

import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.Composable
import androidx.ui.foundation.Icon
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Apps
import com.ivianuu.essentials.apps.AppInfo
import com.ivianuu.essentials.apps.coil.AppIcon
import com.ivianuu.essentials.apps.getAppInfo
import com.ivianuu.essentials.apps.ui.AppPickerPage
import com.ivianuu.essentials.apps.ui.LaunchableAppFilter
import com.ivianuu.essentials.gestures.R
import com.ivianuu.essentials.gestures.action.Action
import com.ivianuu.essentials.gestures.action.ActionExecutor
import com.ivianuu.essentials.gestures.action.ActionFactory
import com.ivianuu.essentials.gestures.action.ActionIconProvider
import com.ivianuu.essentials.gestures.action.ActionPickerDelegate
import com.ivianuu.essentials.gestures.action.actionFactory
import com.ivianuu.essentials.gestures.action.actionPickerDelegate
import com.ivianuu.essentials.gestures.action.ui.picker.ActionPickerResult
import com.ivianuu.essentials.ui.navigation.Navigator
import com.ivianuu.essentials.util.Resources
import com.ivianuu.essentials.util.Toaster
import com.ivianuu.injekt.ApplicationComponent
import com.ivianuu.injekt.Assisted
import com.ivianuu.injekt.Module
import com.ivianuu.injekt.Provider
import com.ivianuu.injekt.Reader
import com.ivianuu.injekt.Unscoped
import com.ivianuu.injekt.composition.installIn
import com.ivianuu.injekt.get
import kotlinx.coroutines.flow.Flow

@Module
fun AppActionModule() {
    installIn<ApplicationComponent>()
    actionFactory<AppActionFactory>()
    actionPickerDelegate<AppActionPickerDelegate>()
}

@Reader
@Unscoped
internal class AppActionExecutor(
    private val packageName: @Assisted String,
    private val packageManager: PackageManager,
    private val delegateProvider: @Provider (Intent) -> IntentActionExecutor
) : ActionExecutor {
    override suspend fun invoke() {
        try {
            delegateProvider(
                packageManager.getLaunchIntentForPackage(
                    packageName
                )!!
            )()
        } catch (e: Exception) {
            e.printStackTrace()
            Toaster.toast(R.string.es_activity_not_found)
        }
    }
}

@Reader
@Unscoped
internal class AppActionFactory(
    private val appActionExecutorProvider: @Provider (String) -> AppActionExecutor,
    private val appActionIconProvider: @Provider (String) -> AppActionIconProvider
) : ActionFactory {
    override fun handles(key: String): Boolean = key.startsWith(ACTION_KEY_PREFIX)
    override suspend fun createAction(key: String): Action {
        val packageName = key.removePrefix(ACTION_KEY_PREFIX)
        return Action(
            key = key,
            title = getAppInfo(packageName).packageName,
            unlockScreen = true,
            iconProvider = appActionIconProvider(packageName),
            executor = appActionExecutorProvider(packageName),
            enabled = true
        )
    }
}

@Reader
@Unscoped
internal class AppActionPickerDelegate : ActionPickerDelegate {
    override val title: String
        get() = Resources.getString(R.string.es_action_app)
    override val icon: @Composable () -> Unit
        get() = { Icon(Icons.Default.Apps) }

    override suspend fun getResult(navigator: Navigator): ActionPickerResult? {
        val app = navigator.push<AppInfo> {
            AppPickerPage(appFilter = get<LaunchableAppFilter>())
        } ?: return null
        return ActionPickerResult.Action("$ACTION_KEY_PREFIX${app.packageName}")
    }
}

@Unscoped
internal class AppActionIconProvider(
    private val delegateProvider: @Provider (Any) -> CoilActionIconProvider,
    private val packageName: @Assisted String
) : ActionIconProvider {
    override val icon: Flow<@Composable () -> Unit>
        get() = delegateProvider(AppIcon(packageName)).icon
}

private const val ACTION_KEY_PREFIX = "app=:="
