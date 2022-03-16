package and.okm.currency.rate.presentation.screens.favorites

import and.okm.currency.rate.domain.models.RatesResponse
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
class FavoritesViewModel @Inject constructor(
    private val ratesUseCase: RatesUseCase,
    private val favoriteCurrencyUseCase: FavoriteCurrencyUseCase,
    private val formatter: RatesFormatter,
) : ViewModel() {

    val rates = MutableLiveData<RatesVo>()
    val refreshStatus = MutableLiveData<Boolean>()
    val textHint = MutableLiveData<Boolean>()

    fun getAllFavoriteCurrenciesRates() {
        CoroutineScope(Dispatchers.IO).launch {
            refreshStatus.postValue(true)
            textHint.postValue(false)
            val allFavoriteCurrencies = favoriteCurrencyUseCase.getAllFavoriteCurrencies()
            var ratesVo = RatesVo()
            if (allFavoriteCurrencies.isNotEmpty()) {
                val responseRates = ratesUseCase.getRatesForSpecificCurrencies(
                    specificCurrencies = allFavoriteCurrencies
                )
                if (responseRates.isSuccessful) {
                    ratesVo = formatter.formatFavorites(
                        ratesResponse = responseRates.body() ?: RatesResponse.UNSUCCESSFUL
                    )
                }
            } else {
                textHint.postValue(true)
            }
            rates.postValue(ratesVo)
            refreshStatus.postValue(false)
        }
    }

    fun changeFavoriteCurrencies(currency: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentFavoriteCurrencies = favoriteCurrencyUseCase.getByCurrency(currency)
            if (currentFavoriteCurrencies.isNotEmpty()) {
                currentFavoriteCurrencies.forEach {
                    favoriteCurrencyUseCase.delete(it)
                }
            }
            val countFavoriteCurrencies = favoriteCurrencyUseCase.getCountFavoriteCurrencies()
            textHint.postValue(countFavoriteCurrencies == 0)
        }
    }

}