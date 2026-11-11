package com.mystiq.ui.screens.archive

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ArchiveRoute(
    onBackClick: () -> Unit,
    viewModel: ArchiveViewModel = viewModel()
) {
    ArchiveScreen(
        uiState = viewModel.uiState.value,
        onBackClick = onBackClick,
        onDeleteClick = viewModel::removeOutfit
    )
}