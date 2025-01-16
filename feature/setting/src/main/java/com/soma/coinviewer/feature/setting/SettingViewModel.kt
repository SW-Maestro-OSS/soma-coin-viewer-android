package com.soma.coinviewer.feature.setting

import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import com.soma.coinviewer.domain.preferences.Language
import com.soma.coinviewer.domain.preferences.PriceCurrencyUnit
import com.soma.coinviewer.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
) : BaseViewModel() {
    private val _isPriceCurrencyWon = MutableStateFlow(false)
    val isPriceCurrencyWon = _isPriceCurrencyWon.asStateFlow()

    private val _isLanguageKorean = MutableStateFlow(false)
    val isLanguageKorean = _isLanguageKorean.asStateFlow()

    private val _isSymbolGrid = MutableStateFlow(false)
    val isSymbolGrid = _isSymbolGrid.asStateFlow()

    init {
        loadSettings()
    }

    fun togglePriceCurrencyUnit(isChecked: Boolean) {
        val unit = if (isChecked) PriceCurrencyUnit.WON else PriceCurrencyUnit.DEFAULT
        savePriceCurrencyUnit(unit)
    }

    fun toggleLanguage(isChecked: Boolean) {
        val language = if (isChecked) Language.KOREAN else Language.DEFAULT
        saveLanguage(language)
    }

    fun toggleHowToShowSymbols(isChecked: Boolean) {
        val setting = if (isChecked) HowToShowSymbols.GRID2X2 else HowToShowSymbols.DEFAULT
        saveHowToShowSymbols(setting)
    }

    private fun loadSettings() {
        viewModelScope.launch {
            launch {
                settingRepository.getPriceCurrencyUnit().collect { unit ->
                    _isPriceCurrencyWon.value = (unit == PriceCurrencyUnit.WON)
                }
            }

            launch {
                settingRepository.getLanguage().collect { language ->
                    _isLanguageKorean.value = (language == Language.KOREAN)
                }
            }

            settingRepository.getHowToShowSymbols().collect { symbolSetting ->
                _isSymbolGrid.value = (symbolSetting == HowToShowSymbols.GRID2X2)
            }
        }
    }

    private fun savePriceCurrencyUnit(unit: PriceCurrencyUnit) = viewModelScope.launch {
        settingRepository.savePriceCurrencyUnit(unit)
    }

    private fun saveLanguage(language: Language) = viewModelScope.launch {
        settingRepository.saveLanguage(language)
    }

    private fun saveHowToShowSymbols(setting: HowToShowSymbols) = viewModelScope.launch {
        settingRepository.saveHowToShowSymbols(setting)
    }
}