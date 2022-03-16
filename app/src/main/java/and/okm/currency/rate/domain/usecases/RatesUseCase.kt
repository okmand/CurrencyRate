package and.okm.currency.rate.domain.usecases

import and.okm.currency.rate.domain.models.RatesResponse
import and.okm.currency.rate.domain.repositories.RatesRepository
import retrofit2.Response
import javax.inject.Inject

class RatesUseCase @Inject constructor(
    private val repository: RatesRepository
) {

    suspend fun getRates(): Response<RatesResponse> {
        return repository.getRates()
    }

    suspend fun getRatesForSpecificCurrencies(specificCurrencies: List<String>): Response<RatesResponse> {
        val symbols = specificCurrencies.joinToString(separator = ",")
        return repository.getRatesForSpecificCurrencies(symbols)
    }

}