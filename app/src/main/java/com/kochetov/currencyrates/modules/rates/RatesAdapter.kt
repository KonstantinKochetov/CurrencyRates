package com.kochetov.currencyrates.modules.rates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kochetov.currencyrates.R
import com.kochetov.currencyrates.extensions.AdapterHelper
import com.kochetov.currencyrates.usecases.rates.model.Rate
import kotlinx.android.synthetic.main.rate_item.view.*

class RatesAdapter(private val viewModel: RatesViewModel) :
    RecyclerView.Adapter<RatesAdapter.ViewHolder>(),
    AdapterHelper<Rate> {

    private var items = mutableListOf<Rate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rate_item, parent, false),
            viewModel
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(view: View, viewModel: RatesViewModel) : RecyclerView.ViewHolder(view) {
        fun bind(rate: Rate) {
            itemView.iv_flag.setImageResource(
                itemView.context.applicationContext.resources.getIdentifier(
                    rate.imageResString,
                    "drawable",
                    itemView.context.applicationContext.packageName
                )
            )
            itemView.tv_currency_code.text = rate.currency.currencyCode
            itemView.tv_currency_name.text = rate.currency.displayName
            itemView.et_currency_amount.setText(rate.amount.toString())
        }
    }

    override fun addAll(list: MutableList<Rate>) {
        items = list // less work on the UI thread this way
        notifyDataSetChanged()
    }

}
