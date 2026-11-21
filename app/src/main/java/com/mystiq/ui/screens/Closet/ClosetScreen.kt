package com.mystiq.ui.screens.closet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mystiq.ui.components.MystiqBottomBar
import com.mystiq.ui.theme.Mystiq_FinalTheme

@Composable
fun ClosetScreen(
    uiState: ClosetUiState,
    onBackClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onRetryClick: () -> Unit,
    onAddItemClick: () -> Unit,
    onStatsClick: () -> Unit,
    onWearTrackerClick: () -> Unit,
    onCategoriesClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    onHomeClick: () -> Unit,
    onOutfitsClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            MystiqBottomBar(
                selectedTab = "closet",
                onHomeClick = onHomeClick,
                onClosetClick = {},                 // already on Closet
                onAddClick = onAddItemClick,        // Add tab -> camera
                onOutfitsClick = onOutfitsClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            ClosetHeader(onBackClick = onBackClick, onStatsClick = onStatsClick, onAddClick = onAddItemClick)
            Spacer(Modifier.height(12.dp))
            SearchRow(
                query = uiState.searchQuery,
                onQueryChange = onSearchQueryChange,
                onCategoriesClick = onCategoriesClick
            )
            BodyPreview(onWearTrackerClick = onWearTrackerClick)
            SectionTitle("My Clothing Items")

            // This Box switches between the 4 UI states
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                when {
                    uiState.isLoading -> CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
                    uiState.errorMessage != null -> ErrorState(uiState.errorMessage, onRetryClick)
                    uiState.items.isEmpty() -> EmptyClosetState(onScanClick = onAddItemClick)
                    uiState.visibleItems.isEmpty() -> Text(
                        text = "No items match \"${uiState.searchQuery}\"",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f)
                    )
                    else -> ClothingGrid(items = uiState.visibleItems, onItemClick = onItemClick)
                }
            }
        }
    }
}

@Composable
private fun ClosetHeader(onBackClick: () -> Unit, onStatsClick: () -> Unit, onAddClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(
            text = "Closet",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        SquareIconButton(Icons.Filled.BarChart, "Closet statistics", onStatsClick)
        Spacer(Modifier.width(8.dp))
        SquareIconButton(Icons.Filled.Add, "Add item", onAddClick)
    }
}

@Composable
private fun SquareIconButton(icon: ImageVector, contentDescription: String, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    FilledIconButton(
        onClick = onClick,
        modifier = Modifier.size(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = colors.primary,
            contentColor = colors.onPrimary
        )
    ) {
        Icon(icon, contentDescription = contentDescription, modifier = Modifier.size(30.dp))
    }
}

@Composable
private fun SearchRow(
    query: String,
    onQueryChange: (String) -> Unit,
    onCategoriesClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Search") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.surfaceVariant,
                unfocusedContainerColor = colors.surfaceVariant,
                focusedTextColor = colors.onSurfaceVariant,
                unfocusedTextColor = colors.onSurfaceVariant,
                focusedPlaceholderColor = colors.onSurfaceVariant.copy(alpha = 0.6f),
                unfocusedPlaceholderColor = colors.onSurfaceVariant.copy(alpha = 0.6f),
                focusedLeadingIconColor = colors.onSurfaceVariant,
                unfocusedLeadingIconColor = colors.onSurfaceVariant,
                cursorColor = colors.onSurfaceVariant,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        FilledTonalButton(
            onClick = onCategoriesClick,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = colors.surfaceVariant,
                contentColor = colors.onSurfaceVariant
            )
        ) {
            Icon(Icons.Filled.SwapVert, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(4.dp))
            Text("Categories")
        }
    }
}

