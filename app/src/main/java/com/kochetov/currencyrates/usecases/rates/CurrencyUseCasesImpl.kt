package com.kochetov.currencyrates.usecases.rates

import com.kochetov.currencyrates.usecases.rates.api.CurrencyApi
import com.kochetov.currencyrates.usecases.rates.model.CurrencyRates
import com.kochetov.currencyrates.usecases.rates.model.toCurrencyRates
import io.reactivex.Single

class CurrencyUseCasesImpl(private val api: CurrencyApi): CurrencyUseCases {

    override fun getCurrencyRates(base: String): Single<CurrencyRates> =
        api.getRates(base = base)
            .map {
                return@map it.toCurrencyRates()
            }

}