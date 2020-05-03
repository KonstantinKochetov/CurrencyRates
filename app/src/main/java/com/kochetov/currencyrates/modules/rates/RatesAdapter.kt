package com.kochetov.currencyrates.modules.rates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.kochetov.currencyrates.R
import com.kochetov.currencyrates.usecases.rates.model.Rate
import kotlinx.android.synthetic.main.rate_item.view.*

class RatesAdapter(private val viewModel: RatesViewModel) :
    RecyclerView.Adapter<RatesAdapter.ViewHolder>() {

    companion object {
        const val PAYLOAD_UPDATE = 1
    }

    private var items = mutableListOf<Rate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rate_item, parent, false),
            viewModel
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            items[position]
        ) { index ->
            val temp = items[index]
            items.removeAt(index)
            items.add(0, temp)
            notifyItemMoved(index, 0)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            when (payloads[0]) {
                PAYLOAD_UPDATE -> {
                    if (position != 0) {
                        holder.updateAmount(items[position])
                    }
                }
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    class ViewHolder(view: View, private val viewModel: RatesViewModel) :
        RecyclerView.ViewHolder(view) {

        fun bind(
            rate: Rate,
            updateList: (Int) -> Unit
        ) {
            itemView.iv_flag.setImageResource(
                itemView.context.applicationContext.resources.getIdentifier(
                    rate.imageResString,
                    "drawable",
                    itemView.context.applicationContext.packageName
                )
            )
            itemView.tv_currency_code.text = rate.currency.currencyCode
            itemView.tv_currency_name.text = rate.currency.displayName
            if (!itemView.et_currency_amount.isFocused) {
                itemView.et_currency_amount.setText(rate.amount.toString())
            }

            itemView.setOnClickListener {
                if (!itemView.et_currency_amount.isFocused) itemView.et_currency_amount.requestFocus()
            }

            itemView.et_currency_amount.setOnFocusChangeListener { _, isFocused ->
                if (isFocused) {
                    updateList(adapterPosition)
                    viewModel.changeBase(base = rate)
                }
            }

            itemView.et_currency_amount.doAfterTextChanged {
                if (itemView.et_currency_amount.isFocused) {
                    viewModel.changeBase(
                        base = Rate(
                            currency = rate.currency,
                            amount = it.toString().toDoubleOrNull() ?: 0.0,
                            imageResString = rate.imageResString
                        )
                    )
                }
            }
        }

        fun updateAmount(rate: Rate) {
            if (!itemView.et_currency_amount.isFocused) {
                itemView.et_currency_amount.setText(rate.amount.toString())
            }
        }
    }

    fun addMap(map: Map<String, Rate>) {
        val newItems = mutableListOf<Rate>()

        when {
            items.isEmpty() -> {
                newItems.addAll(map.values)
                items = newItems
                notifyDataSetChanged()
            }
            else -> {
                items.forEachIndexed { index, item ->
                    map[item.currency.currencyCode]?.let { rate ->
                        newItems.add(rate)
                        notifyItemChanged( // updating only et_currency_amount view instead of redrawing all views
                            index,
                            PAYLOAD_UPDATE
                        )
                    }
                }
                items = newItems
            }
        }
    }
}
