package com.soma.coinviewer.feature.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.soma.coinviewer.common_ui.base.BaseBindingFragment
import com.soma.coinviewer.feature.setting.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment :
    BaseBindingFragment<FragmentSettingBinding, SettingViewModel>(FragmentSettingBinding::inflate) {
    override val fragmentViewModel: SettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = fragmentViewModel
    }
}