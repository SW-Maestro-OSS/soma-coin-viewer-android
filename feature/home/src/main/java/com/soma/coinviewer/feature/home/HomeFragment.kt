package com.soma.coinviewer.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soma.coinviewer.common_ui.base.BaseComposeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class HomeFragment : BaseComposeFragment() {
    override val fragmentViewModel: HomeViewModel by viewModels()

    @Composable
    override fun ComposeLayout() {
        fragmentViewModel.apply {
            val uiState by homeUiState.collectAsStateWithLifecycle()

            fragmentViewModel.apply {
                HomeScreen(
                    uiState = uiState ?: "",
                    test = ::testBinance,
                    testDisconnect = ::testDisconnectBinance,
                )
            }
        }
    }
}


@Composable
private fun HomeScreen(
    uiState: String,
    test: () -> Unit,
    testDisconnect: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Hello CoinViewer")
        Text(text = "Binance Order book Test Response입니다!!")

        Spacer(
            modifier = Modifier
                .height(10.dp)
        )

        Text(text = uiState)

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = test,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Binance Connect")
        }

        Spacer(
            modifier = Modifier
                .height(5.dp)
        )

        Button(
            onClick = testDisconnect,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Binance Disconnect")
        }
    }
}