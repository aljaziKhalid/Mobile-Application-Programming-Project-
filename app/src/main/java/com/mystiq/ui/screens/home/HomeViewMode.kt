package com.mystiq.ui.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> =
        _uiState.asStateFlow()

    fun loadRecentOutfits() {
        // TODO: Connect to saved outfits repository
        _uiState.update {
            it.copy(
                isLoading = false,
                recentOutfits = emptyList(),
                errorMessage = null
            )
        }
    }

    init {
        loadRecentOutfits()
    }
}