package com.soma.coinviewer.feature.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.preferences.PriceCurrencyUnit
import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import com.soma.coinviewer.domain.preferences.Language
import com.soma.coinviewer.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
) : BaseViewModel() {

    private val _priceCurrencyUnit = MutableLiveData<PriceCurrencyUnit>()
    val priceCurrencyUnit: LiveData<PriceCurrencyUnit> get() = _priceCurrencyUnit

    private val _language = MutableLiveData<Language>()
    val language: LiveData<Language> get() = _language

    private val _howToShowSymbols = MutableLiveData<HowToShowSymbols>()
    val howToShowSymbols: LiveData<HowToShowSymbols> get() = _howToShowSymbols

    fun savePriceCurrencyUnit(priceCurrencyUnit: PriceCurrencyUnit) {
        viewModelScope.launch {
            settingRepository.savePriceCurrencyUnit(priceCurrencyUnit)
        }
    }

    fun saveLanguage(language: Language) {
        viewModelScope.launch {
            settingRepository.saveLanguage(language)
        }
    }

    fun saveHowToShowSymbols(howToShowSymbols: HowToShowSymbols) {
        viewModelScope.launch {
            settingRepository.saveHowToShowSymbols(howToShowSymbols)
        }
    }

    fun getPriceCurrencyUnit() {
        viewModelScope.launch {
            settingRepository.getPriceCurrencyUnit().collect { selectType ->
                _priceCurrencyUnit.value = selectType
            }
        }
    }

    fun getLanguage() {
        viewModelScope.launch {
            settingRepository.getLanguage().collect { selectType ->
                _language.value = selectType
            }
        }
    }

    fun getHowToShowSymbols() {
        viewModelScope.launch {
            settingRepository.getHowToShowSymbols().collect { selectType ->
                _howToShowSymbols.value = selectType
            }
        }
    }
}