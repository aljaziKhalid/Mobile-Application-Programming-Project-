ggggvvvvvvvvgbggggg
package com.mystiq.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mystiq.ui.screens.gender.GenderRoute
import com.mystiq.ui.screens.bodyshape.BodyShapeRoute


@Composable
fun MystiqOnboardingNavigation(
    onFinish: (gender: String, bodyShape: String) -> Unit
) {
    var currentScreen by remember { mutableStateOf("gender") }
    var selectedGender by remember { mutableStateOf<String?>(null) }

    when (currentScreen) {

        "gender" -> {
            GenderRoute(
                onNext = { gender ->
                    selectedGender = gender
                    currentScreen = "bodyshape"
                }
            )
        }

        "bodyshape" -> {
            BodyShapeRoute(
                onDone = { bodyShape ->
                    selectedGender?.let { gender ->
                        onFinish(gender, bodyShape)
                    }
                }
            )
        }
    }
}

