package com.soma.coinviewer.feature.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(){

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.testBinance()

        enableEdgeToEdge()
        setContent {
            MyApp {
                HomeScreen()
            }
        }
    }

    @Composable
    private fun MyApp(content: @Composable (modifier: Modifier) -> Unit) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) { innerPadding ->
            content(
                Modifier.padding(innerPadding)
            )
        }
    }

    @Composable
    private fun HomeScreen() {
        Column (
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(text = "Hello CoinViewer")

            Text(text = viewModel.homeUiState.value)

            Spacer(modifier = Modifier.weight(1f))

            Surface(
                onClick = { viewModel.disconnectBinance() },
                color = Color.Blue,
                contentColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "Web Socket 연결 끊기",
                    fontSize = 15.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp)
                        .wrapContentWidth()
                )
            }
        }
    }
}