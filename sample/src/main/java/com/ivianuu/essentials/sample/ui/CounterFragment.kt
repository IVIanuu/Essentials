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

package com.ivianuu.essentials.sample.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ivianuu.essentials.sample.R
import com.ivianuu.essentials.ui.base.BaseFragment
import com.ivianuu.traveler.keys.FragmentKey
import com.ivianuu.traveler.keys.requireKey
import kotlinx.android.parcel.Parcelize

/**
 * @author Manuel Wrage (IVIanuu)
 */
class CounterFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_counter

    private var containerId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        containerId = container?.id ?: -1
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val key = requireKey<CounterKey>()

        title.text = "Count: ${key.count}"

    }

}

@Parcelize
data class CounterKey(val count: Int) : FragmentKey(), Parcelable {
    override fun createFragment() = CounterFragment()
}