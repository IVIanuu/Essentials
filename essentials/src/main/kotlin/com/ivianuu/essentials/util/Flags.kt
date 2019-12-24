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

package com.ivianuu.essentials.util

// todo inline class Flag(val backing: Int)

fun Int.setFlag(flag: Int, set: Boolean): Int = if (set) addFlag(flag) else removeFlag(flag)
fun Int.addFlag(flag: Int): Int = this or flag
fun Int.removeFlag(flag: Int): Int = this and flag.inv()
fun Int.containsFlag(flag: Int): Boolean = this and flag == flag
fun Int.notContainsFlag(flag: Int): Boolean = !containsFlag(flag)
