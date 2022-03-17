package and.okm.currency.rate.data.repositories

import and.okm.currency.rate.data.database.FavoriteCurrenciesDao
import and.okm.currency.rate.data.dto.FavoriteCurrency
import and.okm.currency.rate.domain.mappers.FavoriteCurrenciesMapper
import javax.inject.Inject

class FavoriteCurrenciesRepository @Inject constructor(
    private val dao: FavoriteCurrenciesDao,
    private val mapper: FavoriteCurrenciesMapper,
) {

    suspend fun getAllFavoriteCurrencies(): List<String> {
        return mapper.map(dao.getAllFavoriteCurrencies())
    }

    suspend fun getByCurrency(currency: String): List<FavoriteCurrency> {
        return dao.getByCurrency(currency)
    }

    suspend fun getCountFavoriteCurrencies(): Int {
        return dao.getCountFavoriteCurrencies()
    }

    suspend fun insert(currency: FavoriteCurrency) {
        dao.insert(currency)
    }

    suspend fun delete(currency: FavoriteCurrency) {
        dao.delete(currency)
    }

}