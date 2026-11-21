package com.mystiq.ui.screens.archive

data class ArchivedOutfit(
    val id: Int,
    val name: String,
    val date: String,
    // Optional. Category filters are shown only when at least one item has a category.
    val category: String? = null
)

data class ArchiveUIState(
    val archivedOutfits: List<ArchivedOutfit> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val selectedCategory: String? = null
) {

    /** Distinct, non-blank categories found in the data (empty = no filter chips). */
    val categories: List<String>
        get() = archivedOutfits
            .mapNotNull { outfit -> outfit.category?.trim() }
            .filter { category -> category.isNotEmpty() }
            .distinct()

    /** The selected category, or null if it no longer exists in the data. */
    val activeCategory: String?
        get() = selectedCategory?.takeIf { selected -> selected in categories }

    val hasActiveFilters: Boolean
        get() = searchQuery.isNotBlank() || activeCategory != null

    /** Archive items after applying the search text and the selected category. */
    val filteredOutfits: List<ArchivedOutfit>
        get() {
            val query = searchQuery.trim()
            val category = activeCategory

            return archivedOutfits.filter { outfit ->
                val matchesCategory = category == null ||
                        outfit.category?.trim() == category

                val matchesQuery = query.isEmpty() ||
                        outfit.name.contains(query, ignoreCase = true) ||
                        outfit.date.contains(query, ignoreCase = true) ||
                        (outfit.category?.contains(query, ignoreCase = true) == true)

                matchesCategory && matchesQuery
            }
        }
}