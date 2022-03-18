package and.okm.currency.rate.presentation.screens.home

import and.okm.currency.rate.data.dto.FavoriteCurrency
import and.okm.currency.rate.domain.models.RatesResponse
import and.okm.currency.rate.domain.models.Result.Failure
import and.okm.currency.rate.domain.models.Result.Success
import and.okm.currency.rate.domain.usecases.FavoriteCurrencyUseCase
import and.okm.currency.rate.domain.usecases.RatesUseCase
import and.okm.currency.rate.domain.usecases.SettingUseCase
import and.okm.currency.rate.presentation.constants.Settings
import and.okm.currency.rate.presentation.formatters.RatesFormatter
import and.okm.currency.rate.presentation.viewobjects.RatesVo
import and.okm.currency.rate.utils.ErrorHandler
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val ratesUseCase: RatesUseCase,
    private val favoriteCurrencyUseCase: FavoriteCurrencyUseCase,
    private val settingsUseCase: SettingUseCase,
    private val formatter: RatesFormatter,
) : ViewModel() {

    val rates: StateFlow<RatesVo>
        get() = ratesOrderStateFlow
    private val ratesOrderStateFlow = MutableStateFlow(RatesVo())

    val refreshStatus: StateFlow<Boolean>
        get() = refreshStatusOrderStateFlow
    private val refreshStatusOrderStateFlow = MutableStateFlow(false)

    val error: SharedFlow<String>
        get() = errorSharedFlow.asSharedFlow()
    private val errorSharedFlow = MutableSharedFlow<String>()

    fun getAllRates() {
        CoroutineScope(Dispatchers.IO).launch {
            refreshStatusOrderStateFlow.emit(true)
            when (val ratesResponse = ratesUseCase.getRates()) {
                is Success -> handleSuccess(ratesResponse.value)
                is Failure -> handleError(ratesResponse.cause)
            }
        }
    }

    private suspend fun handleSuccess(ratesResponse: RatesResponse) {
        val allFavoriteCurrencies = favoriteCurrencyUseCase.getAllFavoriteCurrencies()
        val settings = settingsUseCase.getSettings()
        val isAlphabetSetting = getSetting(Settings.ALPHABET_SORTING_VALUE, settings)
        val isAscendingOrder = getSetting(Settings.ASCENDING_ORDER_VALUE, settings)
        val ratesVo = formatter.format(
            ratesResponse = ratesResponse,
            favoriteCurrencies = allFavoriteCurrencies,
            isAlphabetSorting = isAlphabetSetting,
            isAscendingOrder = isAscendingOrder,
        )
        ratesOrderStateFlow.emit(ratesVo)
        refreshStatusOrderStateFlow.emit(false)
    }

    private suspend fun handleError(cause: Throwable) {
        val message = ErrorHandler.getMessageByThrowable(cause)
        errorSharedFlow.emit(message)
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
            getAllRates()
        }

    }
}