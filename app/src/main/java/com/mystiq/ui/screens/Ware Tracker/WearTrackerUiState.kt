
package com.mystiq.ui.screens.WareTracker

data class WearItem(
    val name: String,
    val wearTimes: Int,
    val lastWorn: String,
    val isAlert: Boolean = false,
    val image: String = ""
)

data class WearTrackerUiState(
    val items: List<WearItem> = emptyList()
)
