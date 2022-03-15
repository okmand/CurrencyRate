package and.okm.currency.rate.domain.usecases

import and.okm.currency.rate.domain.models.RatesResponse
import and.okm.currency.rate.domain.repositories.RatesRepository
import retrofit2.Response
import javax.inject.Inject

class RatesUseCase @Inject constructor(
    private val repository: RatesRepository
) {

    suspend fun execute(): Response<RatesResponse> {
        return repository.getRates()
    }

}