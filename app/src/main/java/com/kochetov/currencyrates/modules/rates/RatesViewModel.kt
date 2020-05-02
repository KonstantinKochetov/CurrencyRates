package com.kochetov.currencyrates.modules.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kochetov.currencyrates.extensions.*
import com.kochetov.currencyrates.usecases.rates.CurrencyUseCases
import com.kochetov.currencyrates.usecases.rates.model.CurrencyRates
import com.kochetov.currencyrates.usecases.rates.model.CurrencyRatesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RatesViewModel @Inject constructor(
    private val currencyUseCases: CurrencyUseCases
) : ViewModel() {

    val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    private val _state = MutableLiveData<Outcome<CurrencyRates>>()
    val state: LiveData<Outcome<CurrencyRates>> = _state

    fun getCurrentRates(base: String = "EUR") {
        currencyUseCases.getCurrencyRates(base)
            .delay(1, TimeUnit.SECONDS)
            .repeat()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _state.postValue(Outcome.success(it))
            }, { error ->
                error?.let {
                    _state.postValue(Outcome.failure(it))
                }
            }).addTo(disposables)
    }

}