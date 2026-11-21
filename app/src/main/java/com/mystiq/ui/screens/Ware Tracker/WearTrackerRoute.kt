package com.mystiq.ui.screens.WareTracker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WearTrackerRoute(
    viewModel: WearTrackerViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onClosetClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onOutfitsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    WearTrackerScreen(
        state = state,
        onBackClick = onNavigateBack,
        onHomeClick = onHomeClick,
        onClosetClick = onClosetClick,
        onAddClick = onAddClick,
        onOutfitsClick = onOutfitsClick,
        onProfileClick = onProfileClick
    )
}