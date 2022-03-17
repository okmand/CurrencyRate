package and.okm.currency.rate.presentation.screens.home

import and.okm.currency.rate.data.dto.FavoriteCurrency
import and.okm.currency.rate.domain.models.RatesResponse.Companion.UNSUCCESSFUL
import and.okm.currency.rate.domain.usecases.FavoriteCurrencyUseCase
import and.okm.currency.rate.domain.usecases.RatesUseCase
import and.okm.currency.rate.domain.usecases.SettingUseCase
import and.okm.currency.rate.presentation.constants.Settings
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
    private val settingsUseCase: SettingUseCase,
    private val formatter: RatesFormatter,
) : ViewModel() {

    val rates = MutableLiveData<RatesVo>()
    val refreshStatus = MutableLiveData<Boolean>()

    fun getAllRates() {
        CoroutineScope(Dispatchers.IO).launch {
            refreshStatus.postValue(true)
            val ratesResponse = ratesUseCase.getRates()
            val settings = settingsUseCase.getSettings()
            val isAlphabetSetting = getSetting(Settings.ALPHABET_SORTING_VALUE, settings)
            val isAscendingOrder = getSetting(Settings.ASCENDING_ORDER_VALUE, settings)
            if (ratesResponse.isSuccessful) {
                val allFavoriteCurrencies = favoriteCurrencyUseCase.getAllFavoriteCurrencies()
                val ratesVo = formatter.format(
                    ratesResponse = ratesResponse.body() ?: UNSUCCESSFUL,
                    favoriteCurrencies = allFavoriteCurrencies,
                    isAlphabetSorting = isAlphabetSetting,
                    isAscendingOrder = isAscendingOrder,
                )
                rates.postValue(ratesVo)
                refreshStatus.postValue(false)
            }
        }
    }

    private fun getSetting(parameter: String, settings: Map<String, Boolean>): Boolean {
        val value = settings[parameter]
        return value == null || value
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