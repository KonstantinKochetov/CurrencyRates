package com.kochetov.currencyrates.usecases.rates.model

import com.google.gson.annotations.SerializedName
import com.kochetov.currencyrates.usecases.rates.model.RatesResponse.Companion.FLAG_PREFIX
import java.io.Serializable
import java.math.BigDecimal
import java.util.Currency
import java.util.Locale

data class RatesResponse(
    @SerializedName("baseCurrency") val baseCurrency: String,
    @SerializedName("rates") val rates: MutableMap<String, BigDecimal>
) : Serializable {
    companion object {
        const val FLAG_PREFIX = "currency_flag_"
    }
}

data class Rate(val currency: Currency, val amount: BigDecimal, val imageResString: String)

/**
 * Helper functions
 */
fun RatesResponse.toRatesMap(base: Rate): Map<String, Rate> {
    val map = mutableMapOf<String, Rate>()
    // insert base
    map[base.currency.currencyCode] = base

    // insert the rest of currencies
    rates.forEach {
        map[it.key] = Rate(
            currency = Currency.getInstance(it.key),
            amount =  it.value * base.amount,
            imageResString = "$FLAG_PREFIX${it.key.toLowerCase(Locale.getDefault())}"
        )
    }
    return map
}