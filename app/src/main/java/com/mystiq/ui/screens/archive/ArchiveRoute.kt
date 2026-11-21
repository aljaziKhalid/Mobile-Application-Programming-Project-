package com.mystiq.ui.screens.archive

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ArchiveRoute(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit = onBackClick,
    onClosetClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onOutfitsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    viewModel: ArchiveViewModel = viewModel()
) {
    ArchiveScreen(
        uiState = viewModel.uiState.value,
        onBackClick = onBackClick,
        onDeleteClick = viewModel::removeOutfit,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onCategorySelected = viewModel::onCategorySelected,
        onClearFilters = viewModel::clearFilters,
        onRetryClick = viewModel::loadArchive,
        onHomeClick = onHomeClick,
        onClosetClick = onClosetClick,
        onAddClick = onAddClick,
        onOutfitsClick = onOutfitsClick,
        onProfileClick = onProfileClick
    )
}