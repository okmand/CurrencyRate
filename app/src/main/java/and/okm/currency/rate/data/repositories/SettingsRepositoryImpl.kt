package and.okm.currency.rate.data.repositories

import and.okm.currency.rate.data.database.SettingsDao
import and.okm.currency.rate.data.dto.Setting
import and.okm.currency.rate.domain.mappers.SettingsMapper
import and.okm.currency.rate.domain.repositories.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dao: SettingsDao,
    private val mapper: SettingsMapper,
) : SettingsRepository {

    override suspend fun getAllSettings(): Map<String, Boolean> {
        return mapper.map(dao.getAllSettings())
    }

    override suspend fun getByParameter(parameter: String): List<Setting> {
        return dao.getByParameter(parameter)
    }

    override suspend fun insert(setting: Setting) {
        dao.insert(setting)
    }

    override suspend fun update(setting: Setting) {
        dao.update(setting)
    }

}