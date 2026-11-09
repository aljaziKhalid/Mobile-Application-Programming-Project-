package com.mystiq.ui.screens.tagging

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TaggingRoute(
    imageUri: String?,
    onBackClick: () -> Unit,
    onAddItemClick: () -> Unit,
    viewModel: TaggingViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(imageUri) {
        viewModel.setImageUri(imageUri)
    }

    TaggingScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onNameChange = viewModel::onNameChange,
        onCategoryChange = viewModel::onCategoryChange,
        onColorChange = viewModel::onColorChange,
        onBrandChange = viewModel::onBrandChange,
        onAddItemClick = {
            if (viewModel.canAddItem()) onAddItemClick()
        }
    )
}