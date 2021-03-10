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

package com.ivianuu.essentials.billing.debug

import android.app.Activity
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.AcknowledgePurchaseResponseListener
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ConsumeResponseListener
import com.android.billingclient.api.InternalPurchasesResult
import com.android.billingclient.api.PriceChangeConfirmationListener
import com.android.billingclient.api.PriceChangeFlowParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchaseHistoryRecord
import com.android.billingclient.api.PurchaseHistoryResponseListener
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.SkuDetails
import com.android.billingclient.api.SkuDetailsParams
import com.android.billingclient.api.SkuDetailsResponseListener
import com.ivianuu.essentials.billing.Sku
import com.ivianuu.essentials.billing.debug.DebugBillingClient.ClientState.CLOSED
import com.ivianuu.essentials.billing.debug.DebugBillingClient.ClientState.CONNECTED
import com.ivianuu.essentials.billing.debug.DebugBillingClient.ClientState.DISCONNECTED
import com.ivianuu.essentials.coroutines.GlobalScope
import com.ivianuu.essentials.datastore.android.PrefUpdater
import com.ivianuu.essentials.store.Collector
import com.ivianuu.essentials.ui.navigation.NavigationAction
import com.ivianuu.essentials.ui.navigation.pushForResult
import com.ivianuu.essentials.util.AppUiStarter
import com.ivianuu.essentials.util.BuildInfo
import com.ivianuu.injekt.Given
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Date

