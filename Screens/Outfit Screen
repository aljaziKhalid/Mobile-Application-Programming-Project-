package com.example.mystic.vault

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// ---------- Data ----------

data class Outfit(
    val id: Int,
    val itemIds: List<Int>   // an outfit = several closet items saved together
)

// ---------- 1. ROUTE (stateful) ----------

@Composable
fun VaultRoute(onBackClick: () -> Unit = {}) {
    val outfits = remember {
        mutableStateListOf(
            Outfit(1, listOf(1, 2)),
            Outfit(2, listOf(3, 2)),
            Outfit(3, listOf(1, 3)),
            Outfit(4, listOf(2, 3))
        )
    }
    var selectedIds by rememberSaveable { mutableStateOf(setOf<Int>()) }

    VaultScreen(
        outfits = outfits,
        selectedIds = selectedIds,
        onBackClick = onBackClick,
        onOutfitClick = { outfit ->
            selectedIds = if (outfit.id in selectedIds) selectedIds - outfit.id
                          else selectedIds + outfit.id
        },
        onSelectAllClick = { selectedIds = outfits.map { it.id }.toSet() },
        onCloseClick = { selectedIds = emptySet() }   // X – TODO confirm (clear selection vs delete)
    )
}

// ---------- 2. SCREEN (stateless layout) ----------

@Composable
fun VaultScreen(
    outfits: List<Outfit>,
    selectedIds: Set<Int>,
    onBackClick: () -> Unit,
    onOutfitClick: (Outfit) -> Unit,
    onSelectAllClick: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

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
                Text("Your Outfits", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(outfits, key = { it.id }) { outfit ->
                        val selected = outfit.id in selectedIds
                        Box(
                            Modifier
                                .aspectRatio(0.65f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFD6E8FF))
                                .then(
                                    if (selected) Modifier.background(Color(0x553366FF)) else Modifier
                                )
                                .clickable { onOutfitClick(outfit) }
                        )
                    }
                }

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onSelectAllClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A8A8A)),
                        shape = RoundedCornerShape(10.dp)
                    ) { Text("Select All") }

                    Spacer(Modifier.weight(1f))

                    FilledIconButton(
                        onClick = onCloseClick,
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color(0xFF8A8A8A)
                        )
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
            }
        }
    }
}

// ---------- 3. PREVIEW ----------

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun VaultScreenPreview() {
    MaterialTheme {
        VaultScreen(
            outfits = listOf(
                Outfit(1, listOf(1, 2)), Outfit(2, listOf(3, 2)),
                Outfit(3, listOf(1, 3)), Outfit(4, listOf(2, 3))
            ),
            selectedIds = setOf(2),
            onBackClick = {}, onOutfitClick = {},
            onSelectAllClick = {}, onCloseClick = {}
        )
    }
}