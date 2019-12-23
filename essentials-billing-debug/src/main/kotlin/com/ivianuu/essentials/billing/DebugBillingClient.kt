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

package com.ivianuu.essentials.billing

import android.app.Activity
import android.content.Context
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
import com.android.billingclient.api.RewardLoadParams
import com.android.billingclient.api.RewardResponseListener
import com.android.billingclient.api.SkuDetails
import com.android.billingclient.api.SkuDetailsParams
import com.android.billingclient.api.SkuDetailsResponseListener
import com.ivianuu.essentials.billing.DebugBillingClient.ClientState.CLOSED
import com.ivianuu.essentials.billing.DebugBillingClient.ClientState.CONNECTED
import com.ivianuu.essentials.billing.DebugBillingClient.ClientState.DISCONNECTED
import com.ivianuu.injekt.Param
import com.ivianuu.injekt.Single
import com.ivianuu.injekt.android.ApplicationScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@ApplicationScope
@Single
class DebugBillingClient(
    private val context: Context,
    @Param private val purchasesUpdatedListener: PurchasesUpdatedListener,
    private val billingStore: BillingStore
) : BillingClient() {

    private var billingClientStateListener: BillingClientStateListener? = null

    private enum class ClientState {
        DISCONNECTED,
        CONNECTING,
        CONNECTED,
        CLOSED
    }

    private val requests = ConcurrentHashMap<String, PurchaseRequest>()

    private var clientState = DISCONNECTED

    override fun isReady(): Boolean = clientState == CONNECTED

    override fun startConnection(listener: BillingClientStateListener) {
        if (isReady) {
            listener.onBillingSetupFinished(BillingResult.newBuilder().setResponseCode(BillingClient.BillingResponseCode.OK).build())
            return
        }

        if (clientState == CLOSED) {
            listener.onBillingSetupFinished(BillingResult.newBuilder().setResponseCode(BillingClient.BillingResponseCode.DEVELOPER_ERROR).build())
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

    override fun isFeatureSupported(feature: String?): BillingResult {
        // TODO Update BillingStore to allow feature enable/disable
        return if (!isReady) {
            BillingResult.newBuilder().setResponseCode(BillingResponseCode.SERVICE_DISCONNECTED)
                .build()
        } else {
            BillingResult.newBuilder().setResponseCode(BillingResponseCode.OK).build()
        }
    }

    override fun consumeAsync(consumeParams: ConsumeParams?, listener: ConsumeResponseListener) {
        val purchaseToken = consumeParams?.purchaseToken
        if (purchaseToken == null || purchaseToken.isBlank()) {
            listener.onConsumeResponse(
                BillingResult.newBuilder().setResponseCode(
                    BillingResponseCode.DEVELOPER_ERROR
                ).build(), purchaseToken
            )
            return
        }

        GlobalScope.launch {
            val purchase = billingStore.getPurchaseByToken(purchaseToken)
            if (purchase != null) {
                billingStore.removePurchase(purchase.purchaseToken)
                listener.onConsumeResponse(
                    BillingResult.newBuilder().setResponseCode(
                        BillingResponseCode.OK
                    ).build(), purchaseToken
                )
            } else {
                listener.onConsumeResponse(
                    BillingResult.newBuilder().setResponseCode(
                        BillingResponseCode.ITEM_NOT_OWNED
                    ).build(), purchaseToken
                )
            }
        }
    }

    override fun launchBillingFlow(activity: Activity?, params: BillingFlowParams?): BillingResult {
        if (params == null) return BillingResult.newBuilder().setResponseCode(BillingResponseCode.DEVELOPER_ERROR).build()

        val requestId = UUID.randomUUID().toString()
        val request = PurchaseRequest(params.sku, params.skuType!!)
        requests[requestId] = request
        DebugBillingActivity.purchase(context, requestId)

        return BillingResult.newBuilder().setResponseCode(BillingResponseCode.OK).build()
    }

    override fun queryPurchaseHistoryAsync(
        skuType: String?,
        listener: PurchaseHistoryResponseListener
    ) {
        if (!isReady) {
            listener.onPurchaseHistoryResponse(
                BillingResult.newBuilder().setResponseCode(
                    BillingResponseCode.SERVICE_DISCONNECTED
                ).build(), null
            )
            return
        }
        GlobalScope.launch {
            val history = queryPurchases(skuType)
            listener.onPurchaseHistoryResponse(BillingResult.newBuilder().setResponseCode(history.responseCode).build(),
                history.purchasesList.map { PurchaseHistoryRecord(it.originalJson, it.signature) })
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
                ).build(), null
            )
            return
        }
        GlobalScope.launch {
            listener.onSkuDetailsResponse(
                BillingResult.newBuilder().setResponseCode(
                    BillingResponseCode.OK
                ).build(), billingStore.getSkuDetails(params)
            )
        }
    }

    override fun queryPurchases(@SkuType skuType: String?): Purchase.PurchasesResult {
        if (!isReady) {
            return InternalPurchasesResult(
                BillingResult.newBuilder().setResponseCode(
                    BillingResponseCode.SERVICE_DISCONNECTED
                ).build(), null
            )
        }
        if (skuType == null || skuType.isBlank()) {
            return InternalPurchasesResult(
                BillingResult.newBuilder().setResponseCode(
                    BillingResponseCode.DEVELOPER_ERROR
                ).build(), /* purchasesList */ null
            )
        }
        return billingStore.getPurchases(skuType)
    }

    override fun launchPriceChangeConfirmationFlow(
        activity: Activity?,
        params: PriceChangeFlowParams?,
        listener: PriceChangeConfirmationListener
    ) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun loadRewardedSku(params: RewardLoadParams?, listener: RewardResponseListener) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun acknowledgePurchase(
        params: AcknowledgePurchaseParams?,
        listener: AcknowledgePurchaseResponseListener?
    ) {
        val purchaseToken = params?.purchaseToken
        if (purchaseToken == null || purchaseToken.isBlank()) {
            listener?.onAcknowledgePurchaseResponse(
                BillingResult.newBuilder().setResponseCode(
                    BillingResponseCode.DEVELOPER_ERROR
                ).build()
            )
            return
        }

        GlobalScope.launch {
            val purchase = billingStore.getPurchaseByToken(purchaseToken)
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
                billingStore.addPurchase(updated)
                listener?.onAcknowledgePurchaseResponse(
                    BillingResult.newBuilder().setResponseCode(
                        BillingResponseCode.OK
                    ).build()
                )
            } else {
                listener?.onAcknowledgePurchaseResponse(
                    BillingResult.newBuilder().setResponseCode(
                        BillingResponseCode.ITEM_NOT_OWNED
                    ).build()
                )
            }
        }
    }

    internal fun getSkuDetailsForRequest(requestId: String): SkuDetails? {
        val request = requests[requestId] ?: return null
        return billingStore.getSkuDetails(
            SkuDetailsParams.newBuilder()
                .setType(request.skuType)
                .setSkusList(listOf(request.sku))
                .build()
        ).firstOrNull()
    }

    internal fun onPurchaseResult(
        requestId: String,
        responseCode: Int,
        purchases: List<Purchase>?
    ) {
        requests -= requestId

        if (responseCode == BillingResponseCode.OK) {
            purchases!!.forEach { billingStore.addPurchase(it) }
        }

        purchasesUpdatedListener.onPurchasesUpdated(
            BillingResult.newBuilder().setResponseCode(
                responseCode
            ).build(), purchases
        )
    }
}
