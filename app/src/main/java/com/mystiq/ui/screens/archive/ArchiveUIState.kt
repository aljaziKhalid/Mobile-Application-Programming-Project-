package com.mystiq.ui.screens.archive

data class ArchivedOutfit(
    val id: Int,
    val name: String,
    val date: String
)

data class ArchiveUIState(
    val archivedOutfits: List<ArchivedOutfit> = emptyList(),
    val isLoading: Boolean = false
)