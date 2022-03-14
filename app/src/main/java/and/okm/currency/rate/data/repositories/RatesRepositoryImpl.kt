package and.okm.currency.rate.data.repositories

import and.okm.currency.rate.domain.repositories.RatesRepository
import and.okm.currency.rate.data.ApiService
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RatesRepository {

    override suspend fun getRates() = apiService.getRates()

}