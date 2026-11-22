
package com.mystiq.ui.screens.Spread

data class SpreadUiState(
    val shoesPercentage: Int = 0,
    val shirtsPercentage: Int = 0,
    val jeansPercentage: Int = 0,
    val hatsPercentage: Int = 0,
    val dominantColorPercentage: Int = 0,
    val dominantColorName: String = "No color yet",
    val isLoading: Boolean = true
)
