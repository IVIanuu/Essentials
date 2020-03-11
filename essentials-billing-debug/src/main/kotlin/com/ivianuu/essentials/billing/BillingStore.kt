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

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.SkuType
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.InternalPurchasesResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.Purchase.PurchasesResult
import com.android.billingclient.api.SkuDetails
import com.android.billingclient.api.SkuDetailsParams
import com.ivianuu.essentials.store.getValue
import com.ivianuu.essentials.util.AppCoroutineDispatchers
import com.ivianuu.injekt.Single
import com.ivianuu.injekt.android.ApplicationScope
import kotlinx.coroutines.withContext

@ApplicationScope
@Single
class BillingStore internal constructor(
    private val dispatchers: AppCoroutineDispatchers,
    prefs: BillingPrefs
) {

    private val products = prefs.products
    private val purchases = prefs.purchases

    suspend fun getSkuDetails(params: SkuDetailsParams): List<SkuDetails> = withContext(dispatchers.default) {
        products.getValue().filter { it.sku in params.skusList && it.type == params.skuType }
    }

    suspend fun getPurchases(@SkuType skuType: String): PurchasesResult = withContext(dispatchers.default) {
        InternalPurchasesResult(BillingResult.newBuilder().setResponseCode(BillingClient.BillingResponseCode.OK).build(),
            purchases.getValue().filter { it.signature.endsWith(skuType) })
    }

    suspend fun getPurchaseByToken(purchaseToken: String): Purchase? = withContext(dispatchers.default) {
        purchases.getValue().firstOrNull { it.purchaseToken == purchaseToken }
    }

    suspend fun addProduct(skuDetails: SkuDetails) = withContext(dispatchers.default) {
        products.getValue()
            .toMutableList()
            .also { it += skuDetails }
            .let { products.update(it) }
    }

    suspend fun removeProduct(sku: String) = withContext(dispatchers.default) {
        products.getValue()
            .filter { it.sku != sku }
            .let { products.update(it) }
    }

    suspend fun clearProducts() = withContext(dispatchers.default) {
        products.update(emptyList())
    }

    suspend fun addPurchase(purchase: Purchase) = withContext(dispatchers.default) {
        purchases.getValue()
            .toMutableList()
            .also { it += purchase }
            .let { purchases.update(it) }
    }

    suspend fun removePurchase(purchaseToken: String) = withContext(dispatchers.default) {
        purchases.getValue()
            .filter { it.purchaseToken != purchaseToken }
            .let { purchases.update(it) }
    }

    suspend fun clearPurchases() = withContext(dispatchers.default) {
        purchases.update(emptyList())
    }
}
