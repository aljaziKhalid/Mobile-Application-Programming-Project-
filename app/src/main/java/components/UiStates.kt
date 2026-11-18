package com.mystiq.ui.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/*
 * Reusable UI states for every Mystiq screen.
 * Any screen (Closet, Outfits, Wishlist, Wear Tracker...) can show these
 * instead of writing its own loading / empty / error layout.
 *
 * Example inside a screen:
 *   when {
 *       uiState.isLoading -> LoadingState()
 *       uiState.errorMessage != null -> ErrorState(uiState.errorMessage, onRetry = viewModel::load)
 *       uiState.items.isEmpty() -> ClosetEmptyState(onScanClick = onAddItemClick)
 *       else -> /* the screen's normal content */
 *   }
 */

// TODO: move these into ui/theme/Color.kt
private val LightBlue = Color(0xFFCFE0F3)
private val BlueText = Color(0xFF5B7A99)
private val MutedText = Color(0xFF6B6B6B)

/** Loading: a spinner in the middle of the available space. */
@Composable
fun LoadingState(
    modifier: Modifier = Modifier,
    message: String? = null
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        if (message != null) {
            Spacer(Modifier.height(12.dp))
            Text(message, color = MutedText)
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
            tint = MutedText,
            modifier = Modifier.size(96.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MutedText,
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
                colors = ButtonDefaults.buttonColors(containerColor = LightBlue, contentColor = BlueText)
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
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(56.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(message, textAlign = TextAlign.Center)
        Spacer(Modifier.height(12.dp))
        OutlinedButton(onClick = onRetryClick) { Text("Try again") }
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

// ---------- Previews ----------

@Preview(name = "Loading", showBackground = true, heightDp = 400)
@Composable
private fun LoadingStatePreview() {
    MaterialTheme { LoadingState(message = "Loading your closet...") }
}

@Preview(name = "Empty closet", showBackground = true, heightDp = 400)
@Composable
private fun ClosetEmptyStatePreview() {
    MaterialTheme { ClosetEmptyState(onScanClick = {}) }
}

@Preview(name = "Empty wishlist", showBackground = true, heightDp = 400)
@Composable
private fun WishlistEmptyStatePreview() {
    MaterialTheme { WishlistEmptyState() }
}

@Preview(name = "Error", showBackground = true, heightDp = 400)
@Composable
private fun ErrorStatePreview() {
    MaterialTheme { ErrorState(message = "Your closet couldn't be loaded.", onRetryClick = {}) }
}
