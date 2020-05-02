package com.kochetov.currencyrates.usecases.rates.model

import com.google.gson.annotations.SerializedName
import com.kochetov.currencyrates.usecases.rates.model.CurrencyRatesResponse.Companion.FLAG_PREFIX
import java.io.Serializable
import java.util.*

data class CurrencyRatesResponse(
    @SerializedName("baseCurrency") val baseCurrency: String,
    @SerializedName("rates") val rates: MutableMap<String, Double>
) : Serializable {
    companion object {
        const val FLAG_PREFIX = "currency_flag_"
    }
}

data class Rate(val currency: Currency, val amount: Double, val imageResString: String)

/**
 * Helper functions
 */
fun CurrencyRatesResponse.toRatesMap(base: Rate): Map<String, Rate> {
    val map = mutableMapOf<String, Rate>()
    // insert base
    map[base.currency.currencyCode] = base

    // insert the rest of currencies
    rates.forEach {
        map[it.key] = Rate(
            currency = Currency.getInstance(it.key),
            amount = "%.2f".format(it.value).toDouble(),
            imageResString = "$FLAG_PREFIX${it.key.toLowerCase(Locale.getDefault())}"
        )
    }
    return map
}



