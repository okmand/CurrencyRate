package and.okm.currency.rate.data.database

import and.okm.currency.rate.data.dto.Setting
import androidx.room.*

@Dao
interface SettingsDao {

    @Query("SELECT * FROM settings")
    suspend fun getAllSettings(): List<Setting>

    @Query("SELECT * FROM settings WHERE parameter = :parameter")
    suspend fun getByParameter(parameter: String): List<Setting>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(setting: Setting)

    @Update
    suspend fun update(setting: Setting)

}