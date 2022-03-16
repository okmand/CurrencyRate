package and.okm.currency.rate.domain.repositories

import and.okm.currency.rate.domain.models.RatesResponse
import retrofit2.Response

interface RatesRepository {

    suspend fun getRates(): Response<RatesResponse>

    suspend fun getRatesForSpecificCurrencies(symbols: String): Response<RatesResponse>

}