package and.okm.currency.rate.data.repositories

import and.okm.currency.rate.data.FavoriteCurrencyDao
import and.okm.currency.rate.data.dto.FavoriteCurrency
import and.okm.currency.rate.domain.mappers.FavoriteCurrenciesMapper
import javax.inject.Inject

class FavoriteCurrencyRepository @Inject constructor(
    private val favoriteCurrencyDao: FavoriteCurrencyDao,
    private val mapper: FavoriteCurrenciesMapper,
) {

    suspend fun getAllFavoriteCurrencies(): Set<String> {
        return mapper.map(favoriteCurrencyDao.getAllFavoriteCurrencies())
    }

    suspend fun getByCurrency(currency: String): List<FavoriteCurrency> {
        return favoriteCurrencyDao.getByCurrency(currency)
    }

    suspend fun insert(currency: FavoriteCurrency) {
        favoriteCurrencyDao.insert(currency)
    }

    suspend fun delete(currency: FavoriteCurrency) {
        favoriteCurrencyDao.delete(currency)
    }

}