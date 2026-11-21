package com.mystiq.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mystiq.ui.theme.Mystiq_FinalTheme

private const val TAB_ADD = "add"

private data class BottomBarTab(
    val key: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun MystiqBottomBar(
    selectedTab: String,
    onHomeClick: () -> Unit,
    onClosetClick: () -> Unit,
    onAddClick: () -> Unit,
    onOutfitsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    val tabs = listOf(
        BottomBarTab("home", "Home", Icons.Filled.Home, Icons.Outlined.Home, onHomeClick),
        BottomBarTab("closet", "Closet", Icons.Filled.Checkroom, Icons.Outlined.Checkroom, onClosetClick),
        BottomBarTab(TAB_ADD, "Add", Icons.Filled.Add, Icons.Filled.Add, onAddClick),
        BottomBarTab("outfits", "Outfits", Icons.Filled.AutoAwesome, Icons.Outlined.AutoAwesome, onOutfitsClick),
        BottomBarTab("profile", "Profile", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle, onProfileClick)
    )

    Column {
        // Thin separator so the bar doesn't blend into the page in light mode
        HorizontalDivider(color = colors.outlineVariant.copy(alpha = 0.5f))

        NavigationBar(
            containerColor = colors.surface,
            tonalElevation = 0.dp
        ) {
            tabs.forEach { tab ->
                val selected = selectedTab == tab.key
                val isAdd = tab.key == TAB_ADD

                NavigationBarItem(
                    selected = selected,
                    onClick = tab.onClick,
                    icon = {
                        if (isAdd) {
                            AddTabIcon(icon = tab.selectedIcon)
                        } else {
                            Icon(
                                imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                contentDescription = null
                            )
                        }
                    },
                    label = {
                        Text(
                            text = tab.label,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        // The pill uses secondaryContainer so it stays visible on the bar
                        // (primaryContainer was the same color as the bar background).
                        selectedIconColor = colors.onSecondaryContainer,
                        selectedTextColor = colors.onSurface,
                        // The Add button draws its own background, so no pill behind it
                        indicatorColor = if (isAdd) Color.Transparent else colors.secondaryContainer,
                        unselectedIconColor = colors.onSurfaceVariant,
                        unselectedTextColor = colors.onSurfaceVariant
                    )
                )
            }
        }
    }
}

@Composable
private fun AddTabIcon(icon: ImageVector) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .size(width = 56.dp, height = 32.dp)
            .background(colors.primary, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colors.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MystiqBottomBarPreview() {
    Mystiq_FinalTheme {
        MystiqBottomBar(
            selectedTab = "home",
            onHomeClick = {},
            onClosetClick = {},
            onAddClick = {},
            onOutfitsClick = {},
            onProfileClick = {}
        )
    }
}