package and.okm.currency.rate.ui

import and.okm.currency.rate.domain.models.RatesResponse.Companion.UNSUCCESSFUL
import and.okm.currency.rate.domain.usecases.RatesUseCase
import and.okm.currency.rate.ui.formatters.RatesFormatter
import and.okm.currency.rate.ui.viewobjects.RatesVo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val useCase: RatesUseCase,
    private val formatter: RatesFormatter,
) : ViewModel() {

    val rates = MutableLiveData<RatesVo>()
    val progressBarStatus = MutableLiveData<Boolean>()

    fun getAllRates() {
        CoroutineScope(Dispatchers.IO).launch {
            progressBarStatus.postValue(true)
            val response = useCase.execute()
            if (response.isSuccessful) {
                val ratesVo = formatter.format(response.body() ?: UNSUCCESSFUL)
                rates.postValue(ratesVo)
                progressBarStatus.postValue(false)
            }
        }
    }
}