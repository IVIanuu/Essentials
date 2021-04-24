package com.ivianuu.essentials.foreground

import com.ivianuu.essentials.coroutines.*
import com.ivianuu.essentials.logging.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.coroutines.*
import com.ivianuu.injekt.scope.*
import kotlinx.coroutines.flow.*

data class ForegroundInfo(val id: Int, val state: ForegroundState)

data class InternalForegroundState(val infos: List<ForegroundInfo>) {
    val isForeground: Boolean get() = infos.any { it.state is ForegroundState.Foreground }
}

@Given
fun internalForegroundState(
    @Given foregroundStates: Set<Flow<ForegroundState>> = emptySet(),
    @Given logger: Logger,
    @Given scope: GivenCoroutineScope<AppGivenScope>,
): @Scoped<AppGivenScope> Flow<InternalForegroundState> = combine(
    foregroundStates
        .mapIndexed { index, foregroundState ->
            foregroundState
                .onStart { emit(ForegroundState.Background) }
                .map { ForegroundInfo(index + 1, it) }
                .distinctUntilChanged()
        }
) { currentForegroundStates -> InternalForegroundState(currentForegroundStates.toList()) }
    .onEach { current ->
        logger.d { "Internal foreground state changed $current" }
    }
    .shareIn(scope, SharingStarted.WhileSubscribed(), 1)
    .distinctUntilChanged()
