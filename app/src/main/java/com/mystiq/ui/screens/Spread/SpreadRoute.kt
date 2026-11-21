
package com.mystiq.ui.screens.Spread

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SpreadRoute(
    viewModel: SpreadViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateWearTracker: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    SpreadScreen(
        state = state,
        onBackClick = onNavigateBack,
        onHomeClick = onNavigateHome,
        onWearTrackerClick = onNavigateWearTracker
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpreadScreen(
    state: SpreadUiState,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onWearTrackerClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onHomeClick) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    }

                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            // Connect the shared BottomBar here if needed.
        },
        containerColor = Color(0xFFF7F7F7)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Your Closet Spread",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SpreadCategoryCard(
                    title = "Shoes",
                    percentage = "${state.shoesPercentage}%",
                    modifier = Modifier.weight(1f)
                )

                SpreadCategoryCard(
                    title = "Shirts",
                    percentage = "${state.shirtsPercentage}%",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SpreadCategoryCard(
                    title = "Jeans",
                    percentage = "${state.jeansPercentage}%",
                    modifier = Modifier.weight(1f)
                )

                SpreadCategoryCard(
                    title = "Hats",
                    percentage = "${state.hatsPercentage}%",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFEAEAEA)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${state.dominantColorPercentage}%",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = state.dominantColorName,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onWearTrackerClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray
                )
            ) {
                Text(
                    text = "Wear Tracker",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun SpreadCategoryCard(
    title: String,
    percentage: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(130.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        Color.LightGray,
                        RoundedCornerShape(8.dp)
                    )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = percentage,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Text(
                text = "View",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
