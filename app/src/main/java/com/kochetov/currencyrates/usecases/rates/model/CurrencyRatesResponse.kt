package com.kochetov.currencyrates.usecases.rates.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class CurrencyRatesResponse(
    @SerializedName("baseCurrency") val baseCurrency: String,
    @SerializedName("rates") val rates: Map<String, Double>
) : Serializable

fun CurrencyRatesResponse.toCurrencyRates() = CurrencyRates(this.baseCurrency, this.rates.toList())

data class CurrencyRates(
    val baseCurrency: String,
    val rates: MutableList<Rate>
)

data class Rate(val currency: Currency, val amount: Double, val imageResString: String)

fun Map<String, Double>.toList(): MutableList<Rate> {

    val list = mutableListOf<Rate>()

    this.forEach {
        list.add(
            Rate(
                currency = Currency.getInstance(it.key),
                amount = "%.2f".format(it.value).toDouble(),
                imageResString = "currency_flag_${it.key.toLowerCase(Locale.getDefault())}"
            )
        )
    }
    return list
}



