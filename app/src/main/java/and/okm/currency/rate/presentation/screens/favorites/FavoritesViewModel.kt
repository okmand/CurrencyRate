package and.okm.currency.rate.presentation.screens.favorites

import and.okm.currency.rate.domain.models.RatesResponse
import and.okm.currency.rate.domain.models.Result
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
class FavoritesViewModel @Inject constructor(
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

    val contentIsEmpty: StateFlow<Boolean>
        get() = contentIsEmptyStatusOrderStateFlow
    private val contentIsEmptyStatusOrderStateFlow = MutableStateFlow(true)

    val error: SharedFlow<String>
        get() = errorSharedFlow.asSharedFlow()
    private val errorSharedFlow = MutableSharedFlow<String>()

    fun getAllFavoriteCurrenciesRates() {
        CoroutineScope(Dispatchers.IO).launch {
            refreshStatusOrderStateFlow.emit(true)
            contentIsEmptyStatusOrderStateFlow.emit(true)
            val allFavoriteCurrencies = favoriteCurrencyUseCase.getAllFavoriteCurrencies()
            if (allFavoriteCurrencies.isNotEmpty()) {
                val ratesResponse = ratesUseCase.getRatesForSpecificCurrencies(
                    specificCurrencies = allFavoriteCurrencies
                )
                when (ratesResponse) {
                    is Result.Success -> handleSuccess(ratesResponse.value)
                    is Result.Failure -> handleError(ratesResponse.cause)
                }
            } else {
                ratesOrderStateFlow.emit(RatesVo())
                contentIsEmptyStatusOrderStateFlow.emit(false)
                refreshStatusOrderStateFlow.emit(false)
            }
        }
    }

    private suspend fun handleSuccess(ratesResponse: RatesResponse) {
        val settings = settingsUseCase.getSettings()
        val isAlphabetSetting = getSetting(Settings.ALPHABET_SORTING_VALUE, settings)
        val isAscendingOrder = getSetting(Settings.ASCENDING_ORDER_VALUE, settings)
        val ratesVo = formatter.formatFavorites(
            ratesResponse = ratesResponse,
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
            }
            val countFavoriteCurrencies = favoriteCurrencyUseCase.getCountFavoriteCurrencies()
            contentIsEmptyStatusOrderStateFlow.emit(countFavoriteCurrencies != 0)
            getAllFavoriteCurrenciesRates()
        }
    }

    fun deleteAllFavoriteCurrencies() {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteCurrencyUseCase.deleteAllFavoriteCurrencies()
            getAllFavoriteCurrenciesRates()
        }
    }

}