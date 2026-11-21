
package com.mystiq.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SignInRoute(
    onSignInSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit = {},
    signInViewModel: SignInViewModel = viewModel()
) {
    val uiState by signInViewModel.uiState.collectAsState()

    SignInScreen(
        username = uiState.username,
        password = uiState.password,
        onUsernameChange = {
            signInViewModel.onUsernameChange(it)
        },
        onPasswordChange = {
            signInViewModel.onPasswordChange(it)
        },
        onForgotPasswordClick = onForgotPasswordClick,
        onSignInClick = {
            onSignInSuccess()
        },
        onSignUpClick = onSignUpClick,
        isSignInEnabled = uiState.isSignInEnabled
    )
}

