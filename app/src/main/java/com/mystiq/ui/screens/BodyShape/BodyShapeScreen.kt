package com.mystiq.ui.screens.bodyshape

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private val CardShape = RoundedCornerShape(20.dp)

// Half-widths as a fraction of the figure's width
private data class ShapeOption(
    val name: String,
    val shoulder: Float,
    val waist: Float,
    val hip: Float
)

private val ShapeOptions = listOf(
    ShapeOption("Slim", shoulder = 0.30f, waist = 0.20f, hip = 0.27f),
    ShapeOption("Medium", shoulder = 0.33f, waist = 0.26f, hip = 0.34f),
    ShapeOption("Curvy", shoulder = 0.34f, waist = 0.23f, hip = 0.44f)
)

@Composable
fun BodyShapeScreen(
    uiState: BodyShapeUiState,
    onShapeSelected: (String) -> Unit,
    onDone: () -> Unit
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
                    text = "Choose Your Body Shape",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Select the figure that best represents your body shape",
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
                    ShapeOptions.forEach { option ->
                        ShapeCard(
                            option = option,
                            selected = uiState.selectedShape == option.name,
                            onClick = { onShapeSelected(option.name) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = onDone,
                enabled = uiState.selectedShape != null,
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
                    text = "Done",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun ShapeCard(
    option: ShapeOption,
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
    val figureColor by animateColorAsState(
        targetValue = if (selected) colors.primary
        else colors.onSurfaceVariant.copy(alpha = 0.5f),
        label = "figure"
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
        BodyFigure(option = option, color = figureColor)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = option.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            color = if (selected) colors.onPrimaryContainer else colors.onSurface
        )
    }
}

// Simple silhouette drawn in code. Swap for real illustrations when you have them.
@Composable
private fun BodyFigure(
    option: ShapeOption,
    color: Color
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(132.dp)
    ) {
        val w = size.width
        val h = size.height
        val cx = w / 2f

        val headRadius = h * 0.08f
        val shoulderY = headRadius * 2f + h * 0.06f
        val waistY = h * 0.50f
        val hipY = h * 0.66f

        val shoulder = w * option.shoulder
        val waist = w * option.waist
        val hip = w * option.hip
        val foot = hip * 0.55f

        val upper = waistY - shoulderY
        val lower = hipY - waistY

        drawCircle(
            color = color,
            radius = headRadius,
            center = Offset(cx, headRadius)
        )

        val body = Path().apply {
            // Left side: shoulder -> waist -> hip -> foot
            moveTo(cx - shoulder, shoulderY)
            cubicTo(
                cx - shoulder, shoulderY + upper * 0.45f,
                cx - waist, waistY - upper * 0.30f,
                cx - waist, waistY
            )
            cubicTo(
                cx - waist, waistY + lower * 0.50f,
                cx - hip, hipY - lower * 0.40f,
                cx - hip, hipY
            )
            lineTo(cx - foot, h)
            lineTo(cx + foot, h)
            lineTo(cx + hip, hipY)

            // Right side, back up to the shoulder
            cubicTo(
                cx + hip, hipY - lower * 0.40f,
                cx + waist, waistY + lower * 0.50f,
                cx + waist, waistY
            )
            cubicTo(
                cx + waist, waistY - upper * 0.30f,
                cx + shoulder, shoulderY + upper * 0.45f,
                cx + shoulder, shoulderY
            )

            // Rounded top across the shoulders
            cubicTo(
                cx + shoulder * 0.5f, shoulderY - h * 0.05f,
                cx - shoulder * 0.5f, shoulderY - h * 0.05f,
                cx - shoulder, shoulderY
            )
            close()
        }

        drawPath(path = body, color = color)
    }
}