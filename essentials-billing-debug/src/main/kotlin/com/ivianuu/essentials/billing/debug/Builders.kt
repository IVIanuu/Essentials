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

package com.ivianuu.essentials.billing.debug

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.ivianuu.essentials.billing.Sku
import org.json.JSONObject

fun Purchase(
    orderId: String? = null,
    packageName: String? = null,
    sku: String,
    purchaseTime: Long = 831852000000,
    purchaseToken: String = "dummy_token",
    signature: String = "dummy_signature",
    isAcknowledged: Boolean? = null,
    isAutoRenewing: Boolean? = null,
    developerPayload: String? = null
): Purchase {
    val json = JSONObject()
    orderId?.let { json.put("orderId", it) }
    packageName?.let { json.put("packageName", it) }
    json.put("productId", sku)
    json.put("purchaseTime", purchaseTime)
    json.put("purchaseToken", purchaseToken)
    isAcknowledged?.let { json.put("acknowledged", it) }
    isAutoRenewing?.let { json.put("autoRenewing", it) }
    developerPayload?.let { json.put("developerPayload", it) }
    return Purchase(json.toString(), signature)
}

fun SkuDetails(
    sku: Sku,
    price: String = "0.99$",
    priceAmountMicros: Long = 3990000,
    priceCurrencyCode: String = "$",
    title: String = "Dummy title",
    description: String = "Dummy description",
    subscriptionPeriod: String? = null,
    freeTrialPeriod: String? = null,
    introductoryPrice: String? = null,
    introductoryPriceAmountMicros: String? = null,
    introductoryPricePeriod: String? = null,
    introductoryPriceCycles: String? = null
) = SkuDetails(
    sku = sku.skuString,
    type = sku.type.value,
    price = price,
    priceAmountMicros = priceAmountMicros,
    priceCurrencyCode = priceCurrencyCode,
    title = title,
    description = description,
    subscriptionPeriod = subscriptionPeriod,
    freeTrialPeriod = freeTrialPeriod,
    introductoryPrice = introductoryPrice,
    introductoryPriceAmountMicros = introductoryPriceAmountMicros,
    introductoryPricePeriod = introductoryPricePeriod,
    introductoryPriceCycles = introductoryPriceCycles
)

fun SkuDetails(
    sku: String,
    @BillingClient.SkuType type: String,
    price: String = "0.99$",
    priceAmountMicros: Long = 3990000,
    priceCurrencyCode: String = "$",
    title: String = "Dummy title",
    description: String = "Dummy description",
    subscriptionPeriod: String? = null,
    freeTrialPeriod: String? = null,
    introductoryPrice: String? = null,
    introductoryPriceAmountMicros: String? = null,
    introductoryPricePeriod: String? = null,
    introductoryPriceCycles: String? = null
): SkuDetails {
    val json = JSONObject()
        .put("productId", sku)
        .put("type", type)
        .put("price", price)
        .put("price_amount_micros", priceAmountMicros)
        .put("price_currency_code", priceCurrencyCode)
        .put("title", title)
        .put("description", description)

    subscriptionPeriod?.let { json.put("subscriptionPeriod", subscriptionPeriod) }
    freeTrialPeriod?.let { json.put("freeTrialPeriod", freeTrialPeriod) }
    introductoryPrice?.let { json.put("introductoryPrice", introductoryPrice) }
    introductoryPriceAmountMicros?.let {
        json.put(
            "introductoryPriceAmountMicros",
            introductoryPriceAmountMicros
        )
    }
    introductoryPricePeriod?.let {
        json.put(
            "introductoryPricePeriod",
            introductoryPricePeriod
        )
    }
    introductoryPriceCycles?.let {
        json.put(
            "introductoryPriceCycles",
            introductoryPriceCycles
        )
    }

    return SkuDetails(json.toString())
}