package com.kochetov.currencyrates.di

import android.content.Context
import com.kochetov.currencyrates.CurrencyRatesApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class,
        AndroidSupportInjectionModule::class]

)
interface ApplicationComponent: AndroidInjector<CurrencyRatesApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}