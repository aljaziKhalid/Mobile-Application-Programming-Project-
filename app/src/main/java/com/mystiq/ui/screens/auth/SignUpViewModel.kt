
package com.mystiq.ui.screens.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun onFullNameChange(value: String) {
        _uiState.update { it.copy(fullName = value) }
        validateForm()
    }

    fun onUsernameChange(value: String) {
        _uiState.update { it.copy(username = value) }
        validateForm()
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value) }
        validateForm()
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
        validateForm()
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update { it.copy(confirmPassword = value) }
        validateForm()
    }

    fun togglePasswordVisibility() {
        _uiState.update {
            it.copy(passwordVisible = !it.passwordVisible)
        }
    }

    fun toggleConfirmPasswordVisibility() {
        _uiState.update {
            it.copy(confirmPasswordVisible = !it.confirmPasswordVisible)
        }
    }

    private fun validateForm() {
        _uiState.update { state ->
            state.copy(
                isSignUpEnabled =
                    state.fullName.isNotBlank() &&
                            state.username.isNotBlank() &&
                            state.email.contains("@") &&
                            state.password.length >= 6 &&
                            state.password == state.confirmPassword
            )
        }
    }

    fun setLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    fun setError(message: String?) {
        _uiState.update { it.copy(errorMessage = message) }
    }
}
