package com.soma.coinviewer.feature.splash

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.soma.coinviewer.common_ui.base.BaseBindingFragment
import com.soma.coinviewer.common_ui.repeatOnStarted
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

        fragmentViewModel.apply {
            repeatOnStarted {
                eventFlow.collect { handleEvent(it) }
            }
        }
    }

    private fun handleEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.TimerDone ->
                findNavController().navigate(
                    deepLink = "soma://home".toUri(),
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(destinationId = R.id.splashFragment, inclusive = true)
                        .build()
                )
        }
    }
}