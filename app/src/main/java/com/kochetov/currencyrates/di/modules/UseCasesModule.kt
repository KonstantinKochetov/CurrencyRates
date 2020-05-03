package com.kochetov.currencyrates.di.modules

import com.kochetov.currencyrates.usecases.rates.RatesUseCases
import com.kochetov.currencyrates.usecases.rates.RatesUseCasesImpl
import com.kochetov.currencyrates.usecases.rates.api.RatesApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesModule {

    @Provides
    @Singleton
    fun providesCurrencyUseCases(api: RatesApi): RatesUseCases =
        RatesUseCasesImpl(api = api)
}