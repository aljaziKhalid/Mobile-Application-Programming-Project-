package com.mystiq.ui.screens.wishlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WishlistRoute(
    onBackClick: () -> Unit,
    onAddClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    viewModel: WishlistViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    WishlistScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onAddClick = onAddClick,
        onItemClick = onItemClick,
        onRemoveClick = viewModel::onRemoveClick
    )
}
