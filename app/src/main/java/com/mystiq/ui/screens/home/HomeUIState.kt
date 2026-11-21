package com.mystiq.ui.screens.home

/**
 * Lightweight preview of a clothing item for the "Recently added" section.
 * Kept separate from the Room entity so the UI layer does not depend on the data layer.
 */
data class HomeClothingPreview(
    val id: Int,
    val name: String,
    val category: String? = null
)

data class HomeUiState(
    val isLoading: Boolean = false,
    val recentOutfits: List<String> = emptyList(),
    val errorMessage: String? = null,

    // Wardrobe summary. null = not available yet (shown as "–" in the UI).
    val clothingCount: Int? = null,
    val outfitCount: Int? = null,
    val archivedCount: Int? = null,

    // Recently added clothing. Empty until a data source fills it.
    val recentItems: List<HomeClothingPreview> = emptyList()
)