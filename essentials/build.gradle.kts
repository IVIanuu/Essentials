/*
 * Copyright 2019 Manuel Wrage
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
    kotlin("android")
    kotlin("kapt")
}

apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/android-build-lib.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/java-8-android.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/kt-android-ext.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/kt-compiler-args.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/kt-kapt.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/kt-source-sets-android.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/mvn-publish.gradle")

dependencies {
    api(Deps.AndroidX.activity)
    api(Deps.AndroidX.appCompat)
    api(Deps.AndroidX.cardView)
    api(Deps.AndroidX.constraintLayout)
    api(Deps.AndroidX.core)
    api(Deps.AndroidX.Lifecycle.liveData)
    api(Deps.AndroidX.Lifecycle.reactiveStreams)
    api(Deps.AndroidX.Lifecycle.runtime)
    api(Deps.AndroidX.Lifecycle.ViewModel.viewModel)
    api(Deps.AndroidX.Lifecycle.ViewModel.savedState)
    api(Deps.AndroidX.Ui.material)

    api(Deps.Coroutines.android)
    api(Deps.Coroutines.core)

    api(Deps.coil)

    api(Deps.Director.director)
    api(Deps.Director.common)

    api(Deps.epoxy)

    api(Deps.epoxyPrefs)

    kapt(project(":essentials-compiler"))

    api(Deps.Injekt.injekt)
    api(Deps.Injekt.android)

    api(Deps.Kotlin.stdlib)

    api(Deps.kotlinFlowExtensions)

    api(Deps.KPrefs.kPrefs)
    api(Deps.KPrefs.common)
    api(Deps.KPrefs.coroutines)

    api(Deps.KSettings.kSettings)
    api(Deps.KSettings.coroutines)

    api(Deps.materialComponents)

    api(Deps.MaterialDialogs.bottomSheets)
    api(Deps.MaterialDialogs.color)
    api(Deps.MaterialDialogs.core)
    api(Deps.MaterialDialogs.dateTime)
    api(Deps.MaterialDialogs.files)
    api(Deps.MaterialDialogs.input)
    api(Deps.MaterialDialogs.lifecycle)

    api(Deps.Scopes.scopes)
    api(Deps.Scopes.android)
    api(Deps.Scopes.coroutines)

    api(Deps.timberKt)

}