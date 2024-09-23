package com.soma.coinviewer.feature.home

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = ViewModelProvider(
        LocalContext.current as ComponentActivity
    ).get(HomeViewModel::class.java)

    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Hello CoinViewer")
        Text(text = "Binance Order book Test Response입니다!!")

        Spacer(
            modifier = Modifier
                .height(10.dp)
        )

        Text(text = viewModel.homeUiState.collectAsStateWithLifecycle().value ?: "")

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.testBinance() },
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
            onClick = { viewModel.testDisconnectBinance() },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Binance Disconnect")
        }
    }
}