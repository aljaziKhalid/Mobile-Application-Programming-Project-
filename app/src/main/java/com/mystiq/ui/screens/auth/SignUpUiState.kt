
package com.mystiq.ui.screens.auth

data class SignUpUiState(
    val fullName: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val passwordVisible: Boolean = false,
    val confirmPasswordVisible: Boolean = false,
    val isSignUpEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
