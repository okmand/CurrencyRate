package and.okm.currency.rate.data

import and.okm.currency.rate.constants.HttpRequests.Companion.FIELD_ACCESS_KEY
import and.okm.currency.rate.constants.Tokens.Companion.TOKEN
import and.okm.currency.rate.data.dto.RatesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // You need to get your token: https://github.com/exchangeratesapi/exchangeratesapi
    @GET("latest")
    suspend fun getRates(
        @Query(FIELD_ACCESS_KEY) accessKey: String = TOKEN,
    ): Response<RatesResponseDto>

}