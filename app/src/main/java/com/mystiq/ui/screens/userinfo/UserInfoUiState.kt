package com.mystiq.ui.screens.userinfo

enum class BodyShape(val label: String) {
    HOURGLASS("Hourglass"),
    PEAR("Pear"),
    APPLE("Apple"),
    RECTANGLE("Rectangle"),
    INVERTED_TRIANGLE("Inverted Triangle")
}

data class UserInfoUiState(
    val name: String = "",
    val email: String = "",
    val height: String = "",          // kept as text while typing, converted to Int on save
    val bodyShape: BodyShape? = null,
    val nameError: String? = null,
    val emailError: String? = null,
    val heightError: String? = null,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false      // true -> screen shows "Changes saved" once
)
