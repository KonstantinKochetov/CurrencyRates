package com.kochetov.currencyrates.usecases.rates

import com.kochetov.currencyrates.usecases.rates.model.Rate
import io.reactivex.Single

interface CurrencyUseCases {

    fun getCurrencyRates(base: Rate): Single<Map<String, Rate>>
}