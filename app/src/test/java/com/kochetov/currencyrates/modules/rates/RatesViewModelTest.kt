package com.kochetov.currencyrates.modules.rates

import androidx.lifecycle.Observer
import com.kochetov.currencyrates.base.BaseTestClass
import com.kochetov.currencyrates.common.Outcome
import com.kochetov.currencyrates.modules.rates.RatesViewModel.Companion.INITIAL_BASE
import com.kochetov.currencyrates.usecases.rates.RatesUseCases
import com.kochetov.currencyrates.usecases.rates.model.Rate
import io.mockk.every
import io.mockk.verify
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class RatesViewModelTest : BaseTestClass() {

    lateinit var testScheduler: TestScheduler

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

    @Before
    override fun setUp() {
        super.setUp()
        testScheduler = TestScheduler()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
    }

    @Test
    fun test_get_current_rates() {
        viewModel.state.observeForever(stateObserver)

        every {
            ratesUseCases.getCurrencyRates(INITIAL_BASE)
        } returns Single.just(map)

        viewModel.getCurrencyRates()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(exactly = 1) { ratesUseCases.getCurrencyRates(INITIAL_BASE) }

        verify(exactly = 1) { stateObserver.onChanged(Outcome.success(map)) }
    }

    @Test
    fun test_change_base() {
        every {
            ratesUseCases.getCurrencyRates(rate)
        } returns Single.just(map)

        viewModel.changeBase(rate)

        verify(exactly = 1) { ratesUseCases.getCurrencyRates(rate) }
    }
}
