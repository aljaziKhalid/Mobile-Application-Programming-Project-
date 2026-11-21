package com.mystiq.ui.screens.closet

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ClosetRoute(
    onBackClick: () -> Unit,           // back arrow / system back -> home
    onAddItemClick: () -> Unit,        // "+" button and "Scan your first item" -> camera
    onStatsClick: () -> Unit,
    onWearTrackerClick: () -> Unit,
    onCategoriesClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    onOutfitsClick: () -> Unit = {},
    onWishlistClick: () -> Unit = {},  // heart button -> wishlist
    viewModel: ClosetViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // System back button also returns to Home
    BackHandler(onBack = onBackClick)

    ClosetScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onRetryClick = viewModel::loadItems,
        onAddItemClick = onAddItemClick,
        onStatsClick = onStatsClick,
        onWearTrackerClick = onWearTrackerClick,
        onCategoriesClick = onCategoriesClick,
        onItemClick = onItemClick,
        onHomeClick = onHomeClick,
        onOutfitsClick = onOutfitsClick,
        onProfileClick = onProfileClick,
        onWishlistClick = onWishlistClick
    )
}
