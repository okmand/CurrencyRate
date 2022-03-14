package and.okm.currency.rate.ui

import and.okm.currency.rate.data.RatesResponse
import and.okm.currency.rate.domain.repositories.RatesRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val repository: RatesRepository
) : ViewModel() {

    val rates = MutableLiveData<RatesResponse>()

    fun getAllRates() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getRates()
            if (response.isSuccessful) {
                rates.postValue(response.body())
            }
        }
    }
}