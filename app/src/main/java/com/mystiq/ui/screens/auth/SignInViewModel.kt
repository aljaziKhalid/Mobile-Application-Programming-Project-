
package com.mystiq.ui.screens.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun onUsernameChange(value: String) {
        _uiState.update { it.copy(username = value) }
        validateForm()
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
        validateForm()
    }

    fun togglePasswordVisibility() {
        _uiState.update {
            it.copy(passwordVisible = !it.passwordVisible)
        }
    }

    private fun validateForm() {
        _uiState.update { state ->
            state.copy(
                isSignInEnabled =
                    state.username.isNotBlank() &&
                            state.password.isNotBlank()
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

