package com.mystiq.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mystiq.ui.components.MystiqBottomBar
import com.mystiq.ui.theme.Mystiq_FinalTheme
import java.util.Calendar

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onClosetClick: () -> Unit,
    onVaultClick: () -> Unit,
    onArchiveClick: () -> Unit,
    onCameraClick: () -> Unit,
    onProfileClick: () -> Unit,
    onHomeClick: () -> Unit = {},
    onAddClick: () -> Unit = onCameraClick,
    onOutfitsClick: () -> Unit = {},
    onRetryClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Scaffold(
        modifier = modifier,
        containerColor = colors.background,
        bottomBar = {
            MystiqBottomBar(
                selectedTab = "home",
                onHomeClick = onHomeClick,
                onClosetClick = onClosetClick,
                onAddClick = onAddClick,
                onOutfitsClick = onOutfitsClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            item {
                HomeHeader(onProfileClick = onProfileClick)
            }

            item {
                HeroCard(onCameraClick = onCameraClick)
            }

            item {
                Section(title = "Wardrobe summary") {
                    SummaryRow(uiState = uiState)
                }
            }

            item {
                Section(title = "Quick actions") {
                    QuickActions(
                        onClosetClick = onClosetClick,
                        onVaultClick = onVaultClick,
                        onArchiveClick = onArchiveClick,
                        onCameraClick = onCameraClick
                    )
                }
            }

            item {
                Section(title = "Recent fits") {
                    RecentFitsContent(
                        uiState = uiState,
                        onCameraClick = onCameraClick,
                        onRetryClick = onRetryClick
                    )
                }
            }

            item {
                Section(title = "Recently added") {
                    RecentlyAddedContent(uiState = uiState)
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Header
// ---------------------------------------------------------------------------

private fun greetingForNow(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 5..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        in 17..21 -> "Good evening"
        else -> "Welcome back"
    }
}

@Composable
private fun HomeHeader(onProfileClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val greeting = remember { greetingForNow() }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = greeting,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onBackground.copy(alpha = 0.85f)
            )
            Text(
                text = "Mystiq",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground
            )
        }

        Surface(
            onClick = onProfileClick,
            shape = CircleShape,
            color = colors.surfaceVariant,
            modifier = Modifier.size(46.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Profile",
                    tint = colors.onSurfaceVariant
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Hero card
// ---------------------------------------------------------------------------

@Composable
private fun HeroCard(onCameraClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = colors.primary
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {

            // Decorative background icon
            Icon(
                imageVector = Icons.Outlined.Checkroom,
                contentDescription = null,
                tint = colors.onPrimary,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 20.dp, y = (-6).dp)
                    .size(130.dp)
                    .alpha(0.12f)
            )

            Column(modifier = Modifier.padding(22.dp)) {
                Text(
                    text = "Your Digital Wardrobe",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colors.onPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Scan, organise and style everything you own.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onPrimary.copy(alpha = 0.85f)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = onCameraClick,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.onPrimary,
                        contentColor = colors.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CameraAlt,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Scan a new piece",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Section wrapper
// ---------------------------------------------------------------------------

@Composable
private fun Section(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        content()
    }
}

// ---------------------------------------------------------------------------
// Shared small pieces
// ---------------------------------------------------------------------------

@Composable
private fun IconBadge(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .size(size)
            .background(colors.tertiaryContainer, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colors.onTertiaryContainer,
            modifier = Modifier.size(size * 0.5f)
        )
    }
}

// ---------------------------------------------------------------------------
// Wardrobe summary
// ---------------------------------------------------------------------------

@Composable
private fun SummaryRow(uiState: HomeUiState) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        SummaryCard(
            icon = Icons.Outlined.Checkroom,
            value = uiState.clothingCount,
            label = "Items",
            modifier = Modifier.weight(1f)
        )
        SummaryCard(
            icon = Icons.Outlined.AutoAwesome,
            value = uiState.outfitCount,
            label = "Outfits",
            modifier = Modifier.weight(1f)
        )
        SummaryCard(
            icon = Icons.Outlined.Archive,
            value = uiState.archivedCount,
            label = "Archived",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SummaryCard(
    icon: ImageVector,
    value: Int?,
    label: String,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = colors.surfaceVariant
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IconBadge(icon = icon, size = 36.dp)

            Column {
                Text(
                    text = value?.toString() ?: "–",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colors.onSurfaceVariant
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.onSurfaceVariant.copy(alpha = 0.8f)
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Quick actions
// ---------------------------------------------------------------------------

@Composable
private fun QuickActions(
    onClosetClick: () -> Unit,
    onVaultClick: () -> Unit,
    onArchiveClick: () -> Unit,
    onCameraClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickActionCard(
                icon = Icons.Outlined.Checkroom,
                label = "Closet",
                onClick = onClosetClick,
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                icon = Icons.Outlined.Lock,
                label = "Vault",
                onClick = onVaultClick,
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickActionCard(
                icon = Icons.Outlined.Inventory2,
                label = "Archive",
                onClick = onArchiveClick,
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                icon = Icons.Outlined.CameraAlt,
                label = "Camera",
                onClick = onCameraClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun QuickActionCard(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = colors.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconBadge(icon = icon, size = 40.dp)

            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = colors.onSurfaceVariant
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Recent fits
// ---------------------------------------------------------------------------

@Composable
private fun RecentFitsContent(
    uiState: HomeUiState,
    onCameraClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    val error = uiState.errorMessage

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
            }
        }

        error != null -> {
            ErrorCard(message = error, onRetryClick = onRetryClick)
        }

        uiState.recentOutfits.isEmpty() -> {
            EmptyRecentFits(onCameraClick = onCameraClick)
        }

        else -> {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(uiState.recentOutfits) { outfit ->
                    PreviewCard(
                        icon = Icons.Outlined.AutoAwesome,
                        title = outfit,
                        subtitle = null,
                        width = 150.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorCard(
    message: String,
    onRetryClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = colors.errorContainer
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ErrorOutline,
                contentDescription = null,
                tint = colors.onErrorContainer,
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onErrorContainer,
                textAlign = TextAlign.Center
            )
            OutlinedButton(
                onClick = onRetryClick,
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = colors.onErrorContainer
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Try again", color = colors.onErrorContainer)
            }
        }
    }
}

@Composable
private fun EmptyRecentFits(onCameraClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = colors.surfaceVariant
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconBadge(icon = Icons.Outlined.AutoAwesome, size = 56.dp)

            Text(
                text = "No outfits yet",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = colors.onSurfaceVariant
            )

            Text(
                text = "Outfits you create will show up here.\nStart by adding a piece to your wardrobe.",
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onSurfaceVariant.copy(alpha = 0.85f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Button(
                onClick = onCameraClick,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.CameraAlt,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Scan your first item",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Recently added clothing
// ---------------------------------------------------------------------------

@Composable
private fun RecentlyAddedContent(uiState: HomeUiState) {
    val colors = MaterialTheme.colorScheme

    when {
        // The loading spinner is already shown in "Recent fits".
        uiState.isLoading -> Unit

        uiState.recentItems.isEmpty() -> {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = colors.surfaceVariant
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    IconBadge(icon = Icons.Outlined.Checkroom, size = 44.dp)

                    Text(
                        text = "Items you add to your closet will appear here.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        else -> {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(uiState.recentItems, key = { it.id }) { item ->
                    PreviewCard(
                        icon = Icons.Outlined.Checkroom,
                        title = item.name,
                        subtitle = item.category,
                        width = 130.dp
                    )
                }
            }
        }
    }
}

/**
 * Rounded preview card with a themed icon placeholder where an image will go
 * once an image-loading library is added to the project.
 */
@Composable
private fun PreviewCard(
    icon: ImageVector,
    title: String,
    subtitle: String?,
    width: Dp
) {
    val colors = MaterialTheme.colorScheme

    Surface(
        modifier = Modifier.width(width),
        shape = RoundedCornerShape(20.dp),
        color = colors.surfaceVariant
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(colors.tertiaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = colors.onTertiaryContainer,
                    modifier = Modifier.size(40.dp)
                )
            }

            Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (!subtitle.isNullOrBlank()) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.onSurfaceVariant.copy(alpha = 0.8f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Previews (sample values exist only here, never in app data)
// ---------------------------------------------------------------------------

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun HomeScreenEmptyPreview() {
    Mystiq_FinalTheme {
        HomeScreen(
            uiState = HomeUiState(),
            onClosetClick = {},
            onVaultClick = {},
            onArchiveClick = {},
            onCameraClick = {},
            onProfileClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun HomeScreenWithDataPreview() {
    Mystiq_FinalTheme {
        HomeScreen(
            uiState = HomeUiState(
                recentOutfits = listOf("Outfit 1", "Outfit 2"),
                clothingCount = 12,
                outfitCount = 3,
                archivedCount = 1,
                recentItems = listOf(
                    HomeClothingPreview(1, "Linen shirt", "Tops"),
                    HomeClothingPreview(2, "Black jeans", "Bottoms")
                )
            ),
            onClosetClick = {},
            onVaultClick = {},
            onArchiveClick = {},
            onCameraClick = {},
            onProfileClick = {}
        )
    }
}