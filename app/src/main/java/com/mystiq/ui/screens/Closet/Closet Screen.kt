package com.example.mystic.closet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// ---------- Data ----------

enum class ClosetFilter(val label: String) { All("All"), Shirts("Shirts"), Pants("Pants") }

data class ClosetItem(
    val id: Int,
    val category: ClosetFilter,   // Shirts or Pants
    val color: Color              // placeholder until you load real images
)

// ---------- 1. ROUTE (stateful: holds and manipulates data) ----------

@Composable
fun ClosetRoute(
    onBackClick: () -> Unit = {},
    onStatsClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onSaveOutfitClick: () -> Unit = {},   // cube icon – TODO confirm
    onSuggestClick: () -> Unit = {}       // sparkle icon – TODO confirm
) {
    // Swap these for a ViewModel + StateFlow when you connect real data
    var selectedFilter by rememberSaveable { mutableStateOf(ClosetFilter.All) }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val allItems = remember {
        listOf(
            ClosetItem(1, ClosetFilter.Shirts, Color(0xFFFF8A65)),
            ClosetItem(2, ClosetFilter.Pants, Color(0xFF263238)),
            ClosetItem(3, ClosetFilter.Shirts, Color(0xFFCE93D8))
        )
    }
    val visibleItems = allItems.filter {
        selectedFilter == ClosetFilter.All || it.category == selectedFilter
    }

    ClosetScreen(
        items = visibleItems,
        selectedFilter = selectedFilter,
        searchQuery = searchQuery,
        onFilterSelected = { selectedFilter = it },
        onSearchChange = { searchQuery = it },
        onBackClick = onBackClick,
        onStatsClick = onStatsClick,
        onAddClick = onAddClick,
        onSaveOutfitClick = onSaveOutfitClick,
        onSuggestClick = onSuggestClick,
        onItemClick = { /* TODO add item to outfit preview */ }
    )
}

// ---------- 2. SCREEN (stateless layout) ----------

@Composable
fun ClosetScreen(
    items: List<ClosetItem>,
    selectedFilter: ClosetFilter,
    searchQuery: String,
    onFilterSelected: (ClosetFilter) -> Unit,
    onSearchChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onStatsClick: () -> Unit,
    onAddClick: () -> Unit,
    onSaveOutfitClick: () -> Unit,
    onSuggestClick: () -> Unit,
    onItemClick: (ClosetItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top bar
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Spacer(Modifier.weight(1f))
            FilledIconButton(onClick = onStatsClick) {
                Icon(Icons.Default.BarChart, contentDescription = "Statistics")
            }
            Spacer(Modifier.width(8.dp))
            FilledIconButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add item")
            }
        }

        Spacer(Modifier.height(8.dp))

        // Outfit preview panel
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFEFEFEF),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Outfit Preview", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Row(
                    Modifier.fillMaxWidth().weight(1f),
                    verticalAlignment = Alignment.Top
                ) {
                    FilledIconButton(onClick = onSaveOutfitClick) {
                        Icon(Icons.Default.Inventory2, contentDescription = "Save outfit")
                    }
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(horizontal = 12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFD6E8FF))
                    )
                    FilledIconButton(onClick = onSuggestClick) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = "Suggest outfit")
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Category tabs
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ClosetFilter.entries.forEach { filter ->
                        FilterChip(
                            selected = filter == selectedFilter,
                            onClick = { onFilterSelected(filter) },
                            label = { Text(filter.label) }
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Search bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = { Text("Search") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                // Items grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(100.dp)
                ) {
                    items(items, key = { it.id }) { item ->
                        Box(
                            Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFD6E8FF))
                                .clickable { onItemClick(item) },
                            contentAlignment = Alignment.Center
                        ) {
                            // Replace with AsyncImage(...) from Coil later
                            Box(Modifier.size(32.dp).background(item.color, RoundedCornerShape(4.dp)))
                        }
                    }
                }
            }
        }
    }
}

// ---------- 3. PREVIEW ----------

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun ClosetScreenPreview() {
    MaterialTheme {
        ClosetScreen(
            items = listOf(
                ClosetItem(1, ClosetFilter.Shirts, Color(0xFFFF8A65)),
                ClosetItem(2, ClosetFilter.Pants, Color(0xFF263238)),
                ClosetItem(3, ClosetFilter.Shirts, Color(0xFFCE93D8))
            ),
            selectedFilter = ClosetFilter.All,
            searchQuery = "",
            onFilterSelected = {}, onSearchChange = {},
            onBackClick = {}, onStatsClick = {}, onAddClick = {},
            onSaveOutfitClick = {}, onSuggestClick = {}, onItemClick = {}
        )
    }
}