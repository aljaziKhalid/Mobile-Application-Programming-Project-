package com.mystiq.ui.screens.home

data class HomeUiState(
    val isLoading: Boolean = false,
    val recentOutfits: List<String> = emptyList(),
    val errorMessage: String? = null
)