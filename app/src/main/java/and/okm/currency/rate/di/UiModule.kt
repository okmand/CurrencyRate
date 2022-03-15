package and.okm.currency.rate.di

import and.okm.currency.rate.ui.formatters.RatesFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {

    @Provides
    fun provideFormatter(): RatesFormatter {
        return RatesFormatter()
    }

}