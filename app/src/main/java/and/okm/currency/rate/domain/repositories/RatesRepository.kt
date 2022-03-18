package and.okm.currency.rate.domain.repositories

import and.okm.currency.rate.domain.models.RatesResponse
import and.okm.currency.rate.domain.models.Result

interface RatesRepository {

    suspend fun getRates(): Result<RatesResponse>

    suspend fun getRatesForSpecificCurrencies(symbols: String): Result<RatesResponse>

}