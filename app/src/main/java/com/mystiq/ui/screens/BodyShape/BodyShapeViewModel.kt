
package com.mystiq.ui.screens.bodyshape

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BodyShapeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BodyShapeUiState())
    val uiState: StateFlow<BodyShapeUiState> = _uiState.asStateFlow()

    fun selectShape(shape: String) {
        _uiState.value = BodyShapeUiState(
            selectedShape = shape
        )
    }
}
