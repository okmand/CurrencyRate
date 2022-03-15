package and.okm.currency.rate.ui

import and.okm.currency.rate.databinding.AdapterRateBinding
import and.okm.currency.rate.ui.viewobjects.RateVo
import and.okm.currency.rate.ui.viewobjects.RatesVo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class RatesAdapter @Inject constructor() : RecyclerView.Adapter<MainViewHolder>() {

    private lateinit var binding: AdapterRateBinding

    var rates = mutableListOf<RateVo>()

    fun setRates(ratesResponse: RatesVo) {
        this.rates = ratesResponse.rates.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = AdapterRateBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val rate = rates[position]
        holder.bind(rate)
    }

    override fun getItemCount(): Int {
        return rates.size
    }
}

class MainViewHolder(val binding: AdapterRateBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(rate: RateVo) {
        binding.currency.text = rate.currency
        binding.value.text = rate.value
    }

}