package com.mystiq.ui.screens.closet

import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
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

// TODO: move these into ui/theme/Color.kt
private val ScreenBackground = Color(0xFFF0F0F0)
private val FieldBackground = Color(0xFFDADADA)
private val DarkButton = Color(0xFF8A8A8A)
private val LightBlue = Color(0xFFCFE0F3)
private val BlueText = Color(0xFF5B7A99)
private val SilhouetteColor = Color(0xFF4F5270)
private val MutedText = Color(0xFF6B6B6B)

@Composable
fun ClosetScreen(
    uiState: ClosetUiState,
    onSearchQueryChange: (String) -> Unit,
    onRetryClick: () -> Unit,
    onAddItemClick: () -> Unit,
    onStatsClick: () -> Unit,
    onWearTrackerClick: () -> Unit,
    onCategoriesClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        ClosetHeader(onStatsClick = onStatsClick, onAddClick = onAddItemClick)
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
                uiState.isLoading -> CircularProgressIndicator()
                uiState.errorMessage != null -> ErrorState(uiState.errorMessage, onRetryClick)
                uiState.items.isEmpty() -> EmptyClosetState(onScanClick = onAddItemClick)
                uiState.visibleItems.isEmpty() -> Text(
                    text = "No items match \"${uiState.searchQuery}\"",
                    color = MutedText
                )
                else -> ClothingGrid(items = uiState.visibleItems, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
private fun ClosetHeader(onStatsClick: () -> Unit, onAddClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
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
    FilledIconButton(
        onClick = onClick,
        modifier = Modifier.size(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = DarkButton,
            contentColor = Color.White
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
                focusedContainerColor = FieldBackground,
                unfocusedContainerColor = FieldBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        FilledTonalButton(
            onClick = onCategoriesClick,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = FieldBackground,
                contentColor = MutedText
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
    ) {
        // TODO: show the silhouette that matches the user's body shape (drawable)
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = null,
            tint = SilhouetteColor,
            modifier = Modifier
                .size(170.dp)
                .align(Alignment.Center)
        )
        Surface(
            onClick = onWearTrackerClick,
            shape = RoundedCornerShape(16.dp),
            color = LightBlue,
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.width(12.dp))
        HorizontalDivider(modifier = Modifier.weight(1f), thickness = 2.dp, color = MutedText)
    }
}

@Composable
private fun EmptyClosetState(onScanClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Checkroom,
            contentDescription = null,
            tint = MutedText,
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
            color = MutedText,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onScanClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LightBlue, contentColor = BlueText)
        ) {
            Icon(Icons.Outlined.CameraAlt, contentDescription = null)
            Spacer(Modifier.width(10.dp))
            Text("Scan your first item", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun ErrorState(message: String, onRetryClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(message, color = MaterialTheme.colorScheme.error)
        TextButton(onClick = onRetryClick) { Text("Try again") }
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
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            // TODO: load item.imageUri with Coil: AsyncImage(model = item.imageUri, ...)
            Icon(Icons.Outlined.Checkroom, contentDescription = null, tint = MutedText)
        }
        Column(Modifier.padding(horizontal = 8.dp, vertical = 6.dp)) {
            Text(item.name, style = MaterialTheme.typography.labelLarge, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(item.category, style = MaterialTheme.typography.labelSmall, color = MutedText)
        }
    }
}

// ---------- Previews: one per UI state ----------

@Composable
private fun PreviewScreen(state: ClosetUiState) {
    MaterialTheme {
        ClosetScreen(
            uiState = state,
            onSearchQueryChange = {}, onRetryClick = {}, onAddItemClick = {},
            onStatsClick = {}, onWearTrackerClick = {}, onCategoriesClick = {}, onItemClick = {}
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
