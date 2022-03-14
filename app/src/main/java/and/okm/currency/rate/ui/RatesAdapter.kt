package and.okm.currency.rate.ui

import and.okm.currency.rate.data.RatesResponse
import and.okm.currency.rate.databinding.AdapterRateBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class RatesAdapter @Inject constructor() : RecyclerView.Adapter<MainViewHolder>() {

    private lateinit var binding: AdapterRateBinding

    var rates = mutableListOf<Pair<String, Double>>()

    fun setRates(ratesResponse: RatesResponse) {
        this.rates = ratesResponse.rates.toList().toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = AdapterRateBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val rate = rates[position]
        holder.binding.currency.text = rate.first
        holder.binding.value.text = rate.second.toString()
    }

    override fun getItemCount(): Int {
        return rates.size
    }
}

class MainViewHolder(val binding: AdapterRateBinding) : RecyclerView.ViewHolder(binding.root) {
}