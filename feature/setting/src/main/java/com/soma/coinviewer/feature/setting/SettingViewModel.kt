package com.soma.coinviewer.feature.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soma.coinviewer.common_ui.base.BaseViewModel
import com.soma.coinviewer.domain.datastore.PriceCurrencyUnit
import com.soma.coinviewer.domain.datastore.Language
import com.soma.coinviewer.domain.datastore.HowToShowSymbols
import com.soma.coinviewer.domain.datastore.SettingDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingDataStore: SettingDataStore,
) : BaseViewModel() {

    private val _priceCurrencyUnit = MutableLiveData<PriceCurrencyUnit>()
    val priceCurrencyUnit: LiveData<PriceCurrencyUnit> get() = _priceCurrencyUnit

    private val _language = MutableLiveData<Language>()
    val language: LiveData<Language> get() = _language

    private val _howToShowSymbols = MutableLiveData<HowToShowSymbols>()
    val howToShowSymbols: LiveData<HowToShowSymbols> get() = _howToShowSymbols

    // ✅ 데이터 저장
    fun savePriceCurrencyUnit(priceCurrencyUnit: PriceCurrencyUnit) {
        viewModelScope.launch {
            settingDataStore.savePriceCurrencyUnit(priceCurrencyUnit)
        }
    }

    fun saveLanguage(language: Language) {
        viewModelScope.launch {
            settingDataStore.saveLanguage(language)
        }
    }

    fun saveHowToShowSymbols(howToShowSymbols: HowToShowSymbols) {
        viewModelScope.launch {
            settingDataStore.saveHowToShowSymbols(howToShowSymbols)
        }
    }

    // ✅ 데이터 조회
    fun getPriceCurrencyUnit() {
        viewModelScope.launch {
            settingDataStore.getPriceCurrencyUnit().collect { selectType ->
                _priceCurrencyUnit.value = selectType
            }
        }
    }

    fun getLanguage() {
        viewModelScope.launch {
            settingDataStore.getLanguage().collect { selectType ->
                _language.value = selectType
            }
        }
    }

    fun getHowToShowSymbols() {
        viewModelScope.launch {
            settingDataStore.getHowToShowSymbols().collect { selectType ->
                _howToShowSymbols.value = selectType
            }
        }
    }
}