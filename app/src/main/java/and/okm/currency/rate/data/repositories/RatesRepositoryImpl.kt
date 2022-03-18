package and.okm.currency.rate.data.repositories

import and.okm.currency.rate.data.ApiService
import and.okm.currency.rate.domain.mappers.RatesMapper
import and.okm.currency.rate.domain.models.RatesResponse
import and.okm.currency.rate.domain.models.Result
import and.okm.currency.rate.domain.repositories.RatesRepository
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: RatesMapper,
) : RatesRepository {

    override suspend fun getRates(): Result<RatesResponse> {
        return Result {
            mapper.map(apiService.getRates())
        }
    }

    override suspend fun getRatesForSpecificCurrencies(symbols: String): Result<RatesResponse> {
        return Result {
            mapper.map(
                apiService.getRatesForSpecificCurrencies(symbols = symbols)
            )
        }
    }

}