@Composable
private fun BodyPreview(onWearTrackerClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
    ) {
        // TODO: show the silhouette that matches the user's body shape (drawable)
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = null,
            tint = colors.primary,
            modifier = Modifier
                .size(170.dp)
                .align(Alignment.Center)
        )
        Surface(
            onClick = onWearTrackerClick,
            shape = RoundedCornerShape(16.dp),
            color = colors.tertiaryContainer,
            contentColor = colors.onTertiaryContainer,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Outlined.EventAvailable, contentDescription = null)
                Text("Wear\nTracker", textAlign = TextAlign.Center, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.width(12.dp))
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 2.dp,
            color = colors.onBackground.copy(alpha = 0.5f)
        )
    }
}

@Composable
private fun EmptyClosetState(onScanClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Checkroom,
            contentDescription = null,
            tint = colors.onBackground.copy(alpha = 0.85f),
            modifier = Modifier.size(96.dp)
        )
        Text(
            text = "Your closet is empty",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = "Start building your digital wardrobe\nby adding your first clothing item",
            style = MaterialTheme.typography.bodyMedium,
            color = colors.onBackground.copy(alpha = 0.85f),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onScanClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colors.primary, contentColor = colors.onPrimary)
        ) {
            Icon(Icons.Outlined.CameraAlt, contentDescription = null)
            Spacer(Modifier.width(10.dp))
            Text("Scan your first item", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun ErrorState(message: String, onRetryClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = colors.errorContainer,
        contentColor = colors.onErrorContainer
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(message, color = colors.onErrorContainer)
            TextButton(onClick = onRetryClick) {
                Text("Try again", color = colors.onErrorContainer)
            }
        }
    }
}

@Composable
private fun ClothingGrid(items: List<ClosetItemUi>, onItemClick: (Long) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items, key = { it.id }) { item ->
            ClothingItemCard(item = item, onClick = { onItemClick(item.id) })
        }
    }
}

@Composable
private fun ClothingItemCard(item: ClosetItemUi, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.surfaceVariant,
            contentColor = colors.onSurfaceVariant
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            // TODO: load item.imageUri with Coil: AsyncImage(model = item.imageUri, ...)
            Icon(
                Icons.Outlined.Checkroom,
                contentDescription = null,
                tint = colors.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
        Column(Modifier.padding(horizontal = 8.dp, vertical = 6.dp)) {
            Text(item.name, style = MaterialTheme.typography.labelLarge, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(
                item.category,
                style = MaterialTheme.typography.labelSmall,
                color = colors.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}

// ---------- Previews: one per UI state ----------

@Composable
private fun PreviewScreen(state: ClosetUiState) {
    Mystiq_FinalTheme {
        ClosetScreen(
            uiState = state,
            onBackClick = {},
            onSearchQueryChange = {}, onRetryClick = {}, onAddItemClick = {},
            onStatsClick = {}, onWearTrackerClick = {}, onCategoriesClick = {}, onItemClick = {},
            onHomeClick = {}, onOutfitsClick = {}, onProfileClick = {}
        )
    }
}

@Preview(name = "Empty", showBackground = true, heightDp = 800)
@Composable
private fun ClosetEmptyPreview() = PreviewScreen(ClosetUiState(isLoading = false))

@Preview(name = "Loading", showBackground = true, heightDp = 800)
@Composable
private fun ClosetLoadingPreview() = PreviewScreen(ClosetUiState(isLoading = true))

@Preview(name = "Error", showBackground = true, heightDp = 800)
@Composable
private fun ClosetErrorPreview() =
    PreviewScreen(ClosetUiState(isLoading = false, errorMessage = "Your closet couldn't be loaded."))

@Preview(name = "Content", showBackground = true, heightDp = 800)
@Composable
private fun ClosetContentPreview() = PreviewScreen(
    ClosetUiState(
        isLoading = false,
        items = listOf(
            ClosetItemUi(1, "White shirt", "Tops"),
            ClosetItemUi(2, "Blue jeans", "Bottoms"),
            ClosetItemUi(3, "Black boots", "Shoes")
        )
    )
)
