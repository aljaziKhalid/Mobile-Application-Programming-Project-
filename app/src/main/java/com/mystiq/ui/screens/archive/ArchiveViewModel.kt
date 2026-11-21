package com.mystiq.ui.screens.archive

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ArchiveViewModel : ViewModel() {

    // No sample data: the archive stays empty until a real data source is connected.
    private val _uiState = mutableStateOf(ArchiveUIState())

    val uiState: State<ArchiveUIState> = _uiState

    init {
        loadArchive()
    }

    /**
     * Single entry point for loading archived items.
     * TODO: connect to the archive data source (for example archived clothing items
     * from Room through a repository) and fill archivedOutfits here. Until then it
     * only resets the loading and error flags and keeps the list as it is.
     */
    fun loadArchive() {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            errorMessage = null
        )
    }

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

    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    /** Pass null to show all categories. */
    fun onCategorySelected(category: String?) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun clearFilters() {
        _uiState.value = _uiState.value.copy(
            searchQuery = "",
            selectedCategory = null
        )
    }
}