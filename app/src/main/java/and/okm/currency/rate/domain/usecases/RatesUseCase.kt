package and.okm.currency.rate.domain.usecases

import and.okm.currency.rate.domain.models.RatesResponse
import and.okm.currency.rate.domain.models.Result
import and.okm.currency.rate.domain.repositories.RatesRepository
import javax.inject.Inject

class RatesUseCase @Inject constructor(
    private val repository: RatesRepository
) {

    suspend fun getRates(): Result<RatesResponse> {
        return repository.getRates()
    }

    suspend fun getRatesForSpecificCurrencies(specificCurrencies: List<String>): Result<RatesResponse> {
        val symbols = specificCurrencies.joinToString(separator = ",")
        return repository.getRatesForSpecificCurrencies(symbols)
    }

}