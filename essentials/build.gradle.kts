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
plugins {
    id("com.android.library")
    id("kotlin-android")
}

apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/android-build-lib.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/no-build-config.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/kt-android-ext.gradle")

android {
    defaultConfig {
        consumerProguardFile("proguard-rules.txt")
    }
}

apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/mvn-publish.gradle")

dependencies {
    api(Deps.androidxActivity)
    api(Deps.androidxAppCompat)
    api(Deps.androidxCardView)
    api(Deps.androidxCore)
    api(Deps.androidxFragment)
    api(Deps.androidxLifecycleLiveData)
    api(Deps.androidxLifecycleViewModel)
    api(Deps.androidxLifecycleViewModelSavedState)

    api(Deps.coroutinesAndroid)
    api(Deps.coroutinesCore)
    api(Deps.coroutinesRxJava)

    api(Deps.constraintLayout)

    api(Deps.epoxy)
    api(Deps.epoxyPrefs)

    api(Deps.injekt)
    api(Deps.injektAndroid)
    api(Deps.injektCommon)

    api(Deps.kommonAppCompat)
    api(Deps.kommonCore)
    api(Deps.kommonFragment)
    api(Deps.kommonLifecycle)
    api(Deps.kommonMaterial)
    api(Deps.kommonRecyclerView)
    api(Deps.kommonViewPager)

    api(Deps.kotlinStdLib)

    api(Deps.kPrefs)
    api(Deps.kPrefsCommon)
    api(Deps.kPrefsCoroutines)
    api(Deps.kPrefsLiveData)
    api(Deps.kPrefsRx)

    api(Deps.kSettings)
    api(Deps.kSettingsCoroutines)
    api(Deps.kSettingsLiveData)
    api(Deps.kSettingsRx)

    api(Deps.materialComponents)

    api(Deps.materialDialogsCore)

    api(Deps.rxAndroid)
    api(Deps.rxJava)
    api(Deps.rxKotlin)

    api(Deps.scopes)
    api(Deps.scopesAndroid)
    api(Deps.scopesCoroutines)
    api(Deps.scopesRx)

    api(Deps.timber)
    api(Deps.timberKt)

    api(Deps.traveler)
    api(Deps.travelerAndroid)
    api(Deps.travelerCommon)
}