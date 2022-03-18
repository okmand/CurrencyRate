package and.okm.currency.rate.data.database

import and.okm.currency.rate.data.dto.FavoriteCurrency
import androidx.room.*

@Dao
interface FavoriteCurrenciesDao {

    @Query("SELECT * FROM favorite_currencies")
    suspend fun getAllFavoriteCurrencies(): List<FavoriteCurrency>

    @Query("SELECT * FROM favorite_currencies WHERE currency = :currency")
    suspend fun getByCurrency(currency: String): List<FavoriteCurrency>

    @Query("SELECT COUNT(*) FROM favorite_currencies")
    suspend fun getCountFavoriteCurrencies(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: FavoriteCurrency)

    @Delete
    suspend fun delete(currency: FavoriteCurrency)

    @Query("DELETE FROM favorite_currencies")
    suspend fun deleteAllFavoriteCurrencies()

}