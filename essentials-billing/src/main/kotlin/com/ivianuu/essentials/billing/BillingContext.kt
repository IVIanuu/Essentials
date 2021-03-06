package com.ivianuu.essentials.billing

import com.android.billingclient.api.*
import com.github.michaelbull.result.*
import com.ivianuu.essentials.*
import com.ivianuu.essentials.coroutines.*
import com.ivianuu.essentials.logging.*
import com.ivianuu.essentials.optics.*
import com.ivianuu.essentials.util.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.coroutines.*
import com.ivianuu.injekt.scope.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.*
import java.io.*
import kotlin.coroutines.*

interface BillingContext {
  val billingClient: BillingClient
  val logger: Logger
  val refreshes: MutableSharedFlow<BillingRefresh>

  suspend fun <R> withConnection(block: suspend BillingContext.() -> R): R?
}

@Provide @Scoped<AppScope>
class BillingContextImpl(
  override val billingClient: BillingClient,
  private val dispatcher: IODispatcher,
  override val logger: Logger,
  override val refreshes: MutableSharedFlow<BillingRefresh>,
  private val scope: InjektCoroutineScope<AppScope>
) : BillingContext {
  private var isConnected = false
  private val connectionMutex = Mutex()

  override suspend fun <R> withConnection(block: suspend BillingContext.() -> R): R? =
    withContext(scope.coroutineContext + dispatcher) {
      ensureConnected()
        ?.let { block() }
    }

  private suspend fun ensureConnected(): Unit? = connectionMutex.withLock {
    if (isConnected) return@withLock
    suspendCoroutine<Unit?> { continuation ->
      d { "start connection" }
      billingClient.startConnection(
        object : BillingClientStateListener {
          private var completed = false
          override fun onBillingSetupFinished(result: BillingResult) {
            // for some reason on billing setup finished is sometimes called multiple times
            // we ensure that we we only resume once
            if (!completed) {
              completed = true
              if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                d { "connected" }
                isConnected = true
                continuation.resume(Unit)
              } else {
                d { "connecting failed ${result.responseCode} ${result.debugMessage}" }
                continuation.resume(null)
              }
            }
          }

          override fun onBillingServiceDisconnected() {
            d { "on billing service disconnected" }
          }
        }
      )
    }
  }
}
