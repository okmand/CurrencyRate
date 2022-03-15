package and.okm.currency.rate.domain.models

data class RatesResponse(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
) {
    companion object {

        val SUCCESSFUL = RatesResponse(
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