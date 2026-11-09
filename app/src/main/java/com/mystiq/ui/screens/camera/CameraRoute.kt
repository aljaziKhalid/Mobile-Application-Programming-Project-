package com.mystiq.ui.screens.camera

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CameraRoute(
    onBackClick: () -> Unit,
    onImageReady: (String) -> Unit,
    onCaptureClick: () -> Unit,
    viewModel: CameraViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val galleryPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onImageReady(it.toString()) }
    }

    CameraScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onFlashClick = viewModel::onFlashClick,
        onCaptureClick = onCaptureClick,
        onGalleryClick = { galleryPicker.launch("image/*") }
    )
}