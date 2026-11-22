package com.mystiq.ui.screens.wishlist

data class WishlistItemUi(
    val id: Long,
    val name: String,
    val category: String,
    val imageUri: String? = null
)

data class WishlistUiState(
    val isLoading: Boolean = true,
    val items: List<WishlistItemUi> = emptyList()
)
