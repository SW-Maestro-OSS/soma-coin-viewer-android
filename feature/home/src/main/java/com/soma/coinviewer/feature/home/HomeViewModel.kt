package com.soma.coinviewer.feature.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class HomeViewModel : ViewModel() {
    private val _homeUiState = MutableStateFlow<String>("")
    val homeUiState: StateFlow<String> get() = _homeUiState

}