package com.mystiq.ui.screens.closet

/** What the screen needs to draw one clothing item. */
data class ClosetItemUi(
    val id: Long,
    val name: String,
    val category: String,
    val imageUri: String? = null
)

/**
 * The closet has 4 UI states:
 *  1. Loading  -> isLoading = true
 *  2. Error    -> errorMessage != null
 *  3. Empty    -> items is empty  (the design in the wireframe)
 *  4. Content  -> items has data
 */
data class ClosetUiState(
    val isLoading: Boolean = true,
    val items: List<ClosetItemUi> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null
) {
    val visibleItems: List<ClosetItemUi>
        get() = if (searchQuery.isBlank()) items else items.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true)
        }
}
