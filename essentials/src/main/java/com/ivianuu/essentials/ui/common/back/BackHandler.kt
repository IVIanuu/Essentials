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

package com.ivianuu.essentials.ui.common.back

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.ivianuu.daggerextensions.AutoBindsIntoSet
import com.ivianuu.essentials.injection.EssentialsServiceModule
import com.ivianuu.essentials.internal.EssentialsService
import com.ivianuu.essentials.util.ext.doOnFragmentCreated
import com.ivianuu.essentials.util.ext.registerActivityLifecycleCallbacks
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.set

/**
 * Handles back presses in an activity for all fragment managers
 * It respects back listeners and always pops the last fragment added
 */
@Singleton
@EssentialsServiceModule
@AutoBindsIntoSet(EssentialsService::class)
class BackHandler @Inject constructor(application: Application) : EssentialsService {

    private val transactionIndexers = mutableMapOf<Activity, TransactionIndexer>()

    init {
        application.registerActivityLifecycleCallbacks(
            onActivityCreated = this::onActivityCreated,
            onActivitySaveInstanceState = this::onActivitySaveInstanceState,
            onActivityDestroyed = this::onActivityDestroyed
        )
    }

    fun handleBack(activity: Activity): Boolean {
        if (activity !is FragmentActivity) return false
        if (!transactionIndexers.contains(activity)) return false

        // collect fragments
        val fragments = mutableListOf<Pair<Fragment, FragmentManager>>()
        getAllFragmentsRecursively(activity.supportFragmentManager, fragments)

        // sort by index
        val top = fragments
            .filter { it.first.isAdded && it.first.isVisible }
            .filter { it.first.arguments?.containsKey(KEY_TRANSACTION_INDEX) == true }
            .sortedByDescending { it.first.arguments!!.getInt(KEY_TRANSACTION_INDEX) }
            .firstOrNull()

        // get top entry
        val topFragment = top?.first
        val topFm = top?.second

        // is top fragment is handling back?
        if (topFragment != null
            && topFragment is BackListener
            && topFragment.handleBack()) {
            return true
        }

        // try to pop the backstack
        if (topFm != null && topFm.popBackStackImmediate()) {
            return true
        }

        // give up
        return false
    }

    private fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity !is FragmentActivity) return

        val indexer = TransactionIndexer()

        if (savedInstanceState != null) {
            val bundle = savedInstanceState.getBundle(KEY_TRANSACTION_INDEXER)
            if (bundle != null) {
                indexer.restoreInstance(bundle)
            }
        }

        transactionIndexers[activity] = indexer

        attachIndexer(activity.supportFragmentManager)
    }

    private fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        if (activity !is FragmentActivity) return

        val indexer = transactionIndexers[activity]

        if (indexer != null) {
            val indexerBundle = Bundle()
            indexer.saveInstanceState(indexerBundle)
            outState.putBundle(KEY_TRANSACTION_INDEXER, indexerBundle)
        }
    }

    private fun onActivityDestroyed(activity: Activity) {
        if (activity !is FragmentActivity) return
        transactionIndexers.remove(activity)
    }

    private fun attachIndexer(fm: FragmentManager) {
        fm.doOnFragmentCreated(true) { _: FragmentManager, f: Fragment, savedInstanceState: Bundle? ->
            if (savedInstanceState == null) {
                val args = f.arguments ?: Bundle().also { f.arguments = it }
                if (!args.containsKey(KEY_TRANSACTION_INDEX)) {
                    val indexer = transactionIndexers[f.requireActivity()]
                    if (indexer != null) {
                        args.putInt(KEY_TRANSACTION_INDEX, indexer.getAndIncrement())
                    }
                }
            }
        }
    }

    private fun getAllFragmentsRecursively(
        fm: FragmentManager,
        list: MutableList<Pair<Fragment, FragmentManager>>
    ) {
        fm.fragments.forEach { f ->
            list.add(f to fm)
            getAllFragmentsRecursively(f.childFragmentManager, list)
        }
    }

    companion object {
        private const val KEY_TRANSACTION_INDEXER = "BackHandler.transactionIndexer"
        private const val KEY_TRANSACTION_INDEX = "BackHandler.transactionIndex"
    }
}


private class TransactionIndexer {

    private var index = 0

    @Synchronized fun getAndIncrement(): Int {
        index++
        return index
    }

    fun saveInstanceState(outState: Bundle) {
        outState.putInt(KEY_INDEX, index)
    }

    fun restoreInstance(savedInstanceState: Bundle) {
        index = savedInstanceState.getInt(KEY_INDEX)
    }

    private companion object {
        private const val KEY_INDEX = "TransactionIndexer.index"
    }

}