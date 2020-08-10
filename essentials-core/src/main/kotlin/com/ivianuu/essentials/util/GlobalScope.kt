package com.ivianuu.essentials.util

import com.ivianuu.injekt.ApplicationScoped
import com.ivianuu.injekt.Given
import com.ivianuu.injekt.Reader
import com.ivianuu.injekt.given
import kotlinx.coroutines.CoroutineScope

typealias GlobalScope = CoroutineScope

@Reader
inline val globalScope: GlobalScope
    get() = given()

object GlobalScopeModule {

    @Given(ApplicationScoped::class)
    fun globalScope(): GlobalScope = CoroutineScope(dispatchers.default)

}
