import com.github.jengelman.gradle.plugins.shadow.tasks.*

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
  kotlin("jvm")
  kotlin("kapt")
  id("com.github.johnrengelman.shadow")
}

apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/java-8.gradle")
apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/kt-compiler-args.gradle")

val shadowJar = tasks.getByName<ShadowJar>("shadowJar") {
  configurations = listOf(project.configurations.getByName("compileOnly"))
  relocate("org.jetbrains.kotlin.com.intellij", "com.intellij")
  dependencies {
    exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib"))
    exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib-common"))
    exclude(dependency("org.jetbrains:annotations"))

    exclude(dependency("com.intellij:openapi"))
    exclude(dependency("com.intellij:extensions"))
    exclude(dependency("com.intellij:annotations"))
  }
}

artifacts {
  archives(shadowJar)
}

dependencies {
  api(Deps.Kotlin.compilerEmbeddable)
  implementation(Deps.autoService)
  kapt(Deps.autoService)
  testImplementation(Deps.compileTesting)
  testImplementation(Deps.junit)
  testImplementation(Deps.Kotlin.compilerEmbeddable)
  testImplementation(Deps.kotestAssertions)
  testImplementation(project(":essentials-optics"))
}

plugins.apply("com.vanniktech.maven.publish")
