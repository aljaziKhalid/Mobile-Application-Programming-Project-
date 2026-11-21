package com.example.mystic.outfit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mystiq.ui.theme.Mystiq_FinalTheme

// Stateless layout: renders OutfitUiState and reports events upward.
@Composable
fun OutfitScreen(
    uiState: OutfitUiState,
    onBackClick: () -> Unit,
    onOutfitClick: (Outfit) -> Unit,
    onSelectAllClick: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    // Screen background = Mystiq background (DustyMauve), content color = onBackground (SoftPink)
    Surface(
        modifier = modifier.fillMaxSize(),
        color = colors.background
    ) {
        Column(Modifier.padding(16.dp)) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }

            // Panel (Cream, with DeepPlum content)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = colors.surfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column(
                    Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Your Outfits", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(12.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(uiState.outfits, key = { it.id }) { outfit ->
                            val selected = outfit.id in uiState.selectedIds
                            Box(
                                Modifier
                                    .aspectRatio(0.65f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(colors.tertiaryContainer)
                                    .then(
                                        if (selected) Modifier.background(colors.primary.copy(alpha = 0.35f))
                                        else Modifier
                                    )
                                    .clickable { onOutfitClick(outfit) }
                            )
                        }
                    }

                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Uses the theme's default button colors (primary / onPrimary)
                        Button(
                            onClick = onSelectAllClick,
                            shape = RoundedCornerShape(10.dp)
                        ) { Text("Select All") }

                        Spacer(Modifier.weight(1f))

                        FilledIconButton(onClick = onCloseClick) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun OutfitScreenPreview() {
    Mystiq_FinalTheme {
        OutfitScreen(
            uiState = OutfitUiState(
                outfits = listOf(
                    Outfit(1, listOf(1, 2)), Outfit(2, listOf(3, 2)),
                    Outfit(3, listOf(1, 3)), Outfit(4, listOf(2, 3))
                ),
                selectedIds = setOf(2)
            ),
            onBackClick = {}, onOutfitClick = {},
            onSelectAllClick = {}, onCloseClick = {}
        )
    }
}