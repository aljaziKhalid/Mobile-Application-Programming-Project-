
package com.mystiq.ui.screens.Spread

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val MystiqCream = Color(0xFFFAF6F0)
private val MystiqPink = Color(0xFFEEDCE7)
private val MystiqPlum = Color(0xFF3B2938)
private val MystiqRose = Color(0xFF956B7D)
private val MystiqBorder = Color(0xFFE8DADD)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpreadScreenContent(
    state: SpreadUiState,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onWearTrackerClick: () -> Unit
) {
    Scaffold(
        containerColor = MystiqCream,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Closet spread",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MystiqPlum,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Build a balanced look",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MystiqRose
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MystiqPlum
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onHomeClick) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            tint = MystiqPlum
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MystiqCream
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {

            // Mannequin area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White)
                    .border(
                        1.dp,
                        MystiqBorder,
                        RoundedCornerShape(30.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "YOUR STYLE CANVAS",
                        fontSize = 12.sp,
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.Bold,
                        color = MystiqRose
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "👗",
                        fontSize = 110.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Your outfit starts here",
                        style = MaterialTheme.typography.titleLarge,
                        color = MystiqPlum,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Explore your wardrobe and create a look",
                        style = MaterialTheme.typography.bodySmall,
                        color = MystiqRose,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Closet statistics
            Text(
                text = "Your closet overview",
                style = MaterialTheme.typography.headlineSmall,
                color = MystiqPlum,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SpreadCategoryCard(
                    title = "Shoes",
                    percentage = state.shoesPercentage,
                    modifier = Modifier.weight(1f)
                )

                SpreadCategoryCard(
                    title = "Shirts",
                    percentage = state.shirtsPercentage,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SpreadCategoryCard(
                    title = "Jeans",
                    percentage = state.jeansPercentage,
                    modifier = Modifier.weight(1f)
                )

                SpreadCategoryCard(
                    title = "Hats",
                    percentage = state.hatsPercentage,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Dominant color card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(MystiqPlum)
                    .padding(22.dp)
            ) {
                Text(
                    text = "YOUR DOMINANT COLOR",
                    color = MystiqPink,
                    fontSize = 11.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                when (state.dominantColorName.lowercase()) {
                                    "blue" -> Color(0xFF6685A5)
                                    "pink" -> Color(0xFFE8B9C9)
                                    "black" -> Color.Black
                                    "white" -> Color.White
                                    "beige" -> Color(0xFFD9C4A5)
                                    else -> MystiqPink
                                }
                            )
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = state.dominantColorName,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "${state.dominantColorPercentage}% of your wardrobe",
                            color = MystiqPink,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Wear tracker button
            Button(
                onClick = onWearTrackerClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MystiqRose
                )
            ) {
                Text(
                    text = "Explore Wear Tracker",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun SpreadCategoryCard(
    title: String,
    percentage: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(22.dp))
            .background(Color.White)
            .border(
                1.dp,
                MystiqBorder,
                RoundedCornerShape(22.dp)
            )
            .padding(vertical = 20.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = MystiqRose,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "$percentage%",
            color = MystiqPlum,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        LinearProgressIndicator(
            progress = { (percentage / 100f).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(5.dp)
                .clip(RoundedCornerShape(10.dp)),
            color = MystiqRose,
            trackColor = MystiqPink
        )
    }
}
