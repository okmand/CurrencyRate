package and.okm.currency.rate.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RatesResponseDto(
    @SerializedName("success")
    val success: Boolean?,

    @SerializedName("timestamp")
    val timestamp: Long?,

    @SerializedName("base")
    val base: String?,

    @SerializedName("date")
    val date: String?,

    @SerializedName("rates")
    val rates: Map<String, Double>?,
) {

    companion object {

        val SUCCESSFUL = RatesResponseDto(
            success = true,
            timestamp = 1600000000L,
            base = "EUR",
            date = "2020-09-13",
            hashMapOf()
        )

        val UNSUCCESSFUL = SUCCESSFUL.copy(
            success = false,
        )

    }

}