package and.okm.currency.rate.presentation.screens.settings

import and.okm.currency.rate.data.dto.Setting
import and.okm.currency.rate.domain.usecases.SettingUseCase
import and.okm.currency.rate.presentation.constants.Settings
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCase: SettingUseCase,
) : ViewModel() {

    val alphabeticalSorting = MutableLiveData<Boolean>()
    val ascendingOrder = MutableLiveData<Boolean>()

    fun getSortingSettings() {
        CoroutineScope(Dispatchers.IO).launch {
            val settings = settingsUseCase.getSettings()
            fetchSetting(settings[Settings.ALPHABET_SORTING_VALUE], alphabeticalSorting)
            fetchSetting(settings[Settings.ASCENDING_ORDER_VALUE], ascendingOrder)
        }
    }

    private fun fetchSetting(
        value: Boolean?,
        liveData: MutableLiveData<Boolean>
    ) {
        if (value == null || value) {
            liveData.postValue(true)
        } else {
            liveData.postValue(false)
        }
    }

    fun setAlphabeticalSortingSetting(newSetting: Boolean) {
        setSetting(Settings.ALPHABET_SORTING_VALUE, newSetting)
    }

    fun setAscendingOrderSetting(newSetting: Boolean) {
        setSetting(Settings.ASCENDING_ORDER_VALUE, newSetting)
    }

    private fun setSetting(parameter: String, newSetting: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentSetting = settingsUseCase.getByParameter(parameter).getOrNull(0)
            if (currentSetting != null) {
                val setting = currentSetting.copy(
                    value = newSetting
                )
                settingsUseCase.update(setting)
            } else {
                val setting = Setting(
                    parameter = parameter,
                    value = newSetting
                )
                settingsUseCase.insert(setting)
            }
        }
    }

}