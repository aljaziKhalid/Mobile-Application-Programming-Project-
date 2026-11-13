package com.mystiq.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private val ScreenBackground = Color(0xFFF0F0F0)
private val LightBlue = Color(0xFFCFE0F3)
private val BlueText = Color(0xFF5B7A99)
private val MutedText = Color(0xFF6B6B6B)

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onClosetClick: () -> Unit,
    onVaultClick: () -> Unit,
    onArchiveClick: () -> Unit,
    onCameraClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            HomeHeader(onProfileClick)
        }

        item {
            Text(
                text = "Welcome to Mystiq",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = BlueText
            )
        }

        item {
            Text(
                text = "Your Digital Wardrobe",
                style = MaterialTheme.typography.titleMedium,
                color = MutedText
            )
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    HomeButton("Closet", onClosetClick)
                }
                Box(modifier = Modifier.weight(1f)) {
                    HomeButton("Vault", onVaultClick)
                }
            }
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    HomeButton("Archive", onArchiveClick)
                }
                Box(modifier = Modifier.weight(1f)) {
                    HomeButton("Camera", onCameraClick)
                }
            }
        }

        item {
            Text(
                text = "Recent Fits",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = BlueText
            )
        }

        item {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                uiState.recentOutfits.isEmpty() -> {
                    EmptyRecentFits()
                }

                else -> {
                    Column(
                        verticalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {
                        uiState.recentOutfits.forEach { outfit ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.White,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = outfit,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeHeader(
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Mystiq",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = BlueText
        )

        TextButton(onClick = onProfileClick) {
            Text("Profile", color = BlueText)
        }
    }
}

@Composable
private fun HomeButton(
    title: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = LightBlue
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = BlueText,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun EmptyRecentFits() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "No recent outfits yet",
                color = MutedText,
                textAlign = TextAlign.Center
            )
        }
    }
}