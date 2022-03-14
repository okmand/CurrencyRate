package and.okm.currency.rate.domain.repositories

import and.okm.currency.rate.data.RatesResponse
import retrofit2.Response

interface RatesRepository {

    suspend fun getRates(): Response<RatesResponse>

}