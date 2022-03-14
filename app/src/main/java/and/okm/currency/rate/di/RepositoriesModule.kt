package and.okm.currency.rate.di

import and.okm.currency.rate.domain.repositories.RatesRepository
import and.okm.currency.rate.data.repositories.RatesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoriesModule {

    @Binds
    fun ratesRepository(mainRepositoryImpl: RatesRepositoryImpl): RatesRepository

}