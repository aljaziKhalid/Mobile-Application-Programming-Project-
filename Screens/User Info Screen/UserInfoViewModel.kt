package com.mystiq.ui.screens.userinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserInfoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UserInfoUiState())
    val uiState: StateFlow<UserInfoUiState> = _uiState.asStateFlow()

    init {
        // TODO: load the current user from the repository (Room / DataStore) and fill the state.
    }

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, nameError = null) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email.trim(), emailError = null) }
    }

    fun onHeightChange(height: String) {
        // Accept digits only, max 3 characters (height in cm)
        if (height.length <= 3 && height.all { it.isDigit() }) {
            _uiState.update { it.copy(height = height, heightError = null) }
        }
    }

    fun onBodyShapeSelect(shape: BodyShape) {
        _uiState.update { it.copy(bodyShape = shape) }
    }

    fun onSaveClick() {
        val state = _uiState.value

        val nameError = if (state.name.isBlank()) "Name is required" else null
        val emailError = when {
            state.email.isBlank() -> "Email is required"
            !EMAIL_REGEX.matches(state.email) -> "Enter a valid email"
            else -> null
        }
        val heightValue = state.height.toIntOrNull()
        val heightError =
            if (state.height.isNotEmpty() && (heightValue == null || heightValue !in 50..250)) {
                "Height must be between 50 and 250 cm"
            } else null

        if (nameError != null || emailError != null || heightError != null) {
            _uiState.update {
                it.copy(nameError = nameError, emailError = emailError, heightError = heightError)
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            // TODO: userRepository.updateUser(state.name, state.email, heightValue, state.bodyShape)
            _uiState.update { it.copy(isSaving = false, isSaved = true) }
        }
    }

    fun onSavedMessageShown() {
        _uiState.update { it.copy(isSaved = false) }
    }

    private companion object {
        val EMAIL_REGEX = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    }
}
