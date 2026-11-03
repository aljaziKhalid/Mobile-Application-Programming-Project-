package com.mystiq.ui.screens.auth

data class SignInUiState(
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isSignInEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


