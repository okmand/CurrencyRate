package and.okm.currency.rate.ui.formatters

import and.okm.currency.rate.domain.models.RatesResponse
import and.okm.currency.rate.ui.viewobjects.RateVo
import and.okm.currency.rate.ui.viewobjects.RatesVo

class RatesFormatter {

    fun format(ratesResponse: RatesResponse): RatesVo {
        val listRates = ratesResponse.rates
            .toList()
            .map { currencyWithValue ->
                RateVo(
                    currency = currencyWithValue.first,
                    value = currencyWithValue.second.toString()
                )
            }

        return RatesVo(
            rates = listRates
        )
    }

}