package com.ivianuu.essentials.gestures.action.actions

import android.content.pm.PackageManager
import androidx.ui.graphics.Image
import com.ivianuu.essentials.apps.AppInfo
import com.ivianuu.essentials.apps.AppStore
import com.ivianuu.essentials.apps.coil.AppIcon
import com.ivianuu.essentials.apps.ui.AppPickerRoute
import com.ivianuu.essentials.apps.ui.LaunchableAppFilter
import com.ivianuu.essentials.gestures.R
import com.ivianuu.essentials.gestures.action.Action
import com.ivianuu.essentials.gestures.action.ActionExecutor
import com.ivianuu.essentials.gestures.action.ActionFactory
import com.ivianuu.essentials.gestures.action.ActionIcon
import com.ivianuu.essentials.gestures.action.ActionIconProvider
import com.ivianuu.essentials.gestures.action.ActionPickerDelegate
import com.ivianuu.essentials.gestures.action.bindActionFactory
import com.ivianuu.essentials.gestures.action.bindActionPickerDelegate
import com.ivianuu.essentials.gestures.action.ui.picker.ActionPickerResult
import com.ivianuu.essentials.ui.navigation.NavigatorState
import com.ivianuu.essentials.util.ResourceProvider
import com.ivianuu.essentials.util.Toaster
import com.ivianuu.injekt.Factory
import com.ivianuu.injekt.Lazy
import com.ivianuu.injekt.Module
import com.ivianuu.injekt.Param
import com.ivianuu.injekt.Provider
import com.ivianuu.injekt.parametersOf
import kotlinx.coroutines.flow.Flow

internal val EsAppActionModule = Module {
    bindActionFactory<AppActionFactory>()
    bindActionPickerDelegate<AppActionPickerDelegate>()
}

@Factory
internal class AppActionExecutor(
    @Param private val packageName: String,
    private val packageManager: PackageManager,
    private val lazyDelegate: Lazy<IntentActionExecutor>,
    private val toaster: Toaster
) : ActionExecutor {
    override suspend fun invoke() {
        try {
            lazyDelegate {
                parametersOf({ packageManager.getLaunchIntentForPackage(packageName) })
            }()
        } catch (e: Exception) {
            e.printStackTrace()
            toaster.toast(R.string.es_activity_not_found)
        }
    }
}

@Factory
internal class AppActionFactory(
    private val appStore: AppStore,
    private val appActionExecutorProvider: Provider<AppActionExecutor>,
    private val appActionIconProvider: Provider<AppActionIconProvider>
) : ActionFactory {
    override fun handles(key: String): Boolean = key.startsWith(ACTION_KEY_PREFIX)
    override suspend fun createAction(key: String): Action {
        val packageName = key.removePrefix(ACTION_KEY_PREFIX)
        return Action(
            key = key,
            title = appStore.getAppInfo(packageName).packageName,
            unlockScreen = true,
            iconProvider = appActionIconProvider { parametersOf(packageName) },
            executor = appActionExecutorProvider { parametersOf(packageName) }
        )
    }
}

@Factory
internal class AppActionPickerDelegate(
    private val launchableAppFilter: LaunchableAppFilter,
    private val resourceProvider: ResourceProvider
) : ActionPickerDelegate {
    override val title: String
        get() = resourceProvider.getString(R.string.es_action_app)
    override val icon: Image
        get() = resourceProvider.getDrawable(R.drawable.es_ic_apps)

    override suspend fun getResult(navigator: NavigatorState): ActionPickerResult? {
        val app = navigator.push<AppInfo>(
            AppPickerRoute(appFilter = launchableAppFilter)
        ) ?: return null
        return ActionPickerResult.Action("$ACTION_KEY_PREFIX${app.packageName}")
    }
}

@Factory
internal class AppActionIconProvider(
    private val lazyDelegate: Lazy<CoilActionIconProvider>,
    @Param private val packageName: String
) : ActionIconProvider {
    override val icon: Flow<ActionIcon>
        get() = lazyDelegate { parametersOf(AppIcon(packageName)) }.icon
}

private const val ACTION_KEY_PREFIX = "app=:="