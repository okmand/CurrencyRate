package and.okm.currency.rate.di

import and.okm.currency.rate.data.database.FavoriteCurrenciesDao
import and.okm.currency.rate.data.database.FavoriteCurrenciesDatabase
import and.okm.currency.rate.data.database.SettingsDao
import and.okm.currency.rate.data.database.SettingsDatabase
import and.okm.currency.rate.domain.mappers.FavoriteCurrenciesMapper
import and.okm.currency.rate.domain.mappers.RatesMapper
import and.okm.currency.rate.domain.mappers.SettingsMapper
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

    @Provides
    fun provideSettingsMapper() = SettingsMapper()

    @Singleton
    @Provides
    fun provideFavoriteCurrenciesDatabase(
        @ApplicationContext appContext: Context
    ): FavoriteCurrenciesDatabase {
        return FavoriteCurrenciesDatabase.getDatabase(appContext)
    }

    @Singleton
    @Provides
    fun provideFavoriteCurrenciesDao(db: FavoriteCurrenciesDatabase): FavoriteCurrenciesDao {
        return db.favoriteCurrenciesDao()
    }

    @Singleton
    @Provides
    fun provideSettingsDatabase(
        @ApplicationContext appContext: Context
    ): SettingsDatabase {
        return SettingsDatabase.getDatabase(appContext)
    }

    @Singleton
    @Provides
    fun provideSettingsDao(db: SettingsDatabase): SettingsDao {
        return db.settingsDao()
    }

}