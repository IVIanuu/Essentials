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

@file:Suppress("ClassName", "unused")

object Build {
    const val applicationId = "com.ivianuu.essentials.sample"
    const val buildToolsVersion = "29.0.3"

    const val compileSdk = 29
    const val minSdk = 24
    const val targetSdk = 29
    const val versionCode = 1
    const val versionName = "0.0.1"
}

object Publishing {
    const val groupId = "com.ivianuu.essentials"
    const val vcsUrl = "https://github.com/IVIanuu/essentials"
    const val version = "${Build.versionName}-dev510"
}

object Deps {
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.0.0"

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.1.0"
        const val core = "androidx.core:core-ktx:1.2.0"

        const val composeVersion = "0.0.1-dev135"

        object Compose {
            const val version = composeVersion
            const val runtime = "androidx.compose:compose-runtime:$composeVersion"
        }

        object Lifecycle {
            private const val version = "2.2.0"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        }

        object Test {
            const val core = "androidx.test:core:1.2.0"
            const val junit = "androidx.test.ext:junit:1.1.1"
            const val rules = "androidx.test:rules:1.2.0"
            const val runner = "androidx.test:runner:1.2.0"
        }

        object Ui {
            private const val version = composeVersion
            const val animation = "androidx.ui:ui-animation:$version"
            const val core = "androidx.ui:ui-core:$version"
            const val foundation = "androidx.ui:ui-foundation:$version"
            const val layout = "androidx.ui:ui-layout:$version"
            const val material = "androidx.ui:ui-material:$version"
            const val materialIconsExtended = "androidx.ui:ui-material-icons-extended:$version"
            const val test = "androidx.ui:ui-test:$version"
            const val text = "androidx.ui:ui-text:$version"
            const val tooling = "androidx.ui:ui-tooling:$version"
            const val vector = "androidx.ui:ui-vector:$version"
        }

        const val work = "androidx.work:work-runtime-ktx:2.1.0"
    }

    const val autoService = "com.google.auto.service:auto-service:1.0-rc6"

    const val bintrayGradlePlugin = "com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4"

    const val buildConfigGradlePlugin =
        "gradle.plugin.de.fuerstenau:BuildConfigPlugin:1.1.8"

    const val coil = "io.coil-kt:coil:0.11.0"

    const val compileTesting = "com.github.tschuchortdev:kotlin-compile-testing:1.2.7"

    object Coroutines {
        private const val version = "1.3.7"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    const val dexcountGradlePlugin = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:1.0.0"

    const val essentialsGradlePlugin =
        "com.ivianuu.essentials:essentials-gradle-plugin:0.0.1-dev510"

    object Fabric {
        const val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1"
        const val gradlePlugin = "io.fabric.tools:gradle:1.31.2"
    }

    object Injekt {
        private const val version = "0.0.1-dev173"
        const val android = "com.ivianuu.injekt:injekt-android:$version"
        const val androidCompose = "com.ivianuu.injekt:injekt-android-compose:$version"
        const val androidWork = "com.ivianuu.injekt:injekt-android-work:$version"
        const val common = "com.ivianuu.injekt:injekt-common:$version"
        const val core = "com.ivianuu.injekt:injekt-core:$version"
        const val composition = "com.ivianuu.injekt:injekt-composition:$version"
        const val gradlePlugin = "com.ivianuu.injekt:injekt-gradle-plugin:$version"
    }

    const val junit = "junit:junit:4.13"

    object Kotlin {
        private const val version = "1.4.255-SNAPSHOT"
        const val compilerEmbeddable = "org.jetbrains.kotlin:kotlin-compiler-embeddable:$version"
        const val compiler = "org.jetbrains.kotlin:kotlin-compiler:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val gradlePluginApi = "org.jetbrains.kotlin:kotlin-gradle-plugin-api:$version"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"
    }

    const val kotlinFlowExtensions = "com.github.akarnokd:kotlin-flow-extensions:0.0.2"

    const val mavenGradlePlugin = "com.github.dcendents:android-maven-gradle-plugin:2.1"

    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"

    object Moshi {
        private const val version = "1.9.2"
        const val adapters = "com.squareup.moshi:moshi-adapters:$version"
        const val moshi = "com.squareup.moshi:moshi:$version"
        const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
    }

    object MoshiSealed {
        private const val version = "0.1.0"
        const val annotations = "dev.zacsweers.moshisealed:moshi-sealed-annotations:$version"
        const val codegen = "dev.zacsweers.moshisealed:moshi-sealed-codegen:$version"
    }

    const val playBilling = "com.android.billingclient:billing-ktx:2.1.0"

    const val processingX = "com.ivianuu.processingx:processingx:0.0.1-dev4"

    const val roboelectric = "org.robolectric:robolectric:4.3.1"

    const val spotlessGradlePlugin = "com.diffplug.spotless:spotless-plugin-gradle:3.26.1"

    const val superUser = "eu.chainfire:libsuperuser:1.0.0.+"

    const val versionsGradlePlugin = "com.github.ben-manes:gradle-versions-plugin:0.27.0"
}
