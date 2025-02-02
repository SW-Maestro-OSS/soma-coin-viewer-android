package com.soma.coinviewer.feature.setting

import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import com.soma.coinviewer.domain.repository.SettingRepository
import com.soma.coinviewer.i18n.Currency
import com.soma.coinviewer.i18n.I18NHelper
import com.soma.coinviewer.i18n.USDCurrency
import com.soma.coinviewer.i18n.koreanCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
    private val i18NHelper: I18NHelper,
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

    fun toggleCurrency(isChecked: Boolean) {
        val currency = if (isChecked) koreanCurrency else USDCurrency
        savePriceCurrencyUnit(currency)
    }

    fun toggleLanguage(isChecked: Boolean) {
        val language = if (isChecked) Locale.KOREAN else Locale.US
        saveLanguage(language)
    }

    fun toggleHowToShowSymbols(isChecked: Boolean) {
        val setting = if (isChecked) HowToShowSymbols.GRID2X2 else HowToShowSymbols.DEFAULT
        saveHowToShowSymbols(setting)
    }

    private fun loadSettings() {
        viewModelScope.launch {
            launch {
                val region = i18NHelper.getRegion()

                _isPriceCurrencyWon.value = (region.currency == koreanCurrency)
                _isLanguageKorean.value = (region.language == Locale.KOREAN)
            }

            val howToShowSymbols = settingRepository.getHowToShowSymbols()
            _isSymbolGrid.value = (howToShowSymbols == HowToShowSymbols.GRID2X2)
        }
    }

    private fun savePriceCurrencyUnit(currency: Currency) = viewModelScope.launch {
        val region = i18NHelper.getRegion()
        i18NHelper.saveRegion(region.copy(currency = currency))
    }

    private fun saveLanguage(locale: Locale) = viewModelScope.launch {
        val region = i18NHelper.getRegion()
        i18NHelper.saveRegion(region.copy(language = locale))
    }

    private fun saveHowToShowSymbols(setting: HowToShowSymbols) = viewModelScope.launch {
        settingRepository.saveHowToShowSymbols(setting)
    }
}
