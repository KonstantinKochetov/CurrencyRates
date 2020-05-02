package com.kochetov.currencyrates.di.modules

import androidx.lifecycle.ViewModel
import com.kochetov.currencyrates.di.viewmodel.ViewModelBuilder
import com.kochetov.currencyrates.di.viewmodel.ViewModelKey
import com.kochetov.currencyrates.modules.rates.RatesFragment
import com.kochetov.currencyrates.modules.rates.RatesViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class CurrencyRatesModule {

    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    abstract fun ratesFragment(): RatesFragment

    @Binds
    @IntoMap
    @ViewModelKey(RatesViewModel::class)
    abstract fun bindViewModel(viewModel: RatesViewModel): ViewModel
}