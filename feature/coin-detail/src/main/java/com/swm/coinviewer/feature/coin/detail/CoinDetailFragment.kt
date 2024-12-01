package com.swm.coinviewer.feature.coin.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.soma.coinviewer.common_ui.base.BaseComposeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailFragment : BaseComposeFragment() {
    override val fragmentViewModel: CoinDetailViewModel by viewModels()
    private val args: CoinDetailFragmentArgs by navArgs()

    @Composable
    override fun ComposeLayout() {
        val coinId = args.coinId

        fragmentViewModel.apply {
            CoinDetailScreen()
        }
    }
}

@Composable
private fun CoinDetailScreen() {
    Scaffold(containerColor = Color.White) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {

        }
    }
}