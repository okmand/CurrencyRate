package and.okm.currency.rate.data.repositories

import and.okm.currency.rate.data.ApiService
import and.okm.currency.rate.domain.mappers.RatesMapper
import and.okm.currency.rate.domain.models.RatesResponse
import and.okm.currency.rate.domain.repositories.RatesRepository
import retrofit2.Response
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: RatesMapper,
) : RatesRepository {

    override suspend fun getRates(): Response<RatesResponse> {
        return mapper.map(apiService.getRates())
    }

    override suspend fun getRatesForSpecificCurrencies(symbols: String): Response<RatesResponse> {
        return mapper.map(
            apiService.getRatesForSpecificCurrencies(symbols = symbols)
        )
    }

}