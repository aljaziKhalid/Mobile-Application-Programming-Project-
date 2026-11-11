package com.mystiq.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoute(
    onClosetClick: () -> Unit,
    onVaultClick: () -> Unit,
    onArchiveClick: () -> Unit,
    onCameraClick: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState.value,
        onClosetClick = onClosetClick,
        onVaultClick = onVaultClick,
        onArchiveClick = onArchiveClick,
        onCameraClick = onCameraClick,
        onProfileClick = onProfileClick
    )
}