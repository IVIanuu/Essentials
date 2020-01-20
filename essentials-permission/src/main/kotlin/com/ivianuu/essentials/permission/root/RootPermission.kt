package com.ivianuu.essentials.permission.root

import androidx.fragment.app.FragmentActivity
import com.ivianuu.essentials.permission.MetaDataKeyWithValue
import com.ivianuu.essentials.permission.Metadata
import com.ivianuu.essentials.permission.Permission
import com.ivianuu.essentials.permission.PermissionManager
import com.ivianuu.essentials.permission.PermissionRequestHandler
import com.ivianuu.essentials.permission.PermissionResult
import com.ivianuu.essentials.permission.PermissionStateProvider
import com.ivianuu.essentials.permission.R
import com.ivianuu.essentials.permission.bindPermissionRequestHandler
import com.ivianuu.essentials.permission.bindPermissionStateProvider
import com.ivianuu.essentials.permission.metadataOf
import com.ivianuu.essentials.permission.with
import com.ivianuu.essentials.shell.Shell
import com.ivianuu.essentials.util.Toaster
import com.ivianuu.injekt.Factory
import com.ivianuu.injekt.Module

fun RootPermission(vararg metadata: MetaDataKeyWithValue<*>) = Permission(
    metadata = metadataOf(
        Metadata.IsRootPermission with Unit,
        *metadata
    )
)

val Metadata.Companion.IsRootPermission by lazy {
    Metadata.Key<Unit>("IsRootPermission")
}

internal val EsRootPermissionModule = Module {
    bindPermissionRequestHandler<RootPermissionRequestHandler>()
    bindPermissionStateProvider<RootPermissionStateProvider>()
}

@Factory
internal class RootPermissionStateProvider(private val shell: Shell) : PermissionStateProvider {

    override fun handles(permission: Permission): Boolean =
        Metadata.IsRootPermission in permission.metadata

    override suspend fun isGranted(permission: Permission): Boolean = shell.isAvailable()
}

@Factory
internal class RootPermissionRequestHandler(
    private val shell: Shell,
    private val toaster: Toaster
) : PermissionRequestHandler {
    override fun handles(permission: Permission): Boolean =
        Metadata.IsRootPermission in permission.metadata

    override suspend fun request(
        activity: FragmentActivity,
        manager: PermissionManager,
        permission: Permission
    ): PermissionResult {
        val isOk = shell.isAvailable()
        if (!isOk) toaster.toast(R.string.es_no_root)
        return PermissionResult(isOk)
    }
}