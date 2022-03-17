package and.okm.currency.rate.presentation.screens.favorites

import and.okm.currency.rate.databinding.AdapterFavoriteCurrienceBinding
import and.okm.currency.rate.presentation.viewobjects.RateVo
import and.okm.currency.rate.presentation.viewobjects.RatesVo
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FavoritesCurrenciesAdapter(
    private val onStarClicked: (currency: String) -> Unit
) : RecyclerView.Adapter<FavoritesCurrenciesViewHolder>() {

    private lateinit var binding: AdapterFavoriteCurrienceBinding

    var rates = mutableListOf<RateVo>()

    @SuppressLint("NotifyDataSetChanged")
    fun setRates(ratesResponse: RatesVo) {
        this.rates = ratesResponse.rates.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesCurrenciesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = AdapterFavoriteCurrienceBinding.inflate(inflater, parent, false)
        val viewHolder = FavoritesCurrenciesViewHolder(binding)
        viewHolder.delete.setOnClickListener {
            if (viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                onStarClicked.invoke(rates[viewHolder.adapterPosition].currency)
                rates.removeAt(viewHolder.adapterPosition)
                notifyItemRemoved(viewHolder.adapterPosition)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: FavoritesCurrenciesViewHolder, position: Int) {
        val rate = rates[position]
        holder.bind(rate)
    }

    override fun getItemCount(): Int {
        return rates.size
    }
}

class FavoritesCurrenciesViewHolder(
    binding: AdapterFavoriteCurrienceBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val currency = binding.currency
    private val value = binding.value
    val delete = binding.delete

    fun bind(rate: RateVo) {
        currency.text = rate.currency
        value.text = rate.value.toString()
    }

}