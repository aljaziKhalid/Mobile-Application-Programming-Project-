package com.mystiq.ui.screens.WareTracker
data class WearItem(
    val name: String,
    val wearTimes: Int,
    val lastWorn: String,
    val isAlert: Boolean = false
)

data class WearTrackerUiState(
    val items: List<WearItem> = listOf(
        WearItem("Purple Shirt", 1, "Oct 22, 2026"),
        WearItem("Black pants", 9, "Oct 12, 2026"),
        WearItem("Blazer", 4, "Sep 2, 2026", isAlert = true)
    )
)