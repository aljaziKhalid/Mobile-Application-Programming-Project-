package com.mystiq.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mystiq.ui.theme.MystiqBackground
import com.mystiq.ui.theme.Mystiq_FinalTheme

/*
 * Reusable UI states for every Mystiq screen.
 * They draw on top of the screen's background, so text and icons use onBackground.
 *
 * Example inside a screen:
 *   when {
 *       uiState.isLoading -> LoadingState()
 *       uiState.errorMessage != null -> ErrorState(uiState.errorMessage, onRetryClick = viewModel::load)
 *       uiState.items.isEmpty() -> ClosetEmptyState(onScanClick = onAddItemClick)
 *       else -> /* the screen's normal content */
 *   }
 */

/** Loading: a spinner in the middle of the available space. */
@Composable
fun LoadingState(
    modifier: Modifier = Modifier,
    message: String? = null
) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = colors.onBackground)
        if (message != null) {
            Spacer(Modifier.height(12.dp))
            Text(message, color = colors.onBackground, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

/**
 * Empty: icon + title + message, with an optional action button.
 * Pass actionLabel = null to hide the button.
 */
@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    actionIcon: ImageVector? = null,
    onActionClick: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colors.onBackground,
            modifier = Modifier.size(96.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colors.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.onBackground.copy(alpha = 0.85f),
            textAlign = TextAlign.Center
        )
        if (actionLabel != null) {
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onActionClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                )
            ) {
                if (actionIcon != null) {
                    Icon(actionIcon, contentDescription = null)
                    Spacer(Modifier.width(10.dp))
                }
                Text(actionLabel, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

/** Error: what went wrong + a "Try again" button. */
@Composable
fun ErrorState(
    message: String,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.ErrorOutline,
            contentDescription = null,
            tint = colors.errorContainer, // light error tone, readable on the mauve background
            modifier = Modifier.size(56.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(message, color = colors.onBackground, textAlign = TextAlign.Center)
        Spacer(Modifier.height(12.dp))
        OutlinedButton(
            onClick = onRetryClick,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = colors.onBackground)
        ) { Text("Try again") }
    }
}

// ---------- Ready-made empty states that match the wireframes ----------

/** The empty closet from the "ui states" wireframe. */
@Composable
fun ClosetEmptyState(onScanClick: () -> Unit, modifier: Modifier = Modifier) {
    EmptyState(
        icon = Icons.Outlined.Checkroom,
        title = "Your closet is empty",
        message = "Start building your digital wardrobe\nby adding your first clothing item",
        actionLabel = "Scan your first item",
        actionIcon = Icons.Outlined.CameraAlt,
        onActionClick = onScanClick,
        modifier = modifier
    )
}

/** Empty wishlist. */
@Composable
fun WishlistEmptyState(modifier: Modifier = Modifier) {
    EmptyState(
        icon = Icons.Outlined.FavoriteBorder,
        title = "Your wishlist is empty",
        message = "Save pieces you want to buy so you can find them later",
        modifier = modifier
    )
}

// ---------- Previews (on the app background so colors look real) ----------

@Composable
private fun PreviewBox(content: @Composable () -> Unit) {
    Mystiq_FinalTheme {
        Column(Modifier.background(MystiqBackground)) { content() }
    }
}

@Preview(name = "Loading", heightDp = 400)
@Composable
private fun LoadingStatePreview() = PreviewBox { LoadingState(message = "Loading your closet...") }

@Preview(name = "Empty closet", heightDp = 400)
@Composable
private fun ClosetEmptyStatePreview() = PreviewBox { ClosetEmptyState(onScanClick = {}) }

@Preview(name = "Empty wishlist", heightDp = 400)
@Composable
private fun WishlistEmptyStatePreview() = PreviewBox { WishlistEmptyState() }

@Preview(name = "Error", heightDp = 400)
@Composable
private fun ErrorStatePreview() =
    PreviewBox { ErrorState(message = "Your closet couldn't be loaded.", onRetryClick = {}) }