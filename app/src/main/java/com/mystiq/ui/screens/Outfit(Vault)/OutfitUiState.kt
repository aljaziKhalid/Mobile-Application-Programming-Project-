package com.example.mystic.outfit

data class Outfit(
    val id: Int,
    val itemIds: List<Int>   // an outfit = several closet items saved together
)

data class OutfitUiState(
    val outfits: List<Outfit> = emptyList(),
    val selectedIds: Set<Int> = emptySet()
)