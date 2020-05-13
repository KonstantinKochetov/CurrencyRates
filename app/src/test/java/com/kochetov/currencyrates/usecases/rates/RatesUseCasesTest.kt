package com.kochetov.currencyrates.usecases.rates

import com.kochetov.currencyrates.base.BaseTestClass
import com.kochetov.currencyrates.usecases.rates.api.RatesApi
import com.kochetov.currencyrates.usecases.rates.model.Rate
import com.kochetov.currencyrates.usecases.rates.model.RatesResponse
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Test
import java.math.BigDecimal
import java.util.Currency

class RatesUseCasesTest : BaseTestClass() {

    companion object {
        const val BASE_CODE = "EUR"
        val BASE_AMOUNT = BigDecimal(1)
        const val USD_CODE = "USD"
        val USD_AMOUNT = BigDecimal(1.13)
    }

    @MockK
    lateinit var api: RatesApi

    private val baseRate = Rate(
        currency = Currency.getInstance(BASE_CODE),
        amount = BASE_AMOUNT,
        imageResString = "test"
    )

    private val useCases: RatesUseCases by lazy {
        RatesUseCasesImpl(
            api
        )
    }

    private val response: RatesResponse = RatesResponse(
        baseCurrency = BASE_CODE,
        rates = mutableMapOf(
            USD_CODE to BigDecimal(1.13)
        )
    )

    @Test
    fun test_get_currency_rates_success() {
        every {
            api.getRates(baseRate.currency.currencyCode)
        } returns Single.just(response)

        useCases.getCurrencyRates(baseRate)
            .test()
            .assertValue {
                it[USD_CODE]?.amount == USD_AMOUNT
            }
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun test_get_currency_rates_error() {
        val error = Throwable("test_get_currency_rates_error")

        every {
            api.getRates(baseRate.currency.currencyCode)
        } returns Single.error(error)

        useCases.getCurrencyRates(baseRate)
            .test()
            .assertError(error)
            .assertNotComplete()
    }
}
