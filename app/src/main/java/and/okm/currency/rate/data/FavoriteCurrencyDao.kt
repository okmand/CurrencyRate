package and.okm.currency.rate.data

import and.okm.currency.rate.data.dto.FavoriteCurrency
import androidx.room.*

@Dao
interface FavoriteCurrencyDao {

    @Query("SELECT * FROM favorite_currencies")
    suspend fun getAllFavoriteCurrencies(): List<FavoriteCurrency>

    @Query("SELECT * FROM favorite_currencies WHERE currency = :currency")
    suspend fun getByCurrency(currency: String): List<FavoriteCurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: FavoriteCurrency)

    @Delete
    suspend fun delete(currency: FavoriteCurrency)

}