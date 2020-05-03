package com.kochetov.currencyrates.di.components

import android.content.Context
import com.kochetov.currencyrates.RatesApp
import com.kochetov.currencyrates.di.modules.CurrencyRatesModule
import com.kochetov.currencyrates.di.modules.NetworkModule
import com.kochetov.currencyrates.di.modules.UseCasesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        CurrencyRatesModule::class,
        NetworkModule::class,
        UseCasesModule::class]
)
interface ApplicationComponent : AndroidInjector<RatesApp> {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}