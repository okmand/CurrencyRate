package and.okm.currency.rate.domain.mappers

import and.okm.currency.rate.data.dto.RatesResponseDto
import and.okm.currency.rate.data.dto.RatesResponseDto.Companion.UNSUCCESSFUL
import and.okm.currency.rate.domain.models.RatesResponse
import retrofit2.Response

class RatesMapper {

    fun map(dto: Response<RatesResponseDto>): Response<RatesResponse> {
        val code = dto.code()

        return if (dto.isSuccessful) {
            val body = dto.body() ?: UNSUCCESSFUL

            val success = body.success ?: false
            val timestamp = body.timestamp ?: 0L
            val base = body.base ?: ""
            val date = body.date ?: ""
            val rates = body.rates ?: hashMapOf()

            val ratesResponse = RatesResponse(
                success = success,
                timestamp = timestamp,
                base = base,
                date = date,
                rates = rates
            )
            Response.success(code, ratesResponse)
        } else {
            Response.error(code, dto.errorBody())
        }
    }

}