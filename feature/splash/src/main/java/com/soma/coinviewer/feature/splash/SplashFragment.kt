package com.soma.coinviewer.feature.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.soma.coinviewer.common_ui.base.BaseBindingFragment
import com.soma.coinviewer.feature.splash.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseBindingFragment<FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::inflate
) {
    override val fragmentViewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = fragmentViewModel
    }
}