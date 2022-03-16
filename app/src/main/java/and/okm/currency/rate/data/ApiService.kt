package and.okm.currency.rate.data

import and.okm.currency.rate.data.constants.HttpRequests.Companion.FIELD_ACCESS_KEY
import and.okm.currency.rate.data.constants.HttpRequests.Companion.FIELD_SYMBOLS
import and.okm.currency.rate.data.constants.Tokens.Companion.TOKEN
import and.okm.currency.rate.data.dto.RatesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("latest")
    suspend fun getRates(
        @Query(FIELD_ACCESS_KEY) accessKey: String = TOKEN,
    ): Response<RatesResponseDto>

    @GET("latest")
    suspend fun getRatesForSpecificCurrencies(
        @Query(FIELD_SYMBOLS) symbols: String,
        @Query(FIELD_ACCESS_KEY) accessKey: String = TOKEN,
    ): Response<RatesResponseDto>

}