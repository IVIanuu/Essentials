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
  kotlin("plugin.serialization")
}

apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/android-build-lib.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/java-8-android.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/kt-source-sets-android.gradle")

dependencies {
  api(project(":essentials-accessibility"))
  api(project(":essentials-android-settings"))
  api(project(":essentials-android-util"))
  api(project(":essentials-apps"))
  api(project(":essentials-apps-ui"))
  api(project(":essentials-hide-nav-bar"))
  api(project(":essentials-permission"))
  api(project(":essentials-recent-apps"))
  api(project(":essentials-screen-state"))
  api(project(":essentials-shortcut-picker"))
  api(project(":essentials-system-overlay"))
  api(project(":essentials-torch"))
  api(project(":essentials-unlock"))
  testImplementation(project(":essentials-android-test"))
}

plugins.apply("com.vanniktech.maven.publish")
