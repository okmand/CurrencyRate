package and.okm.currency.rate.di

import and.okm.currency.rate.data.AppDatabase
import and.okm.currency.rate.data.FavoriteCurrencyDao
import and.okm.currency.rate.data.repositories.FavoriteCurrencyRepository
import and.okm.currency.rate.domain.mappers.FavoriteCurrenciesMapper
import and.okm.currency.rate.domain.mappers.RatesMapper
import and.okm.currency.rate.presentation.formatters.RatesFormatter
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideFormatter() = RatesFormatter()

    @Provides
    fun provideRatesMapper() = RatesMapper()

    @Provides
    fun provideFavoriteCurrenciesMapper() = FavoriteCurrenciesMapper()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase): FavoriteCurrencyDao {
        return db.favoriteCurrencyDao()
    }

    @Singleton
    @Provides
    fun favoriteCurrencyRepository(
        favoriteCurrencyDao: FavoriteCurrencyDao,
        favoriteCurrenciesMapper: FavoriteCurrenciesMapper,
    ): FavoriteCurrencyRepository {
        return FavoriteCurrencyRepository(favoriteCurrencyDao, favoriteCurrenciesMapper)
    }

}