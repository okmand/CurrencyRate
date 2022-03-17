package and.okm.currency.rate.data.repositories

import and.okm.currency.rate.data.database.SettingsDao
import and.okm.currency.rate.data.dto.Setting
import and.okm.currency.rate.domain.mappers.SettingsMapper
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val dao: SettingsDao,
    private val mapper: SettingsMapper,
) {

    suspend fun getAllSettings(): Map<String, Boolean> {
        return mapper.map(dao.getAllSettings())
    }

    suspend fun getByParameter(parameter: String): List<Setting> {
        return dao.getByParameter(parameter)
    }

    suspend fun insert(setting: Setting) {
        dao.insert(setting)
    }

    suspend fun update(setting: Setting) {
        dao.update(setting)
    }

}