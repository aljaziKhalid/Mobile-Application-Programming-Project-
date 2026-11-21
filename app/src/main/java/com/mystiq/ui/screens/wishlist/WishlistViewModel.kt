package com.mystiq.ui.screens.wishlist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WishlistViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WishlistUiState())
    val uiState: StateFlow<WishlistUiState> = _uiState.asStateFlow()

    init {
        loadWishlist()
    }

    private fun loadWishlist() {
        // TODO: replace sample data with wishlistRepository.getWishlist() (Room Flow)
        _uiState.update { it.copy(isLoading = false, items = sampleItems) }
    }

    /** Tapping the heart on a card removes it from the wishlist. */
    fun onRemoveClick(itemId: Long) {
        // TODO: wishlistRepository.deleteItem(itemId)
        _uiState.update { state -> state.copy(items = state.items.filterNot { it.id == itemId }) }
    }
}

private val sampleItems = listOf(
    WishlistItemUi(1, "Sunglasses", "Accessories"),
    WishlistItemUi(2, "Navy Coat", "Outerwear"),
    WishlistItemUi(3, "White high heels", "Shoes"),
    WishlistItemUi(4, "Vintage tote bag", "Bags")
)
