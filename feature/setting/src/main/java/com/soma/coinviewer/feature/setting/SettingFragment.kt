package com.soma.coinviewer.feature.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.soma.coinviewer.common_ui.base.BaseBindingFragment
import com.soma.coinviewer.presentation.R
import com.soma.coinviewer.presentation.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class SettingFragment :
    BaseBindingFragment<FragmentSettingBinding, SettingViewModel>(
        R.layout.fragment_setting
    ) {
    override val fragmentViewModel: SettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = fragmentViewModel.apply {

        }
    }
}