@Given
class DebugBillingClient(
    @Given private val appUiStarter: AppUiStarter,
    @Given private val buildInfo: BuildInfo,
    @Given private val globalScope: GlobalScope,
    @Given private val navigator: Collector<NavigationAction>,
    @Given private val prefs: Flow<DebugBillingPrefs>,
    @Given private val updatePrefs: PrefUpdater<DebugBillingPrefs>,
    @Given private val purchasesUpdatedListener: PurchasesUpdatedListener
) : BillingClient() {

    private var billingClientStateListener: BillingClientStateListener? = null

    private enum class ClientState {
        DISCONNECTED,
        CONNECTING,
        CONNECTED,
        CLOSED
    }

    private var clientState = DISCONNECTED

    override fun isReady(): Boolean = clientState == CONNECTED

    override fun startConnection(listener: BillingClientStateListener) {
        if (isReady) {
            listener.onBillingSetupFinished(
                BillingResult.newBuilder().setResponseCode(BillingResponseCode.OK).build()
            )
            return
        }

        if (clientState == CLOSED) {
            listener.onBillingSetupFinished(BillingResult.newBuilder().setResponseCode(BillingResponseCode.DEVELOPER_ERROR).build())
            return
        }

        this.billingClientStateListener = listener
        clientState = CONNECTED
        listener.onBillingSetupFinished(
            BillingResult.newBuilder().setResponseCode(
                BillingResponseCode.OK
            ).build()
        )
    }

    override fun endConnection() {
        billingClientStateListener?.onBillingServiceDisconnected()
        clientState = CLOSED
    }

    override fun isFeatureSupported(feature: String): BillingResult {
        return if (!isReady) {
            BillingResult.newBuilder().setResponseCode(BillingResponseCode.SERVICE_DISCONNECTED)
                .build()
        } else {
            BillingResult.newBuilder().setResponseCode(BillingResponseCode.OK).build()
        }
    }

    override fun consumeAsync(consumeParams: ConsumeParams, listener: ConsumeResponseListener) {
        val purchaseToken = consumeParams.purchaseToken
        if (purchaseToken.isEmpty()) {
            listener.onConsumeResponse(
                BillingResult.newBuilder().setResponseCode(
                    BillingResponseCode.DEVELOPER_ERROR
                ).build(),
                purchaseToken
            )
            return
        }

        globalScope.launch {
            val purchase = prefs.first().purchases.firstOrNull {
                it.purchaseToken == purchaseToken
            }
            if (purchase != null) {
                updatePrefs {
                    copy(purchases = purchases.filterNot { it.purchaseToken == purchaseToken })
                }
                listener.onConsumeResponse(
                    BillingResult.newBuilder().setResponseCode(
                        BillingResponseCode.OK
                    ).build(),
                    purchaseToken
                )
            } else {
                listener.onConsumeResponse(
                    BillingResult.newBuilder().setResponseCode(
                        BillingResponseCode.ITEM_NOT_OWNED
                    ).build(),
                    purchaseToken
                )
            }
        }
    }

    override fun launchBillingFlow(activity: Activity, params: BillingFlowParams): BillingResult {
        globalScope.launch {
            appUiStarter()
            val purchasedSkuDetails = navigator.pushForResult(
                DebugPurchaseKey(
                    Sku(skuString = params.sku,
                        type = Sku.Type
                            .values()
                            .single { it.value == params.skuType }
                    )
                )
            )

            if (purchasedSkuDetails != null) {
                val purchase = purchasedSkuDetails.toPurchase()
                updatePrefs {
                    copy(purchases = purchases + purchase)
                }
                purchasesUpdatedListener.onPurchasesUpdated(
                    BillingResult.newBuilder().setResponseCode(
                        BillingResponseCode.OK
                    ).build(),
                    listOf(purchase)
                )

            } else {
                purchasesUpdatedListener.onPurchasesUpdated(
                    BillingResult.newBuilder().setResponseCode(
                        BillingResponseCode.USER_CANCELED
                    ).build(),
                    null
                )
            }
        }

        return BillingResult.newBuilder().setResponseCode(BillingResponseCode.OK).build()
    }

    override fun queryPurchaseHistoryAsync(
        skuType: String,
        listener: PurchaseHistoryResponseListener
    ) {
        if (!isReady) {
            listener.onPurchaseHistoryResponse(
                BillingResult.newBuilder().setResponseCode(
                    BillingResponseCode.SERVICE_DISCONNECTED
                ).build(),
                null
            )
            return
        }
        globalScope.launch {
            val history = queryPurchases(skuType)
            listener.onPurchaseHistoryResponse(
                BillingResult.newBuilder()
                    .setResponseCode(history.responseCode).build(),
                history.purchasesList?.map { PurchaseHistoryRecord(it.originalJson, it.signature) }
                    ?: emptyList()
            )
        }
    }

    override fun querySkuDetailsAsync(
        params: SkuDetailsParams,
        listener: SkuDetailsResponseListener
    ) {
        if (!isReady) {
            listener.onSkuDetailsResponse(
                BillingResult.newBuilder().setResponseCode(
                    BillingResponseCode.SERVICE_DISCONNECTED
                ).build(),
                null
            )
            return
        }
        globalScope.launch {
            listener.onSkuDetailsResponse(
                BillingResult.newBuilder().setResponseCode(
                    BillingResponseCode.OK
                ).build(),
                prefs.first()
                    .products
                    .filter { it.sku in params.skusList && it.type == params.skuType }
            )
        }
    }

    override fun queryPurchases(skuType: String): Purchase.PurchasesResult {
        if (!isReady) {
            return InternalPurchasesResult(
                BillingResult.newBuilder().setResponseCode(
                    BillingResponseCode.SERVICE_DISCONNECTED
                ).build(),
                null
            )
        }
        if (skuType.isEmpty()) {
            return InternalPurchasesResult(
                BillingResult.newBuilder().setResponseCode(
                    BillingResponseCode.DEVELOPER_ERROR
                ).build(),
                null
            )
        }
        return runBlocking {
            InternalPurchasesResult(
                BillingResult.newBuilder()
                    .setResponseCode(BillingResponseCode.OK).build(),
                prefs.first().purchases.filter { it.signature.endsWith(skuType) }
            )
        }
    }

    override fun launchPriceChangeConfirmationFlow(
        activity: Activity,
        params: PriceChangeFlowParams,
        listener: PriceChangeConfirmationListener
    ) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun acknowledgePurchase(
        params: AcknowledgePurchaseParams,
        listener: AcknowledgePurchaseResponseListener
    ) {
        val purchaseToken = params.purchaseToken
        if (purchaseToken.isEmpty()) {
            listener.onAcknowledgePurchaseResponse(
                BillingResult.newBuilder().setResponseCode(
                    BillingResponseCode.DEVELOPER_ERROR
                ).build()
            )
            return
        }

        globalScope.launch {
            val purchase = prefs.first().purchases.singleOrNull {
                it.purchaseToken == purchaseToken
            }
            if (purchase != null) {
                val updated = Purchase(
                    orderId = purchase.orderId,
                    packageName = purchase.packageName,
                    sku = purchase.sku,
                    purchaseTime = purchase.purchaseTime,
                    purchaseToken = purchase.purchaseToken,
                    signature = purchase.signature,
                    isAcknowledged = true,
                    isAutoRenewing = purchase.isAutoRenewing,
                    developerPayload = purchase.developerPayload
                )
                updatePrefs {
                    copy(purchases = purchases + updated)
                }
                listener.onAcknowledgePurchaseResponse(
                    BillingResult.newBuilder().setResponseCode(
                        BillingResponseCode.OK
                    ).build()
                )
            } else {
                listener.onAcknowledgePurchaseResponse(
                    BillingResult.newBuilder().setResponseCode(
                        BillingResponseCode.ITEM_NOT_OWNED
                    ).build()
                )
            }
        }
    }

    private fun SkuDetails.toPurchase(): Purchase {
        val json =
            """{"orderId":"$sku","packageName":"${buildInfo.packageName}","productId":
      |"$sku","autoRenewing":true,"purchaseTime":"${Date().time}","acknowledged":false,"purchaseToken":
      |"0987654321", "purchaseState":1}""".trimMargin()
        return Purchase(json, "debug-signature-$sku-$type")
    }
}
