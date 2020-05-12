package com.kochetov.currencyrates.modules.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kochetov.currencyrates.common.Outcome
import com.kochetov.currencyrates.usecases.rates.RatesUseCases
import com.kochetov.currencyrates.usecases.rates.model.Rate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.Currency
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RatesViewModel @Inject constructor(
    private val ratesUseCases: RatesUseCases
) : ViewModel() {

    companion object {
        val INITIAL_BASE = Rate(
            currency = Currency.getInstance("EUR"),
            amount = 1.toBigDecimal(),
            imageResString = "currency_flag_eur"
        )
    }

    val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    private var base: Rate

    init {
        base = INITIAL_BASE
    }

    private val _state = MutableLiveData<Outcome<Map<String, Rate>>>()
    val state: LiveData<Outcome<Map<String, Rate>>> = _state

    fun getCurrencyRates() {
        ratesUseCases.getCurrencyRates(base = base)
            .delay(1, TimeUnit.SECONDS)
            .repeat()
            .retry()
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

    fun changeBase(base: Rate) {
        disposables.clear()
        this.base = base
        getCurrencyRates()
    }
}
