package com.ivianuu.essentials.util

import com.ivianuu.injekt.Binding
import com.ivianuu.injekt.merge.ApplicationComponent
import kotlinx.coroutines.CoroutineScope

typealias GlobalScope = CoroutineScope

@Binding(ApplicationComponent::class)
fun globalScope(defaultDispatcher: DefaultDispatcher): GlobalScope =
    CoroutineScope(defaultDispatcher)
