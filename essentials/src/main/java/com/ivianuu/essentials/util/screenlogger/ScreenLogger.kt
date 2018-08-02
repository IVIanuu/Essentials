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

package com.ivianuu.essentials.util.screenlogger

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.ivianuu.daggerextensions.AutoBindsIntoSet
import com.ivianuu.essentials.app.AppService
import com.ivianuu.essentials.injection.EssentialsAppServiceModule
import com.ivianuu.essentials.util.analytics.Analytics
import com.ivianuu.essentials.util.ext.doOnActivityCreated
import com.ivianuu.essentials.util.ext.doOnFragmentCreated
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Utility class to help with screen logging
 */
@Singleton
@EssentialsAppServiceModule
@AutoBindsIntoSet(AppService::class)
class ScreenLogger @Inject constructor(private val application: Application) : AppService {

    private val listeners = mutableListOf<Listener>()

    init {
        listeners.add(AnalyticsListener())
    }

    override fun start() {
        application.doOnActivityCreated { activity, savedInstanceState ->
            handleActivity(activity, savedInstanceState)
        }
    }

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    private fun handleActivity(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is IdentifiableScreen
            && activity !is Ignore
            && savedInstanceState == null) {
            val name = getId(activity)
            listeners.toList()
                .forEach { it.screenLaunched(name) }
        }

        if (activity is FragmentActivity) {
            activity.supportFragmentManager.doOnFragmentCreated(true) { _: FragmentManager, fragment: Fragment, bundle: Bundle? ->
                if (fragment is IdentifiableScreen
                    && fragment !is Ignore
                    && bundle == null) {
                    val name = getId(fragment)
                    listeners.toList()
                        .forEach { it.screenLaunched(name) }
                }
            }
        }
    }

    private fun getId(screen: IdentifiableScreen): String {
        return when {
            screen.screenId.isNotEmpty() -> screen.screenId
            screen.screenIdRes != 0 -> application.getString(screen.screenIdRes)
            else -> parseName(screen.javaClass.simpleName)
        }
    }

    private fun parseName(className: String): String {
        val withoutSuffix = when {
            className.endsWith(SUFFIX_ACTIVITY) -> className.replace(
                SUFFIX_ACTIVITY, ""
            )
            className.endsWith(SUFFIX_DIALOG) -> className.replace(
                SUFFIX_DIALOG, ""
            )
            className.endsWith(SUFFIX_FRAGMENT) -> className.replace(
                SUFFIX_FRAGMENT, ""
            )
            else -> className
        }

        // there's probably a util function but i can't find it:D
        val regex = "([a-z])([A-Z]+)"
        val replacement = "$1_$2"
        return withoutSuffix
            .replace(regex.toRegex(), replacement)
            .toLowerCase()
    }

    interface Listener {
        fun screenLaunched(name: String)
    }

    class AnalyticsListener : Listener {
        override fun screenLaunched(name: String) {
            Analytics.log("screen launched: $name")
        }
    }

    interface Ignore

    private companion object {
        private const val SUFFIX_ACTIVITY = "Activity"
        private const val SUFFIX_DIALOG = "Dialog"
        private const val SUFFIX_FRAGMENT = "Fragment"
    }
}