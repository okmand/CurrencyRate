package and.okm.currency.rate.presentation.formatters

import and.okm.currency.rate.domain.models.RatesResponse
import and.okm.currency.rate.presentation.viewobjects.RateVo
import and.okm.currency.rate.presentation.viewobjects.RatesVo

class RatesFormatter {

    fun format(ratesResponse: RatesResponse, favoriteCurrencies: List<String>): RatesVo {
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

    fun formatFavorites(ratesResponse: RatesResponse): RatesVo {
        val listRates = ratesResponse.rates
            .toList()
            .map { currencyWithValue ->
                RateVo(
                    currency = currencyWithValue.first,
                    value = currencyWithValue.second.toString(),
                    favorite = true,
                )
            }

        return RatesVo(
            rates = listRates
        )
    }

}