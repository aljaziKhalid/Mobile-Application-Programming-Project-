
package com.mystiq.ui.screens.WareTracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mystiq.ui.components.MystiqBottomBar

private val CardShape = RoundedCornerShape(20.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WearTrackerScreen(
    state: WearTrackerUiState,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit = {},
    onClosetClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onOutfitsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme

    var showUnderusedOnly by rememberSaveable {
        mutableStateOf(false)
    }

    var ascending by rememberSaveable {
        mutableStateOf(false)
    }

    val displayedItems = remember(
        state.items,
        showUnderusedOnly,
        ascending
    ) {
        val filtered = if (showUnderusedOnly) {
            state.items.filter { it.isAlert }
        } else {
            state.items
        }

        if (ascending) {
            filtered.sortedBy { it.wearTimes }
        } else {
            filtered.sortedByDescending { it.wearTimes }
        }
    }

    Scaffold(
        containerColor = colors.background,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Wear Tracker",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = colors.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = colors.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.background
                )
            )
        },

        bottomBar = {
            MystiqBottomBar(
                selectedTab = "closet",
                onHomeClick = onHomeClick,
                onClosetClick = onClosetClick,
                onAddClick = onAddClick,
                onOutfitsClick = onOutfitsClick,
                onProfileClick = onProfileClick
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {

            // Heading, sort toggle and filter
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Clothing history",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.onBackground
                    )

                    IconButton(
                        onClick = {
                            ascending = !ascending
                        }
                    ) {
                        Icon(
                            imageVector = if (ascending) {
                                Icons.Filled.ArrowUpward
                            } else {
                                Icons.Filled.ArrowDownward
                            },
                            contentDescription = if (ascending) {
                                "Sorted: least worn first"
                            } else {
                                "Sorted: most worn first"
                            },
                            tint = colors.primary
                        )
                    }
                }

                FilterChip(
                    selected = showUnderusedOnly,
                    onClick = {
                        showUnderusedOnly = !showUnderusedOnly
                    },
                    label = {
                        Text("Underused only")
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = colors.primaryContainer,
                        selectedLabelColor = colors.onPrimaryContainer
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Table headings
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Item",
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.onSurfaceVariant
                )

                Text(
                    text = "Wear Times",
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Items or empty state
            if (displayedItems.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No clothing data available yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }

            } else {

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = displayedItems,
                        key = { it.name }
                    ) { item ->
                        WearItemCard(item = item)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            RediscoverCard()
        }
    }
}

@Composable
private fun RediscoverCard() {
    val colors = MaterialTheme.colorScheme

    Surface(
        shape = CardShape,
        color = colors.primaryContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Rediscover Your Closet",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = colors.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Style your underused items in a new outfit",
                style = MaterialTheme.typography.bodySmall,
                color = colors.onPrimaryContainer.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Style underused outfits 🧥",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = colors.primary
            )
        }
    }
}

@Composable
fun WearItemCard(item: WearItem) {
    val colors = MaterialTheme.colorScheme

    Surface(
        shape = CardShape,
        color = colors.surfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Thumbnail
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = colors.surface,
                modifier = Modifier.size(64.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    if (item.image.isNotBlank()) {
                        Text(
                            text = item.image,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onSurface
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Last worn: ${item.lastWorn}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.onSurfaceVariant
                )

                if (item.isAlert) {
                    Spacer(modifier = Modifier.height(6.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = colors.tertiaryContainer
                    ) {
                        Text(
                            text = "Not worn in 30+ days",
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.onTertiaryContainer,
                            modifier = Modifier.padding(
                                horizontal = 8.dp,
                                vertical = 3.dp
                            )
                        )
                    }
                }
            }

            Text(
                text = item.wearTimes.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = if (item.isAlert) {
                    colors.tertiary
                } else {
                    colors.onSurface
                },
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}
