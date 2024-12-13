package com.soma.coinviewer.feature.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.soma.coinviewer.common_ui.base.BaseBindingFragment
import com.soma.coinviewer.domain.preferences.CurrencyCode
import com.soma.coinviewer.domain.preferences.Language
import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import com.soma.coinviewer.feature.setting.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment :
    BaseBindingFragment<FragmentSettingBinding, SettingViewModel>(FragmentSettingBinding::inflate) {
    override val fragmentViewModel: SettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSelectType()

        // 이전 선택 정보 불러오기
        fragmentViewModel.getPriceCurrencyUnit()
        fragmentViewModel.getLanguage()
        fragmentViewModel.getHowToShowSymbols()

        // ✅ Switch Listener
        binding.switchPriceCurrency.setOnCheckedChangeListener { _, isChecked ->
            val selectCurrencyCode = if (isChecked) {
                CurrencyCode.WON
            } else {
                CurrencyCode.DEFAULT
            }

            fragmentViewModel.savePriceCurrencyUnit(selectCurrencyCode)
        }

        binding.switchLanguage.setOnCheckedChangeListener { _, isChecked ->
            val selectLanguage = if (isChecked) {
                Language.KOREAN
            } else {
                Language.DEFAULT
            }

            fragmentViewModel.saveLanguage(selectLanguage)
        }

        binding.switchShowSymbolGrid.setOnCheckedChangeListener { _, isChecked ->
            val selectHowToShowSymbols = if (isChecked) {
                HowToShowSymbols.GRID2X2
            } else {
                HowToShowSymbols.DEFAULT
            }

            fragmentViewModel.saveHowToShowSymbols(selectHowToShowSymbols)
        }
    }

    private fun observeSelectType() {
        fragmentViewModel.apply {
            currencyCode.observe(viewLifecycleOwner, Observer { priceCurrencyUnit ->
                binding.switchPriceCurrency.isChecked =
                    (priceCurrencyUnit.value != CurrencyCode.DEFAULT.value)
            })

            language.observe(viewLifecycleOwner, Observer { language ->
                binding.switchLanguage.isChecked =
                    (language.value != Language.DEFAULT.value)
            })

            howToShowSymbols.observe(viewLifecycleOwner, Observer { howToShowSymbols ->
                binding.switchShowSymbolGrid.isChecked =
                    (howToShowSymbols.value != HowToShowSymbols.DEFAULT.value)
            })
        }
    }
}