package com.mystiq.ui.screens.userinfo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Route = the connection between Navigation and the screen.
 * It owns the ViewModel, collects the state, and passes plain values + callbacks to the Screen.
 */
@Composable
fun UserInfoRoute(
    onBackClick: () -> Unit,
    viewModel: UserInfoViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UserInfoScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onHeightChange = viewModel::onHeightChange,
        onBodyShapeSelect = viewModel::onBodyShapeSelect,
        onSaveClick = viewModel::onSaveClick,
        onSavedMessageShown = viewModel::onSavedMessageShown
    )
}
