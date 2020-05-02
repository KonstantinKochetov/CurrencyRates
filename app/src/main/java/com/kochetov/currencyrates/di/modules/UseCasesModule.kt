package com.kochetov.currencyrates.di.modules

import com.kochetov.currencyrates.usecases.rates.CurrencyUseCases
import com.kochetov.currencyrates.usecases.rates.CurrencyUseCasesImpl
import com.kochetov.currencyrates.usecases.rates.api.CurrencyApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesModule {

    @Provides
    @Singleton
    fun providesCurrencyUseCases(api: CurrencyApi): CurrencyUseCases =
        CurrencyUseCasesImpl(api = api)
}