
package com.mystiq.ui.screens.gender

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GenderRoute(
    onNext: (String) -> Unit,
    viewModel: GenderViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    GenderScreen(
        uiState = uiState,
        onGenderSelected = viewModel::selectGender,
        onNext = {
            uiState.selectedGender?.let { gender ->
                onNext(gender)
            }
        }
    )
}
