package and.okm.currency.rate.presentation.screens.home

import and.okm.currency.rate.R
import and.okm.currency.rate.databinding.AdapterRateBinding
import and.okm.currency.rate.presentation.viewobjects.RateVo
import and.okm.currency.rate.presentation.viewobjects.RatesVo
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RatesAdapter(
    private val onStarClicked: (currency: String) -> Unit
) : RecyclerView.Adapter<RateViewHolder>() {

    private lateinit var binding: AdapterRateBinding

    var rates = listOf<RateVo>()

    @SuppressLint("NotifyDataSetChanged")
    fun setRates(ratesResponse: RatesVo) {
        this.rates = ratesResponse.rates
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = AdapterRateBinding.inflate(inflater, parent, false)
        val viewHolder = RateViewHolder(binding)
        viewHolder.favorite.setOnClickListener {
            if (viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                onStarClicked.invoke(rates[viewHolder.adapterPosition].currency)
                viewHolder.changeStar()
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val rate = rates[position]
        holder.bind(rate)
    }

    override fun getItemCount(): Int {
        return rates.size
    }
}

class RateViewHolder(
    binding: AdapterRateBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val currency = binding.currency
    private val value = binding.value
    val favorite = binding.favorite

    private var isFavorite = false

    fun bind(rate: RateVo) {
        currency.text = rate.currency
        value.text = rate.value.toString()
        isFavorite = !rate.favorite
        changeStar()
    }

    fun changeStar() {
        isFavorite = isFavorite.not()
        val imageDrawable = if (isFavorite) {
            R.drawable.ic_star_filled
        } else {
            R.drawable.ic_star_empty
        }
        favorite.setImageResource(imageDrawable)
    }

}