package com.mystiq.ui.screens.archive

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ArchiveViewModel : ViewModel() {

    private val _uiState = mutableStateOf(
        ArchiveUIState(
            archivedOutfits = listOf(
                ArchivedOutfit(1, "Casual Outfit", "Today"),
                ArchivedOutfit(2, "Formal Outfit", "Yesterday"),
                ArchivedOutfit(3, "Summer Outfit", "Last Week")
            )
        )
    )

    val uiState: State<ArchiveUIState> = _uiState

    fun removeOutfit(id: Int) {
        _uiState.value = _uiState.value.copy(
            archivedOutfits = _uiState.value.archivedOutfits
                .filterNot { it.id == id }
        )
    }

    fun addOutfit(outfit: ArchivedOutfit) {
        _uiState.value = _uiState.value.copy(
            archivedOutfits = _uiState.value.archivedOutfits + outfit
        )
    }
}