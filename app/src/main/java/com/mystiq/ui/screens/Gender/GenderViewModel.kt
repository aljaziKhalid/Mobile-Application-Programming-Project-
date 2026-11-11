
package com.mystiq.ui.screens.gender

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GenderViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GenderUiState())
    val uiState: StateFlow<GenderUiState> = _uiState.asStateFlow()

    fun selectGender(gender: String) {
        _uiState.value = GenderUiState(
            selectedGender = gender
        )
    }
}
