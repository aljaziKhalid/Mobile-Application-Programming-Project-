
package com.mystiq.ui.screens.bodyshape

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BodyShapeRoute(
    onDone: (String) -> Unit,
    viewModel: BodyShapeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    BodyShapeScreen(
        uiState = uiState,
        onShapeSelected = viewModel::selectShape,
        onDone = {
            uiState.selectedShape?.let { shape ->
                onDone(shape)
            }
        }
    )
}
