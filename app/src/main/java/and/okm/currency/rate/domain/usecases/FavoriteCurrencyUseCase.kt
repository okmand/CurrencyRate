package and.okm.currency.rate.domain.usecases

import and.okm.currency.rate.data.dto.FavoriteCurrency
import and.okm.currency.rate.data.repositories.FavoriteCurrencyRepository
import javax.inject.Inject

class FavoriteCurrencyUseCase @Inject constructor(
    private val favoriteCurrencyRepository: FavoriteCurrencyRepository,
) {

    suspend fun getAllFavoriteCurrencies(): List<String> {
        return favoriteCurrencyRepository.getAllFavoriteCurrencies()
    }

    suspend fun getByCurrency(currency: String): List<FavoriteCurrency> {
        return favoriteCurrencyRepository.getByCurrency(currency)
    }

    suspend fun getCountFavoriteCurrencies(): Int {
        return favoriteCurrencyRepository.getCountFavoriteCurrencies()
    }

    suspend fun insert(currency: FavoriteCurrency) {
        favoriteCurrencyRepository.insert(currency)
    }

    suspend fun delete(currency: FavoriteCurrency) {
        favoriteCurrencyRepository.delete(currency)
    }


}