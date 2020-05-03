package com.kochetov.currencyrates.modules.rates

import androidx.lifecycle.Observer
import com.kochetov.currencyrates.base.BaseTestClass
import com.kochetov.currencyrates.common.Outcome
import com.kochetov.currencyrates.usecases.rates.RatesUseCases
import com.kochetov.currencyrates.usecases.rates.model.Rate
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Test

class NewsViewModelTest: BaseTestClass() {

    @MockK
    lateinit var ratesUseCases: RatesUseCases

    @MockK
    lateinit var stateObserver: Observer<Outcome<Map<String, Rate>>>

    @MockK
    lateinit var rate: Rate

    @MockK
    lateinit var map: Map<String, Rate>

    private val viewModel: RatesViewModel by lazy {
        RatesViewModel(
            ratesUseCases
        )
    }

    @Test
    fun test_get_current_rates_success() {
        viewModel.state.observeForever(stateObserver)

        every {
            ratesUseCases.getCurrencyRates(RatesViewModel.INITIAL_BASE)
        } returns Single.just(map)

        viewModel.getCurrentRates()
        viewModel.disposables.clear()

//        verify (exactly = 1) {
//            ratesUseCases.getCurrencyRates(rate)
//        }
//
//        verify { stateObserver.onChanged(Outcome.success(map)) }
    }

}

