package com.kochetov.currencyrates.usecases.rates

import com.kochetov.currencyrates.usecases.rates.api.CurrencyApi
import com.kochetov.currencyrates.usecases.rates.model.Rate
import com.kochetov.currencyrates.usecases.rates.model.toRatesMap
import io.reactivex.Single

class CurrencyUseCasesImpl(private val api: CurrencyApi) : CurrencyUseCases {

    override fun getCurrencyRates(base: Rate): Single<Map<String, Rate>> =
        api.getRates(apiBase = base.currency.currencyCode)
            .map {
                return@map it.toRatesMap(
                    base = base
                )
            }
}