package com.example.mystic.outfit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun OutfitRoute(
    onBackClick: () -> Unit = {},
    viewModel: OutfitViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OutfitScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onOutfitClick = viewModel::onOutfitClick,
        onSelectAllClick = viewModel::onSelectAllClick,
        onCloseClick = viewModel::onCloseClick
    )
}
.