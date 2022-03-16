package and.okm.currency.rate.domain.mappers

import and.okm.currency.rate.data.dto.FavoriteCurrency

class FavoriteCurrenciesMapper {

    fun map(favoriteCurrencies: List<FavoriteCurrency>): Set<String> {
        return favoriteCurrencies.map {
            it.currency
        }.toSet()
    }

}