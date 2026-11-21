package com.mystiq.ui.screens.archive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mystiq.ui.components.MystiqBottomBar
import com.mystiq.ui.theme.Mystiq_FinalTheme

@Composable
fun ArchiveScreen(
    uiState: ArchiveUIState,
    onBackClick: () -> Unit,
    onDeleteClick: (Int) -> Unit,
    onSearchQueryChange: (String) -> Unit = {},
    onCategorySelected: (String?) -> Unit = {},
    onClearFilters: () -> Unit = {},
    onRetryClick: () -> Unit = {},
    onHomeClick: () -> Unit = onBackClick,
    onClosetClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onOutfitsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme

    // Id of the item waiting for delete confirmation (null = no dialog).
    var pendingDeleteId by rememberSaveable { mutableStateOf<Int?>(null) }
    val pendingDelete = uiState.archivedOutfits.firstOrNull { it.id == pendingDeleteId }

    Scaffold(
        containerColor = colors.background,
        bottomBar = {
            // Archive is opened from Home, so the Home tab stays highlighted.
            MystiqBottomBar(
                selectedTab = "home",
                onHomeClick = onHomeClick,
                onClosetClick = onClosetClick,
                onAddClick = onAddClick,
                onOutfitsClick = onOutfitsClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->

        val showSearch = !uiState.isLoading &&
                uiState.errorMessage == null &&
                uiState.archivedOutfits.isNotEmpty()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ArchiveHeader(
                    count = uiState.archivedOutfits.size,
                    onBackClick = onBackClick
                )

                if (showSearch) {
                    ArchiveSearchBar(
                        query = uiState.searchQuery,
                        onQueryChange = onSearchQueryChange
                    )

                    if (uiState.categories.isNotEmpty()) {
                        CategoryChips(
                            categories = uiState.categories,
                            selected = uiState.activeCategory,
                            onCategorySelected = onCategorySelected
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                val filtered = uiState.filteredOutfits

                when {
                    uiState.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = colors.onBackground)
                        }
                    }

                    uiState.errorMessage != null -> {
                        ArchiveErrorState(
                            message = uiState.errorMessage,
                            onRetryClick = onRetryClick
                        )
                    }

                    uiState.archivedOutfits.isEmpty() -> {
                        ArchiveMessageState(
                            icon = Icons.Outlined.Inventory2,
                            title = "Nothing archived yet",
                            message = "Outfits you archive will be kept safely here.",
                            actionLabel = "Back to Home",
                            onActionClick = onBackClick
                        )
                    }

                    filtered.isEmpty() -> {
                        ArchiveMessageState(
                            icon = Icons.Outlined.SearchOff,
                            title = "No results found",
                            message = "Nothing matches your search or filter.\nTry different words or clear the filters.",
                            actionLabel = "Clear filters",
                            onActionClick = onClearFilters
                        )
                    }

                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                start = 20.dp,
                                end = 20.dp,
                                top = 16.dp,
                                bottom = 16.dp
                            ),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = filtered,
                                key = { it.id }
                            ) { outfit ->
                                ArchiveCard(
                                    outfit = outfit,
                                    onDeleteClick = { pendingDeleteId = outfit.id }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (pendingDelete != null) {
        AlertDialog(
            onDismissRequest = { pendingDeleteId = null },
            shape = RoundedCornerShape(20.dp),
            containerColor = colors.surfaceVariant,
            titleContentColor = colors.onSurfaceVariant,
            textContentColor = colors.onSurfaceVariant,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.DeleteOutline,
                    contentDescription = null,
                    tint = colors.error
                )
            },
            title = { Text(text = "Delete this item?") },
            text = {
                Text(
                    text = "\"${pendingDelete.name}\" will be removed from your archive. " +
                            "This can't be undone."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick(pendingDelete.id)
                        pendingDeleteId = null
                    }
                ) {
                    Text(text = "Delete", color = colors.error, fontWeight = FontWeight.SemiBold)
                }
            },
            dismissButton = {
                TextButton(onClick = { pendingDeleteId = null }) {
                    Text(text = "Cancel", color = colors.onSurfaceVariant)
                }
            }
        )
    }
}

// ---------------------------------------------------------------------------
// Header
// ---------------------------------------------------------------------------

@Composable
private fun ArchiveHeader(
    count: Int,
    onBackClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            onClick = onBackClick,
            shape = CircleShape,
            color = colors.surfaceVariant,
            modifier = Modifier.size(44.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = colors.onSurfaceVariant
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "My Archive",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground
            )
            Text(
                text = "Your saved outfits",
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onBackground.copy(alpha = 0.85f)
            )
        }

        if (count > 0) {
            Surface(
                shape = CircleShape,
                color = colors.surfaceVariant
            ) {
                Text(
                    text = count.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Search and filters
// ---------------------------------------------------------------------------

@Composable
private fun ArchiveSearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        placeholder = { Text(text = "Search your archive") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = colors.onSurfaceVariant,
            unfocusedTextColor = colors.onSurfaceVariant,
            focusedContainerColor = colors.surfaceVariant,
            unfocusedContainerColor = colors.surfaceVariant,
            cursorColor = colors.primary,
            focusedBorderColor = colors.primary,
            unfocusedBorderColor = colors.outlineVariant,
            focusedLeadingIconColor = colors.onSurfaceVariant,
            unfocusedLeadingIconColor = colors.onSurfaceVariant,
            focusedTrailingIconColor = colors.onSurfaceVariant,
            unfocusedTrailingIconColor = colors.onSurfaceVariant,
            focusedPlaceholderColor = colors.onSurfaceVariant.copy(alpha = 0.6f),
            unfocusedPlaceholderColor = colors.onSurfaceVariant.copy(alpha = 0.6f)
        )
    )
}

@Composable
private fun CategoryChips(
    categories: List<String>,
    selected: String?,
    onCategorySelected: (String?) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    val chipColors = FilterChipDefaults.filterChipColors(
        containerColor = colors.surfaceVariant,
        labelColor = colors.onSurfaceVariant,
        selectedContainerColor = colors.primary,
        selectedLabelColor = colors.onPrimary
    )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            FilterChip(
                selected = selected == null,
                onClick = { onCategorySelected(null) },
                label = { Text(text = "All") },
                shape = RoundedCornerShape(12.dp),
                colors = chipColors,
                border = null
            )
        }

        items(items = categories, key = { it }) { category ->
            FilterChip(
                selected = selected == category,
                onClick = { onCategorySelected(category) },
                label = { Text(text = category) },
                shape = RoundedCornerShape(12.dp),
                colors = chipColors,
                border = null
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Grid card
// ---------------------------------------------------------------------------

@Composable
private fun ArchiveCard(
    outfit: ArchivedOutfit,
    onDeleteClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = colors.surfaceVariant
    ) {
        Column {
            // Themed placeholder where an image will go once image loading is added.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(colors.tertiaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Checkroom,
                    contentDescription = null,
                    tint = colors.onTertiaryContainer,
                    modifier = Modifier.size(40.dp)
                )

                val category = outfit.category
                if (!category.isNullOrBlank()) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp),
                        shape = CircleShape,
                        color = colors.surfaceVariant
                    ) {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 6.dp, bottom = 6.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = outfit.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = outfit.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.onSurfaceVariant.copy(alpha = 0.8f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Outlined.DeleteOutline,
                        contentDescription = "Delete ${outfit.name}",
                        tint = colors.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Empty / no results / error states
// ---------------------------------------------------------------------------

@Composable
private fun ArchiveMessageState(
    icon: ImageVector,
    title: String,
    message: String,
    actionLabel: String,
    onActionClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = colors.surfaceVariant
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(colors.tertiaryContainer, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = colors.onTertiaryContainer,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onSurfaceVariant.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onActionClick,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary,
                        contentColor = colors.onPrimary
                    )
                ) {
                    Text(text = actionLabel, style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

@Composable
private fun ArchiveErrorState(
    message: String,
    onRetryClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = colors.errorContainer
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = null,
                    tint = colors.onErrorContainer,
                    modifier = Modifier.size(40.dp)
                )

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onErrorContainer,
                    textAlign = TextAlign.Center
                )

                OutlinedButton(
                    onClick = onRetryClick,
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = null,
                        tint = colors.onErrorContainer,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Try again", color = colors.onErrorContainer)
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Previews (sample values exist only here, never in app data)
// ---------------------------------------------------------------------------

@Preview(showBackground = true, heightDp = 800)
@Composable
private fun ArchiveScreenEmptyPreview() {
    Mystiq_FinalTheme {
        ArchiveScreen(
            uiState = ArchiveUIState(),
            onBackClick = {},
            onDeleteClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 800)
@Composable
private fun ArchiveScreenWithDataPreview() {
    Mystiq_FinalTheme {
        ArchiveScreen(
            uiState = ArchiveUIState(
                archivedOutfits = listOf(
                    ArchivedOutfit(1, "Linen set", "Today", "Casual"),
                    ArchivedOutfit(2, "Evening look", "Yesterday", "Formal"),
                    ArchivedOutfit(3, "Beach day", "Last week", "Casual"),
                    ArchivedOutfit(4, "Office", "Last month")
                )
            ),
            onBackClick = {},
            onDeleteClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 800)
@Composable
private fun ArchiveScreenNoResultsPreview() {
    Mystiq_FinalTheme {
        ArchiveScreen(
            uiState = ArchiveUIState(
                archivedOutfits = listOf(ArchivedOutfit(1, "Linen set", "Today")),
                searchQuery = "zzz"
            ),
            onBackClick = {},
            onDeleteClick = {}
        )
    }
}