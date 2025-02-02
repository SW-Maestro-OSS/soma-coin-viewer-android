package com.soma.coinviewer.feature.setting

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.soma.coinviewer.common_ui.base.BaseBindingFragment
import com.soma.coinviewer.common_ui.repeatOnStarted
import com.soma.coinviewer.feature.setting.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class SettingFragment :
    BaseBindingFragment<FragmentSettingBinding, SettingViewModel>(FragmentSettingBinding::inflate) {
    override val fragmentViewModel: SettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnStarted {
                fragmentViewModel.isLanguageKorean.collect { isKorean ->
                    val locale = if (isKorean) Locale.KOREAN else Locale.US
                    updateLocale(locale)
                }
            }
        }

        binding.apply {
            viewModel = fragmentViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun updateLocale(locale: Locale) {
        Log.d("test", "updateLocale 호출!")
        // Todo
    }
}
