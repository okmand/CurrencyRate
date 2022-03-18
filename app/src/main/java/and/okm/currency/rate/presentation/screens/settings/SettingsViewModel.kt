package and.okm.currency.rate.presentation.screens.settings

import and.okm.currency.rate.data.dto.Setting
import and.okm.currency.rate.domain.usecases.SettingUseCase
import and.okm.currency.rate.presentation.constants.Settings
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCase: SettingUseCase,
) : ViewModel() {

    val alphabeticalSorting: StateFlow<Boolean>
        get() = alphabeticalSortingStateFlow
    private val alphabeticalSortingStateFlow = MutableStateFlow(true)

    val ascendingOrder: StateFlow<Boolean>
        get() = ascendingOrderStateFlow
    private val ascendingOrderStateFlow = MutableStateFlow(true)

    fun getSortingSettings() {
        CoroutineScope(Dispatchers.IO).launch {
            val settings = settingsUseCase.getSettings()
            fetchSetting(settings[Settings.ALPHABET_SORTING_VALUE], alphabeticalSortingStateFlow)
            fetchSetting(settings[Settings.ASCENDING_ORDER_VALUE], ascendingOrderStateFlow)
        }
    }

    private suspend fun fetchSetting(
        value: Boolean?,
        stateFlow: MutableStateFlow<Boolean>
    ) {
        stateFlow.emit(value == null || value)
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