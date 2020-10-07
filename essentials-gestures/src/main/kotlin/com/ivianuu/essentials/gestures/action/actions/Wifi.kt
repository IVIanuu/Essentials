package com.ivianuu.essentials.gestures.action.actions

import android.net.wifi.WifiManager
import androidx.compose.foundation.Icon
import androidx.compose.ui.res.vectorResource
import com.ivianuu.essentials.broadcast.BroadcastFactory
import com.ivianuu.essentials.gestures.R
import com.ivianuu.essentials.gestures.action.Action
import com.ivianuu.essentials.gestures.action.ActionBinding
import com.ivianuu.essentials.gestures.action.ActionIcon
import com.ivianuu.essentials.util.Resources
import com.ivianuu.injekt.FunBinding
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

@ActionBinding
fun wifiAction(
    resources: Resources,
    toggleWifi: toggleWifi,
    wifiIcon: wifiIcon,
) = Action(
    key = "wifi",
    title = resources.getString(R.string.es_action_wifi),
    icon = wifiIcon(),
    execute = { toggleWifi() }
)

@FunBinding
fun toggleWifi(wifiManager: WifiManager) {
    wifiManager.isWifiEnabled = !wifiManager.isWifiEnabled
}

@FunBinding
fun wifiIcon(
    broadcastFactory: BroadcastFactory,
    wifiManager: WifiManager,
): ActionIcon = broadcastFactory.create(WifiManager.WIFI_STATE_CHANGED_ACTION)
    .map {
        val state =
            it.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED)
        state == WifiManager.WIFI_STATE_ENABLED
    }
    .onStart { emit(wifiManager.isWifiEnabled) }
    .map { wifiEnabled ->
        if (wifiEnabled) R.drawable.es_ic_network_wifi
        else R.drawable.es_ic_signal_wifi_off
    }
    .map {
        {
            Icon(vectorResource(it))
        }
    }
