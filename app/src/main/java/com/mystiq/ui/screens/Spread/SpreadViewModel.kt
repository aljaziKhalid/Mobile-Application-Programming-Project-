
package com.mystiq.ui.screens.Spread

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mystiq.data.repository.SpreadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SpreadViewModel(
    private val repository: SpreadRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SpreadUiState())
    val uiState: StateFlow<SpreadUiState> = _uiState.asStateFlow()

    init {
        loadSpreadData()
    }

    private fun loadSpreadData() {
        viewModelScope.launch {
            repository.observeSpreadData().collect { data ->

                val totalItems = data.categories.sumOf { it.itemCount }

                val categoryPercentages = data.categories.associate {
                    it.categoryId to if (totalItems > 0) {
                        (it.itemCount * 100) / totalItems
                    } else {
                        0
                    }
                }

                val dominantColor = data.colors.maxByOrNull {
                    it.itemCount
                }

                val colorTotal = data.colors.sumOf { it.itemCount }

                val colorPercentage =
                    if (colorTotal > 0 && dominantColor != null) {
                        (dominantColor.itemCount * 100) / colorTotal
                    } else {
                        0
                    }

                _uiState.value = SpreadUiState(
                    dominantColorPercentage = colorPercentage,
                    dominantColorName = dominantColor?.color ?: "No color yet",
                    isLoading = false
                )
            }
        }
    }
}
