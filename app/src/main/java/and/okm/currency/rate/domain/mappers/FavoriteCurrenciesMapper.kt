package and.okm.currency.rate.domain.mappers

import and.okm.currency.rate.data.dto.FavoriteCurrency

class FavoriteCurrenciesMapper {

    fun map(favoriteCurrencies: List<FavoriteCurrency>): List<String> {
        return favoriteCurrencies.map {
            it.currency
        }
    }

}