package com.mystiq.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val CardShape = RoundedCornerShape(20.dp)

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onEditProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onPreferencesClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onClosetClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Scaffold(
        containerColor = colors.background
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "My Profile",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                color = colors.onBackground
            )

            Spacer(modifier = Modifier.height(28.dp))

            ProfileHeader(
                username = uiState.username,
                email = uiState.email,
                onEditProfileClick = onEditProfileClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Featured card
            ClosetCard(
                subtitle = "${uiState.closetItemsCount} Items",
                onClick = onClosetClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Grouped options
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = CardShape,
                colors = CardDefaults.cardColors(
                    containerColor = colors.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                ProfileOptionRow(
                    title = "Body Shape",
                    subtitle = uiState.bodyShape,
                    onClick = onEditProfileClick
                )

                OptionDivider()

                ProfileOptionRow(
                    title = "Settings",
                    subtitle = "Manage your account",
                    onClick = onSettingsClick
                )

                OptionDivider()

                ProfileOptionRow(
                    title = "Preferences",
                    subtitle = "Customize your style",
                    onClick = onPreferencesClick
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedButton(
                onClick = onLogoutClick,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, colors.error.copy(alpha = 0.5f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colors.error
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Log Out",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun ProfileHeader(
    username: String,
    email: String,
    onEditProfileClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        // Avatar with a soft ring
        Box(
            modifier = Modifier
                .size(104.dp)
                .border(2.dp, colors.primary.copy(alpha = 0.25f), CircleShape)
                .padding(6.dp)
                .background(colors.primaryContainer, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = username.firstOrNull()?.uppercase() ?: "M",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = colors.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = username,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = colors.onBackground
        )

        Text(
            text = email,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(4.dp))

        TextButton(onClick = onEditProfileClick) {
            Text(
                text = "Edit Profile",
                style = MaterialTheme.typography.labelLarge,
                color = colors.primary
            )
        }
    }
}

@Composable
private fun ClosetCard(
    subtitle: String,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = CardShape,
        colors = CardDefaults.cardColors(
            containerColor = colors.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "My Closet",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onPrimaryContainer
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }

            Chevron(tint = colors.onPrimaryContainer)
        }
    }
}

@Composable
private fun ProfileOptionRow(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = colors.onSurface
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = colors.onSurfaceVariant
            )
        }

        Chevron(tint = colors.primary)
    }
}

@Composable
private fun OptionDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 20.dp),
        color = MaterialTheme.colorScheme.outlineVariant
    )
}

@Composable
private fun Chevron(tint: Color) {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = null,
        tint = tint
    )
}

