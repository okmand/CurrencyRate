package and.okm.currency.rate.domain.usecases

import and.okm.currency.rate.data.dto.FavoriteCurrency
import and.okm.currency.rate.data.repositories.FavoriteCurrenciesRepository
import javax.inject.Inject

class FavoriteCurrencyUseCase @Inject constructor(
    private val repository: FavoriteCurrenciesRepository,
) {

    suspend fun getAllFavoriteCurrencies(): List<String> {
        return repository.getAllFavoriteCurrencies()
    }

    suspend fun getByCurrency(currency: String): List<FavoriteCurrency> {
        return repository.getByCurrency(currency)
    }

    suspend fun getCountFavoriteCurrencies(): Int {
        return repository.getCountFavoriteCurrencies()
    }

    suspend fun insert(currency: FavoriteCurrency) {
        repository.insert(currency)
    }

    suspend fun delete(currency: FavoriteCurrency) {
        repository.delete(currency)
    }


}