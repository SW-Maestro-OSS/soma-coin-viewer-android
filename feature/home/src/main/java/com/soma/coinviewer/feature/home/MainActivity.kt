package com.soma.coinviewer.feature.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(){

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        }
    }
}