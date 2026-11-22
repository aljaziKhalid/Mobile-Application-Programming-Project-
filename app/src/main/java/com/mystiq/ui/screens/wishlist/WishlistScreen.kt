package com.mystiq.ui.screens.wishlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mystiq.ui.components.LoadingState
import com.mystiq.ui.components.WishlistEmptyState
import com.mystiq.ui.theme.MystiqBackground
import com.mystiq.ui.theme.MystiqField
import com.mystiq.ui.theme.MystiqText
import com.mystiq.ui.theme.Mystiq_FinalTheme

@Composable
fun WishlistScreen(
    uiState: WishlistUiState,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    onRemoveClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MystiqBackground)
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = colors.onBackground
                )
            }
            Text(
                text = "My Wishlist",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = colors.onBackground,
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(Modifier.height(8.dp))
        Text(
            "Saved Wishes",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colors.onBackground
        )
        Text(
            text = if (uiState.items.size == 1) "1 item" else "${uiState.items.size} items",
            style = MaterialTheme.typography.bodyMedium,
            color = colors.onBackground.copy(alpha = 0.8f)
        )
        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> LoadingState()
                uiState.items.isEmpty() -> WishlistEmptyState()
                else -> LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.items, key = { it.id }) { item ->
                        WishlistCard(
                            item = item,
                            onClick = { onItemClick(item.id) },
                            onRemoveClick = { onRemoveClick(item.id) }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        Button(
            onClick = onAddClick,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.primary,
                contentColor = colors.onPrimary
            )
        ) {
            Text("Add to wishlist", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Filled.Add, contentDescription = null)
        }
    }
}

@Composable
private fun WishlistCard(
    item: WishlistItemUi,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    Column(modifier = Modifier.clickable(onClick = onClick)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.9f)
                .clip(RoundedCornerShape(16.dp))
                .background(MystiqField),
            contentAlignment = Alignment.Center
        ) {
            // TODO: load item.imageUri with Coil: AsyncImage(model = item.imageUri, ...)
            Icon(
                imageVector = Icons.Outlined.Checkroom,
                contentDescription = null,
                tint = MystiqText,
                modifier = Modifier.size(56.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    item.category,
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.onBackground.copy(alpha = 0.8f)
                )
            }
            IconButton(onClick = onRemoveClick) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Remove ${item.name} from wishlist",
                    tint = colors.onBackground
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 800)
@Composable
private fun WishlistScreenPreview() {
    Mystiq_FinalTheme {
        WishlistScreen(
            uiState = WishlistUiState(
                isLoading = false,
                items = listOf(
                    WishlistItemUi(1, "Sunglasses", "Accessories"),
                    WishlistItemUi(2, "Navy Coat", "Outerwear"),
                    WishlistItemUi(3, "White high heels", "Shoes"),
                    WishlistItemUi(4, "Vintage tote bag", "Bags")
                )
            ),
            onBackClick = {}, onAddClick = {}, onItemClick = {}, onRemoveClick = {}
        )
    }
}

@Preview(name = "Empty", showBackground = true, heightDp = 800)
@Composable
private fun WishlistEmptyPreview() {
    Mystiq_FinalTheme {
        WishlistScreen(
            uiState = WishlistUiState(isLoading = false),
            onBackClick = {}, onAddClick = {}, onItemClick = {}, onRemoveClick = {}
        )
    }
}
