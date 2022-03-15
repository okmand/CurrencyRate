package and.okm.currency.rate.di

import and.okm.currency.rate.domain.mappers.RatesMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideMapper(): RatesMapper {
        return RatesMapper()
    }

}