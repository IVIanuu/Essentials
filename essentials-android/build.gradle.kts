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

plugins {
  id("com.android.library")
  id("com.ivianuu.essentials")
  id("com.ivianuu.essentials.compose")
  kotlin("android")
}

apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/android-build-lib.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/java-8-android.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/kt-compiler-args.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/kt-source-sets-android.gradle")

dependencies {
  api(Deps.AndroidX.Activity.activity)
  api(Deps.AndroidX.Activity.compose)
  api(Deps.AndroidX.core)
  api(Deps.AndroidX.Lifecycle.runtime)

  api(Deps.Coroutines.android)

  api(Deps.Injekt.android)

  api(project(":essentials-common"))
  api(project(":essentials-android-core"))
  api(project(":essentials-android-data"))
  api(project(":essentials-logging-android"))
  api(project(":essentials-android-prefs"))
  api(project(":essentials-android-settings"))
  api(project(":essentials-android-util"))
  api(project(":essentials-ui"))
}

plugins.apply("com.vanniktech.maven.publish")
