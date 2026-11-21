
package com.mystiq.ui.screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProfileRoute(
    onLogoutClick: () -> Unit,
    onClosetClick: () -> Unit,
    onBodyShapeClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onPreferencesClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onOutfitsClick: () -> Unit = {},
    profileViewModel: ProfileViewModel = viewModel()
) {
    val uiState by profileViewModel.uiState.collectAsState()

    ProfileScreen(
        uiState = uiState,
        onEditProfileClick = onEditProfileClick,
        onBodyShapeClick = onBodyShapeClick,
        onSettingsClick = onSettingsClick,
        onPreferencesClick = onPreferencesClick,
        onLogoutClick = onLogoutClick,
        onClosetClick = onClosetClick,
        onHomeClick = onHomeClick,
        onAddClick = onAddClick,
        onOutfitsClick = onOutfitsClick
    )
}
