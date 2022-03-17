package and.okm.currency.rate.data.database

import and.okm.currency.rate.data.dto.FavoriteCurrency
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCurrency::class], version = 1, exportSchema = false)
abstract class FavoriteCurrenciesDatabase : RoomDatabase() {

    abstract fun favoriteCurrenciesDao(): FavoriteCurrenciesDao

    companion object {

        @Volatile
        private var instance: FavoriteCurrenciesDatabase? = null

        fun getDatabase(context: Context): FavoriteCurrenciesDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(
                appContext,
                FavoriteCurrenciesDatabase::class.java,
                "favorite_currencies"
            )
                .fallbackToDestructiveMigration()
                .build()

    }

}