package com.kochetov.currencyrates.usecases.rates

import com.kochetov.currencyrates.usecases.rates.model.CurrencyRates
import io.reactivex.Single

interface CurrencyUseCases {

    fun getCurrencyRates(base: String): Single<CurrencyRates>
}