package and.okm.currency.rate.presentation.formatters

import and.okm.currency.rate.domain.models.RatesResponse
import and.okm.currency.rate.presentation.viewobjects.RateVo
import and.okm.currency.rate.presentation.viewobjects.RatesVo

class RatesFormatter {

    fun format(ratesResponse: RatesResponse, favoriteCurrencies: Set<String>): RatesVo {
        val listRates = ratesResponse.rates
            .toList()
            .map { currencyWithValue ->
                val currency = currencyWithValue.first
                val value = currencyWithValue.second.toString()
                val isFavorite = favoriteCurrencies.contains(currency)
                RateVo(
                    currency = currency,
                    value = value,
                    favorite = isFavorite,
                )
            }

        return RatesVo(
            rates = listRates
        )
    }

}