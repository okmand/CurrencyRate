package and.okm.currency.rate.presentation

import and.okm.currency.rate.data.dto.FavoriteCurrency
import and.okm.currency.rate.domain.models.RatesResponse.Companion.UNSUCCESSFUL
import and.okm.currency.rate.domain.usecases.FavoriteCurrencyUseCase
import and.okm.currency.rate.domain.usecases.RatesUseCase
import and.okm.currency.rate.presentation.formatters.RatesFormatter
import and.okm.currency.rate.presentation.viewobjects.RatesVo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val ratesUseCase: RatesUseCase,
    private val favoriteCurrencyUseCase: FavoriteCurrencyUseCase,
    private val formatter: RatesFormatter,
) : ViewModel() {

    val rates = MutableLiveData<RatesVo>()
    val progressBarStatus = MutableLiveData<Boolean>()

    fun getAllRates() {
        CoroutineScope(Dispatchers.IO).launch {
            progressBarStatus.postValue(true)
            val response = ratesUseCase.execute()
            val allFavoriteCurrencies = favoriteCurrencyUseCase.getAllFavoriteCurrencies()
            if (response.isSuccessful) {
                val ratesVo = formatter.format(
                    ratesResponse = response.body() ?: UNSUCCESSFUL,
                    favoriteCurrencies = allFavoriteCurrencies
                )
                rates.postValue(ratesVo)
                progressBarStatus.postValue(false)
            }
        }
    }

    fun changeFavoriteCurrencies(currency: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentFavoriteCurrencies = favoriteCurrencyUseCase.getByCurrency(currency)
            if (currentFavoriteCurrencies.isNotEmpty()) {
                currentFavoriteCurrencies.forEach {
                    favoriteCurrencyUseCase.delete(it)
                }
            } else {
                val favoriteCurrency = FavoriteCurrency(currency = currency)
                favoriteCurrencyUseCase.insert(favoriteCurrency)
            }
        }

    }
}