package com.mystiq.ui.screens.tagging

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaggingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TaggingUiState())
    val uiState: StateFlow<TaggingUiState> = _uiState.asStateFlow()

    fun setImageUri(uri: String?) {
        _uiState.update { it.copy(imageUri = uri) }
    }

    fun onNameChange(value: String) {
        _uiState.update { it.copy(name = value, errorMessage = null) }
    }

    fun onCategoryChange(value: String) {
        _uiState.update { it.copy(category = value, errorMessage = null) }
    }

    fun onColorChange(value: String) {
        _uiState.update { it.copy(color = value, errorMessage = null) }
    }

    fun onBrandChange(value: String) {
        _uiState.update { it.copy(brand = value, errorMessage = null) }
    }

    // تحقق مبدئي فقط؛ حفظ القطعة في Room نضيفه لاحقًا.
    fun canAddItem(): Boolean {
        val state = _uiState.value
        if (state.name.isBlank() || state.category.isBlank()) {
            _uiState.update {
                it.copy(errorMessage = "Please enter the item name and category.")
            }
            return false
        }
        return true
    }
}