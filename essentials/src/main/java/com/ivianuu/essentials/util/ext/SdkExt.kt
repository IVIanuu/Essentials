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

package com.ivianuu.essentials.util.ext

import android.os.Build

inline val isLollipopMr1
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1

inline val isMarshmallow
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

inline val isNougat
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

inline val isNougatMr1
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1

inline val isOreo
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

inline val isOreoMr1
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1

inline val isP
    get() = Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1