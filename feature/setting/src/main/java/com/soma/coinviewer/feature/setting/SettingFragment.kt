package com.soma.coinviewer.feature.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.soma.coinviewer.common_ui.base.BaseBindingFragment
import com.soma.coinviewer.domain.datastore.PriceCurrencyUnit
import com.soma.coinviewer.domain.datastore.Language
import com.soma.coinviewer.domain.datastore.HowToShowSymbols
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
            val selectPriceCurrencyUnit = if (isChecked) {
                PriceCurrencyUnit.WON
            } else {
                PriceCurrencyUnit.DEFAULT
            }

            fragmentViewModel.savePriceCurrencyUnit(selectPriceCurrencyUnit)
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
                HowToShowSymbols.Grid2x2
            } else {
                HowToShowSymbols.DEFAULT
            }

            fragmentViewModel.saveHowToShowSymbols(selectHowToShowSymbols)
        }
    }

    private fun observeSelectType() {
        fragmentViewModel.priceCurrencyUnit.observe(viewLifecycleOwner, Observer { priceCurrencyUnit ->
            binding.switchPriceCurrency.isChecked = (priceCurrencyUnit.value != PriceCurrencyUnit.DEFAULT.value)
        })

        fragmentViewModel.language.observe(viewLifecycleOwner, Observer { language ->
            binding.switchLanguage.isChecked = (language.value != Language.DEFAULT.value)
        })

        fragmentViewModel.howToShowSymbols.observe(viewLifecycleOwner, Observer { howToShowSymbols ->
            binding.switchShowSymbolGrid.isChecked = (howToShowSymbols.value != HowToShowSymbols.DEFAULT.value)
        })
    }
}