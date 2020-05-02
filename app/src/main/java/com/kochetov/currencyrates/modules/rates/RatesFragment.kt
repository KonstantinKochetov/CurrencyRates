package com.kochetov.currencyrates.modules.rates

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kochetov.currencyrates.R
import com.kochetov.currencyrates.extensions.Outcome
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_rates.*
import javax.inject.Inject

class RatesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<RatesViewModel> { viewModelFactory }

    private lateinit var ratesAdapter: RatesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_rates, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeAdapter()
        startViewModel()
    }

    private fun initializeAdapter() {
        ratesAdapter = RatesAdapter(viewModel)
        rv_rates_list.apply {
            adapter = ratesAdapter
        }
    }

    private fun startViewModel() {
        viewModel.getCurrentRates()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, Observer { outcome ->
            when (outcome) {
                is Outcome.Success -> {
                    showDataView()
                    Log.d("kok", "Success -> ${outcome.data}")
                    if (::ratesAdapter.isInitialized) {
                        ratesAdapter.addAll(outcome.data.rates)
                    }
                }

                is Outcome.Progress -> {
                    Log.d("kok", "Progress -> ${outcome.loading}")
                    if (outcome.loading) {
                        showLoadingView()
                    }
                }

                is Outcome.Failure -> {
                    Log.d("kok", "Failure -> ${outcome.e}")
                    showDataView()
                }
            }
        })

    }

    private fun showDataView() {
        pc_progress.visibility = View.INVISIBLE
        tv_title.visibility = View.VISIBLE
        rv_rates_list.visibility = View.VISIBLE
    }

    private fun showLoadingView() {
        pc_progress.visibility = View.VISIBLE
        tv_title.visibility = View.INVISIBLE
        rv_rates_list.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.disposables.clear()
    }
}
