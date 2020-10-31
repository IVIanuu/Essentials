/*
 * Copyright 2020 Manuel Wrage
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

package com.ivianuu.essentials.about

import androidx.compose.runtime.Composable
import com.ivianuu.essentials.ui.common.InsettingScrollableColumn
import com.ivianuu.essentials.ui.core.Text
import com.ivianuu.essentials.ui.material.ListItem
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.Subheader
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.Navigator
import com.ivianuu.essentials.ui.navigation.UrlRoute
import com.ivianuu.essentials.ui.navigation.push
import com.ivianuu.essentials.util.BuildInfo
import com.ivianuu.injekt.FunApi
import com.ivianuu.injekt.FunBinding

@FunBinding
@Composable
fun AboutPage(
    buildInfo: BuildInfo,
    privacyPolicyUrl: PrivacyPolicyUrl?,
    aboutSection: AboutSection,
) {
    Scaffold(topBar = { TopAppBar(title = { Text(R.string.about_title) }) }) {
        InsettingScrollableColumn {
            aboutSection(
                buildInfo.packageName,
                false,
                privacyPolicyUrl
            )
        }
    }
}

@FunBinding
@Composable
fun AboutSection(
    navigator: Navigator,
    @FunApi packageName: String,
    @FunApi showHeader: Boolean,
    @FunApi privacyPolicyUrl: PrivacyPolicyUrl?
) {
    if (showHeader) {
        Subheader {
            Text(R.string.about_title)
        }
    }

    AboutItem(
        titleRes = R.string.about_rate,
        descRes = R.string.about_rate_desc,
        url = { "https://play.google.com/store/apps/details?id=$packageName" },
        navigator = navigator
    )

    AboutItem(
        titleRes = R.string.about_more_apps,
        descRes = R.string.about_more_apps_desc,
        url = { "https://play.google.com/store/apps/developer?id=Manuel+Wrage" },
        navigator = navigator
    )

    AboutItem(
        titleRes = R.string.about_reddit,
        descRes = R.string.about_reddit_desc,
        url = { "https://www.reddit.com/r/manuelwrageapps" },
        navigator = navigator
    )

    AboutItem(
        titleRes = R.string.about_github,
        descRes = R.string.about_github_desc,
        url = { "https://github.com/IVIanuu" },
        navigator = navigator
    )

    AboutItem(
        titleRes = R.string.about_twitter,
        descRes = R.string.about_twitter_desc,
        url = { "https://twitter.com/IVIanuu" },
        navigator = navigator
    )

    if (privacyPolicyUrl != null) {
        AboutItem(
            titleRes = R.string.about_privacy_policy,
            url = { privacyPolicyUrl },
            navigator = navigator
        )
    }
}

@Composable
private fun AboutItem(
    titleRes: Int,
    descRes: Int? = null,
    url: () -> String,
    navigator: Navigator
) {
    ListItem(
        title = { Text(titleRes) },
        subtitle = descRes?.let {
            {
                Text(it)
            }
        },
        onClick = { navigator.push(UrlRoute(url())) }
    )
}
