package com.mystiq.ui.screens.WareTracker


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

private val TrackerBackground = Color(0xFFF7F7F7)
private val TrackerCard = Color(0xFFDCDADA)
private val TrackerGray = Color(0xFF858585)
private val TrackerDark = Color(0xFF333333)
private val TrackerAlert = Color(0xFF8B4513)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WearTrackerScreen(
    state: WearTrackerUiState,
    onBackClick: () -> Unit
) {
    var showUnderusedOnly by remember {
        mutableStateOf(false)
    }

    var ascending by remember {
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
        containerColor = TrackerBackground,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Wear Tracker",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TrackerGray
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TrackerBackground
                )
            )
        },

        bottomBar = {
            TrackerBottomBar()
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {

            // Heading and filter
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
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        onClick = {
                            ascending = !ascending
                        },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = if (ascending) "↑" else "↓",
                            fontSize = 23.sp,
                            color = Color.Black
                        )
                    }
                }

                Button(
                    onClick = {
                        showUnderusedOnly = !showUnderusedOnly
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (showUnderusedOnly) {
                            TrackerDark
                        } else {
                            TrackerGray
                        }
                    )
                ) {
                    Text(
                        text = if (showUnderusedOnly) {
                            "Show All"
                        } else {
                            "Filter"
                        },
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Table headings
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Item",
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Text(
                    text = "Wear Times",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Dataset items or empty state
            if (displayedItems.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No clothing data available yet",
                        color = TrackerGray,
                        fontSize = 14.sp
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

            // Rediscover your closet
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(TrackerGray)
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Rediscover Your Closet",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Style your underused items in a new outfit",
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Style underused outfits 🧥",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun WearItemCard(item: WearItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(TrackerCard)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(62.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                if (item.image.isNotBlank()) {
                    Text(
                        text = item.image,
                        fontSize = 35.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = item.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Last worn: ${item.lastWorn}",
                    fontSize = 11.sp,
                    color = Color.Black
                )

                if (item.isAlert) {
                    Spacer(modifier = Modifier.height(5.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(TrackerAlert)
                            .padding(
                                horizontal = 7.dp,
                                vertical = 3.dp
                            )
                    ) {
                        Text(
                            text = "Not worn in 30+ days",
                            color = Color.White,
                            fontSize = 9.sp
                        )
                    }
                }
            }
        }

        Text(
            text = item.wearTimes.toString(),
            fontSize = 21.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun TrackerBottomBar() {
    NavigationBar(
        containerColor = Color(0xFFE0DEDE),
        contentColor = Color.DarkGray
    ) {
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Text("▤", fontSize = 23.sp) },
            label = { Text("Closet") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Text("♧", fontSize = 23.sp) },
            label = { Text("Outfits") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Text("＋", fontSize = 23.sp) },
            label = { Text("Add") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Profile"
                )
            },
            label = { Text("Profile") }
        )
    }
}
