package com.mystiq.ui.screens.gender

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private val CardShape = RoundedCornerShape(20.dp)

private data class GenderOption(
    val name: String,
    val symbol: String
)

private val GenderOptions = listOf(
    GenderOption("Female", "♀"),
    GenderOption("Male", "♂")
)

@Composable
fun GenderScreen(
    uiState: GenderUiState,
    onGenderSelected: (String) -> Unit,
    onNext: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {

            // Scrollable content: the button below stays pinned
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Choose Your Gender",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "This helps us create a better outfit experience for you",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(36.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GenderOptions.forEach { option ->
                        GenderCard(
                            option = option,
                            selected = uiState.selectedGender == option.name,
                            onClick = { onGenderSelected(option.name) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = onNext,
                enabled = uiState.selectedGender != null,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary,
                    disabledContainerColor = colors.primary.copy(alpha = 0.35f),
                    disabledContentColor = colors.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Next",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun GenderCard(
    option: GenderOption,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    val containerColor by animateColorAsState(
        targetValue = if (selected) colors.primaryContainer
        else colors.surfaceVariant.copy(alpha = 0.5f),
        label = "container"
    )
    val borderColor by animateColorAsState(
        targetValue = if (selected) colors.primary else colors.outlineVariant,
        label = "border"
    )
    val badgeColor by animateColorAsState(
        targetValue = if (selected) colors.primary else colors.surface,
        label = "badge"
    )
    val symbolColor by animateColorAsState(
        targetValue = if (selected) colors.onPrimary else colors.primary,
        label = "symbol"
    )

    Column(
        modifier = modifier
            .height(220.dp)
            .background(containerColor, CardShape)
            .border(if (selected) 2.dp else 1.dp, borderColor, CardShape)
            .clip(CardShape)
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Symbol inside a round badge
        Box(
            modifier = Modifier
                .size(88.dp)
                .background(badgeColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = option.symbol,
                style = MaterialTheme.typography.displayMedium,
                color = symbolColor
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = option.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            color = if (selected) colors.onPrimaryContainer else colors.onSurface
        )
    }
}