package com.example.mystic.outfit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OutfitViewModel : ViewModel() {

    // Placeholder data – replace with a repository/database call when real data is connected
    private val _uiState = MutableStateFlow(
        OutfitUiState(
            outfits = listOf(
                Outfit(1, listOf(1, 2)),
                Outfit(2, listOf(3, 2)),
                Outfit(3, listOf(1, 3)),
                Outfit(4, listOf(2, 3))
            )
        )
    )
    val uiState: StateFlow<OutfitUiState> = _uiState.asStateFlow()

    fun onOutfitClick(outfit: Outfit) {
        _uiState.update { state ->
            val newSelection =
                if (outfit.id in state.selectedIds) state.selectedIds - outfit.id
                else state.selectedIds + outfit.id
            state.copy(selectedIds = newSelection)
        }
    }

    fun onSelectAllClick() {
        _uiState.update { state -> state.copy(selectedIds = state.outfits.map { it.id }.toSet()) }
    }

    // X button – TODO confirm (clear selection vs delete)
    fun onCloseClick() {
        _uiState.update { it.copy(selectedIds = emptySet()) }
    }
}
