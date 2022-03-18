package and.okm.currency.rate.domain.usecases

import and.okm.currency.rate.data.dto.Setting
import and.okm.currency.rate.domain.repositories.SettingsRepository
import javax.inject.Inject

class SettingUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {

    suspend fun getSettings(): Map<String, Boolean> {
        return repository.getAllSettings()
    }

    suspend fun getByParameter(parameter: String): List<Setting> {
        return repository.getByParameter(parameter)
    }

    suspend fun insert(setting: Setting) {
        repository.insert(setting)
    }

    suspend fun update(setting: Setting) {
        repository.update(setting)
    }

}