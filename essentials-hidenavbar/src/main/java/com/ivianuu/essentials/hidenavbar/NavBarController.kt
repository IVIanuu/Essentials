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

package com.ivianuu.essentials.hidenavbar

import android.annotation.SuppressLint
import android.app.Application
import android.app.KeyguardManager
import android.content.Intent
import android.graphics.Rect
import android.hardware.SensorManager
import android.view.OrientationEventListener
import android.view.Surface
import com.ivianuu.androidktx.core.app.doOnConfigurationChanged
import com.ivianuu.androidktx.core.content.isLandscape
import com.ivianuu.androidktx.core.content.isScreenOff
import com.ivianuu.androidktx.core.content.rotation
import com.ivianuu.essentials.app.AppService
import com.ivianuu.essentials.util.BroadcastFactory
import com.ivianuu.essentials.util.ext.combineLatest
import com.ivianuu.essentials.util.ext.rxMain
import com.ivianuu.essentials.util.ext.toastError
import com.ivianuu.kprefs.rx.observable
import com.ivianuu.rxjavaktx.observable
import com.ivianuu.timberktx.d
import io.reactivex.rxkotlin.Observables
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles the state of the navigation bar
 */
@Singleton class NavBarController @Inject constructor(
    private val broadcastFactory: BroadcastFactory,
    private val app: Application,
    private val keyguardManager: KeyguardManager,
    private val prefs: NavBarPrefs,
    private val overscanHelper: OverscanHelper
) : AppService {

    @SuppressLint("CheckResult")
    override fun start() {
        Observables
            .combineLatest(
                prefs.hideNavBarEnabled.observable,
                prefs.fullOverscan.observable,
                prefs.rot270Fix.observable,
                prefs.tabletMode.observable,
                configChanges().startWith(Unit),
                rotationChanges().startWith(app.rotation)
            )
            .observeOn(rxMain)
            .subscribe { updateNavBarState(false) }

        broadcastFactory.create(
            Intent.ACTION_SCREEN_OFF,
            Intent.ACTION_SCREEN_ON,
            Intent.ACTION_USER_PRESENT
        )
            .map { Unit }
            .startWith(Unit)
            .filter { prefs.showNavBarScreenOff.get() }
            .map { keyguardManager.isKeyguardLocked || app.isScreenOff }
            .subscribe {
                d { "on screen state changed $it" }
                updateNavBarState(it)
            }

        broadcastFactory.create(Intent.ACTION_SHUTDOWN)
            .subscribe {
                d { "force show because of reboot" }
                updateNavBarState(true)
            }
    }

    private fun updateNavBarState(
        forceShow: Boolean
    ) {
        d { "update nav bar force show $forceShow" }
        if (forceShow) {
            updateNavBarStateInternal(false)
        } else {
            val shouldHide = prefs.hideNavBarEnabled.get()
            updateNavBarStateInternal(shouldHide)
        }
    }

    private fun updateNavBarStateInternal(hide: Boolean) {
        d { "update nav bar internal hide $hide" }
        updateNavBar(hide, true)
    }

    private fun updateNavBar(
        hide: Boolean,
        failQuiet: Boolean
    ) {
        try {
            val navBarHeight =
                getNavigationBarHeight() - (if (prefs.fullOverscan.get()) 0 else OVERSCAN_LEFT_PIXELS)
            val rect = getOverscanRect(if (hide) navBarHeight else 0)
            overscanHelper.setDisplayOverScan(rect)
        } catch (e: Exception) {
            e.printStackTrace()
            if (!failQuiet) {
                onFailedToToggleNavBar()
            }
        }
    }

    private fun onFailedToToggleNavBar() {
        app.toastError(R.string.msg_failed_to_toggle_nav_bar)
    }

    private fun getNavigationBarHeight(): Int {
        val id = app.resources.getIdentifier(
            if (!app.isLandscape) "navigation_bar_height" else "navigation_bar_height_landscape",
            "dimen", "android"
        )
        return if (id > 0) {
            app.resources.getDimensionPixelSize(id) //- 1
        } else 0
    }

    private fun getOverscanRect(navBarHeight: Int) = when {
        prefs.rot270Fix.get() -> {
            when (app.rotation) {
                Surface.ROTATION_270 -> Rect(0, -navBarHeight, 0, 0)
                Surface.ROTATION_180 -> Rect(0, -navBarHeight, 0, 0)
                else -> Rect(0, 0, 0, -navBarHeight)
            }
        }
        prefs.tabletMode.get() -> {
            when (app.rotation) {
                Surface.ROTATION_270 -> Rect(0, 0, -navBarHeight, 0)
                Surface.ROTATION_90 -> Rect(-navBarHeight, 0, 0, 0)
                else -> Rect(0, 0, 0, -navBarHeight)
            }
        }
        else -> {
            if (app.rotation == Surface.ROTATION_180) {
                Rect(0, -navBarHeight, 0, 0)
            } else {
                Rect(0, 0, 0, -navBarHeight)
            }
        }
    }

    private fun rotationChanges() = observable<Int> { e ->
        val listener = object : OrientationEventListener(
            app, SensorManager.SENSOR_DELAY_NORMAL
        ) {
            private var currentRotation = app.rotation
            override fun onOrientationChanged(orientation: Int) {
                val rotation = app.rotation
                if (rotation != currentRotation) {
                    e.onNext(rotation)
                    currentRotation = rotation
                }
            }
        }

        e.setCancellable { listener.disable() }

        listener.enable()
    }

    private fun configChanges() = observable<Unit> { e ->
        val callbacks = app.doOnConfigurationChanged {
            e.onNext(Unit)
        }

        e.setCancellable { app.unregisterComponentCallbacks(callbacks) }
    }

    companion object {
        private const val OVERSCAN_LEFT_PIXELS = 1
    }
}