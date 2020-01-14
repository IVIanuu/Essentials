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
    id("com.android.application")
    id("com.github.ben-manes.versions")
    //id("com.ivianuu.injekt")
    //id("com.ivianuu.essentials")
    kotlin("android")
    id("kotlin-android-extensions")
}

apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/android-build-app.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/android-proguard.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/java-8-android.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/kt-android-ext.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/kt-compiler-args.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/kt-source-sets-android.gradle")

android {
    // todo remove once fixed
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":essentials"))
    implementation(project(":essentials-about"))
    api(project(":essentials-accessibility"))
    api(project(":essentials-activity-result"))
    implementation(project(":essentials-apps"))
    implementation(project(":essentials-apps-coil"))
    implementation(project(":essentials-apps-ui"))
    releaseImplementation(project(":essentials-billing-release"))
    debugImplementation(project(":essentials-billing-debug"))
    implementation(project(":essentials-boot"))
    implementation(project(":essentials-foreground"))
    implementation(project(":essentials-gestures"))
    implementation(project(":essentials-hide-nav-bar"))
    implementation(project(":essentials-notification-listener"))
    implementation(project(":essentials-permission"))
    implementation(project(":essentials-process-restart"))
    implementation(project(":essentials-shell"))
    implementation(project(":essentials-secure-settings"))
    implementation(project(":essentials-service"))
    implementation(project(":essentials-shell"))
    implementation(project(":essentials-shortcut-picker"))
    implementation(project(":essentials-tile"))
    implementation(project(":essentials-torch"))
    implementation(project(":essentials-twilight"))
    implementation(project(":essentials-unlock"))
    implementation(project(":essentials-work"))
    testImplementation(project(":essentials-test"))
}