
package com.mystiq.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SignUpRoute(
    onSignInClick: () -> Unit,
    onSignUpSuccess: () -> Unit,
    signUpViewModel: SignUpViewModel = viewModel()
) {
    val uiState by signUpViewModel.uiState.collectAsState()

    SignUpScreen(
        uiState = uiState,
        onFullNameChange = { signUpViewModel.onFullNameChange(it) },
        onUsernameChange = { signUpViewModel.onUsernameChange(it) },
        onEmailChange = { signUpViewModel.onEmailChange(it) },
        onPasswordChange = { signUpViewModel.onPasswordChange(it) },
        onConfirmPasswordChange = {
            signUpViewModel.onConfirmPasswordChange(it)
        },
        onTogglePasswordVisibility = {
            signUpViewModel.togglePasswordVisibility()
        },
        onToggleConfirmPasswordVisibility = {
            signUpViewModel.toggleConfirmPasswordVisibility()
        },
        onSignUpClick = {
            onSignUpSuccess()
        },
        onSignInClick = onSignInClick
    )
}
