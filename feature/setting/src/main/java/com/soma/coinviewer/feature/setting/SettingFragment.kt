package com.soma.coinviewer.feature.setting

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.soma.coinviewer.common_ui.base.BaseBindingFragment
import com.soma.coinviewer.domain.datastore.HowToShowSymbols
import com.soma.coinviewer.domain.datastore.Language
import com.soma.coinviewer.domain.datastore.PriceCurrencyUnit
import com.soma.coinviewer.feature.setting.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment :
    BaseBindingFragment<FragmentSettingBinding, SettingViewModel>(FragmentSettingBinding::inflate) {
    override val fragmentViewModel: SettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = fragmentViewModel

        // ✅ Switch Listener
        binding.switchPriceCurrency.setOnCheckedChangeListener { _, isChecked ->
            binding.viewModel?.let { viewModel ->
                val selectPriceCurrencyUnit = if (isChecked) {
                    PriceCurrencyUnit.WON
                } else {
                    PriceCurrencyUnit.DEFAULT
                }

                viewModel.savePriceCurrencyUnit(selectPriceCurrencyUnit)
            }
        }

        binding.switchLanguage.setOnCheckedChangeListener { _, isChecked ->
            binding.viewModel?.let { viewModel ->
                val selectLanguage = if (isChecked) {
                    Language.KOREAN
                } else {
                    Language.DEFAULT
                }

                viewModel.saveLanguage(selectLanguage)
            }
        }

        binding.switchShowSymbolGrid.setOnCheckedChangeListener { _, isChecked ->
            binding.viewModel?.let { viewModel ->
                val selectHowToShowSymbols = if (isChecked) {
                    HowToShowSymbols.Grid2x2
                } else {
                    HowToShowSymbols.DEFAULT
                }

                viewModel.saveHowToShowSymbols(selectHowToShowSymbols)
            }
        }
    }
}