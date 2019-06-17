/*
 * Copyright 2018 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.essentials.injection

import android.accessibilityservice.AccessibilityService
import android.accounts.AccountManager
import android.app.*
import android.app.admin.DevicePolicyManager
import android.app.job.JobScheduler
import android.app.usage.NetworkStatsManager
import android.app.usage.UsageStatsManager
import android.appwidget.AppWidgetManager
import android.bluetooth.BluetoothManager
import android.content.ClipboardManager
import android.content.Context
import android.content.RestrictionsManager
import android.content.pm.LauncherApps
import android.content.pm.ShortcutManager
import android.hardware.ConsumerIrManager
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.hardware.display.DisplayManager
import android.hardware.fingerprint.FingerprintManager
import android.hardware.input.InputManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaRouter
import android.media.midi.MidiManager
import android.media.projection.MediaProjectionManager
import android.media.session.MediaSessionManager
import android.media.tv.TvInputManager
import android.net.ConnectivityManager
import android.net.nsd.NsdManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.*
import android.os.health.SystemHealthManager
import android.os.storage.StorageManager
import android.print.PrintManager
import android.telecom.TelecomManager
import android.telephony.CarrierConfigManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.CaptioningManager
import android.view.inputmethod.InputMethodManager
import android.view.textservice.TextServicesManager
import androidx.core.content.ContextCompat
import com.ivianuu.injekt.factoryWithState
import com.ivianuu.injekt.module
import com.ivianuu.injekt.typeOf
import kotlin.reflect.KClass

/**
 * Module which binds all available system services
 */
val systemServiceModule = module {
    getSystemServices()
        .map { it as KClass<Any> }
        .forEach { service ->
            factoryWithState(typeOf(service)) {
                val context = link<Context>()
                definition {
                    ContextCompat.getSystemService(
                        context(), service.java
                    )
                }
            }
        }
}

@Suppress("DEPRECATION")
private fun getSystemServices(): Set<KClass<*>> = mutableSetOf<KClass<*>>().apply {
    add(AccessibilityService::class)
    add(AccountManager::class)
    add(ActivityManager::class)
    add(AlarmManager::class)
    add(AudioManager::class)
    add(ClipboardManager::class)
    add(ConnectivityManager::class)
    add(DevicePolicyManager::class)
    add(DownloadManager::class)
    add(DropBoxManager::class)
    add(InputMethodManager::class)
    add(KeyguardManager::class)
    add(LayoutInflater::class)
    add(LocationManager::class)
    add(NfcManager::class)
    add(NotificationManager::class)
    add(PowerManager::class)
    add(SearchManager::class)
    add(SensorManager::class)
    add(StorageManager::class)
    add(TelephonyManager::class)
    add(TextServicesManager::class)
    add(UiModeManager::class)
    add(UsbManager::class)
    add(Vibrator::class)
    add(WallpaperManager::class)
    add(WifiP2pManager::class)
    add(WifiManager::class)
    add(WindowManager::class)

    if (Build.VERSION.SDK_INT > 16) {
        add(InputManager::class)
        add(MediaRouter::class)
        add(NsdManager::class)
    }
    if (Build.VERSION.SDK_INT > 17) {
        add(DisplayManager::class)
        add(UserManager::class)
    }
    if (Build.VERSION.SDK_INT > 18) {
        add(BluetoothManager::class)
    }
    if (Build.VERSION.SDK_INT > 19) {
        add(AppOpsManager::class)
        add(CaptioningManager::class)
        add(ConsumerIrManager::class)
        add(PrintManager::class)
    }
    if (Build.VERSION.SDK_INT > 21) {
        add(AppWidgetManager::class)
        add(BatteryManager::class)
        add(CameraManager::class)
        add(JobScheduler::class)
        add(LauncherApps::class)
        add(MediaProjectionManager::class)
        add(MediaSessionManager::class)
        add(RestrictionsManager::class)
        add(TelecomManager::class)
        add(TvInputManager::class)
    }
    if (Build.VERSION.SDK_INT > 22) {
        add(SubscriptionManager::class)
        add(UsageStatsManager::class)
    }
    if (Build.VERSION.SDK_INT >= 23) {
        add(CarrierConfigManager::class)
        add(FingerprintManager::class)
        add(MidiManager::class)
        add(NetworkStatsManager::class)
    }
    if (Build.VERSION.SDK_INT >= 24) {
        add(HardwarePropertiesManager::class)
        add(SystemHealthManager::class)
    }
    if (Build.VERSION.SDK_INT >= 25) {
        add(ShortcutManager::class)
    }
}