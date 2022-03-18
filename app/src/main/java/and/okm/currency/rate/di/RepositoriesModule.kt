package and.okm.currency.rate.di

import and.okm.currency.rate.data.repositories.FavoriteCurrenciesRepositoryImpl
import and.okm.currency.rate.data.repositories.RatesRepositoryImpl
import and.okm.currency.rate.data.repositories.SettingsRepositoryImpl
import and.okm.currency.rate.domain.repositories.FavoriteCurrenciesRepository
import and.okm.currency.rate.domain.repositories.RatesRepository
import and.okm.currency.rate.domain.repositories.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoriesModule {

    @Binds
    fun favoriteCurrenciesRepository(
        favoriteCurrenciesRepositoryImpl: FavoriteCurrenciesRepositoryImpl
    ): FavoriteCurrenciesRepository

    @Binds
    fun ratesRepository(ratesRepositoryImpl: RatesRepositoryImpl): RatesRepository

    @Binds
    fun settingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository

}