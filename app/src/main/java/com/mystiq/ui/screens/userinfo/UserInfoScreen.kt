package com.mystiq.ui.screens.userinfo

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mystiq.ui.theme.MystiqBackground
import com.mystiq.ui.theme.MystiqField
import com.mystiq.ui.theme.MystiqText
import com.mystiq.ui.theme.Mystiq_FinalTheme

/** Stateless screen: it only draws the state and reports clicks. No ViewModel here. */
@Composable
fun UserInfoScreen(
    uiState: UserInfoUiState,
    onBackClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onBodyShapeSelect: (BodyShape) -> Unit,
    onSaveClick: () -> Unit,
    onSavedMessageShown: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val colors = MaterialTheme.colorScheme

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            snackbarHostState.showSnackbar("Changes saved")
            onSavedMessageShown()
        }
    }

    Scaffold(
        modifier = modifier,
        containerColor = MystiqBackground,
        contentColor = colors.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0), // the main Scaffold (with bottom bar) handles insets
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = colors.onBackground
                )
            }
            Text(
                text = "User Info",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground
            )
            Spacer(Modifier.height(20.dp))

            LabeledTextField(
                label = "Name",
                value = uiState.name,
                onValueChange = onNameChange,
                errorMessage = uiState.nameError,
                keyboardType = KeyboardType.Text
            )
            LabeledTextField(
                label = "Email",
                value = uiState.email,
                onValueChange = onEmailChange,
                errorMessage = uiState.emailError,
                keyboardType = KeyboardType.Email
            )
            LabeledTextField(
                label = "Height (optional)",
                value = uiState.height,
                onValueChange = onHeightChange,
                errorMessage = uiState.heightError,
                keyboardType = KeyboardType.Number,
                suffix = "cm"
            )

            Text(
                text = "Body Shape",
                style = MaterialTheme.typography.titleMedium,
                color = colors.onBackground,
                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BodyShape.entries.forEach { shape ->
                    BodyShapeOption(
                        shape = shape,
                        selected = uiState.bodyShape == shape,
                        onClick = { onBodyShapeSelect(shape) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onSaveClick,
                enabled = !uiState.isSaving,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                )
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = colors.onPrimary
                    )
                } else {
                    Text(
                        "Save Changes",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String?,
    keyboardType: KeyboardType,
    suffix: String? = null
) {
    val colors = MaterialTheme.colorScheme
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = colors.onBackground,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = errorMessage != null,
            supportingText = errorMessage?.let { { Text(it) } },
            suffix = suffix?.let { { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MystiqField,
                unfocusedContainerColor = MystiqField,
                errorContainerColor = MystiqField,
                focusedTextColor = MystiqText,
                unfocusedTextColor = MystiqText,
                errorTextColor = MystiqText,
                cursorColor = MystiqText,
                focusedSuffixColor = MystiqText,
                unfocusedSuffixColor = MystiqText,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                // error text sits on the mauve background, so use the light error tone
                errorSupportingTextColor = colors.errorContainer
            )
        )
    }
}

@Composable
private fun BodyShapeOption(
    shape: BodyShape,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val pill = RoundedCornerShape(50)
    Column(
        modifier = modifier.selectable(selected = selected, onClick = onClick, role = Role.RadioButton),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clip(pill)
                .background(if (selected) colors.tertiaryContainer else MystiqField)
                .then(if (selected) Modifier.border(3.dp, colors.primary, pill) else Modifier),
            contentAlignment = Alignment.Center
        ) {
            BodySilhouette(
                shape = shape,
                color = MystiqText,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp, horizontal = 4.dp)
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = shape.label,
            style = MaterialTheme.typography.labelSmall,
            color = colors.onBackground,
            fontWeight = if (selected) FontWeight.Bold else null,
            textAlign = TextAlign.Center
        )
    }
}

// ---------- Body shape silhouettes (drawn in code, no images needed) ----------

/** Half-widths of the body at each level, as a fraction of the drawing width. */
private data class BodyProportions(
    val shoulders: Float,
    val bust: Float,
    val waist: Float,
    val hips: Float
)

private fun BodyShape.proportions(): BodyProportions = when (this) {
    BodyShape.HOURGLASS -> BodyProportions(shoulders = 0.34f, bust = 0.33f, waist = 0.18f, hips = 0.34f)
    BodyShape.PEAR -> BodyProportions(shoulders = 0.24f, bust = 0.25f, waist = 0.20f, hips = 0.39f)
    BodyShape.APPLE -> BodyProportions(shoulders = 0.30f, bust = 0.34f, waist = 0.35f, hips = 0.29f)
    BodyShape.RECTANGLE -> BodyProportions(shoulders = 0.29f, bust = 0.29f, waist = 0.27f, hips = 0.29f)
    BodyShape.INVERTED_TRIANGLE -> BodyProportions(shoulders = 0.41f, bust = 0.34f, waist = 0.24f, hips = 0.24f)
}

/**
 * Draws a simple front-facing silhouette whose shoulders, bust, waist and hips
 * change with the body shape. Heights are fractions of the canvas height.
 */
@Composable
private fun BodySilhouette(
    shape: BodyShape,
    color: Color,
    modifier: Modifier = Modifier
) {
    val body = shape.proportions()
    Canvas(modifier = modifier) {
        val h = size.height
        val w = minOf(size.width, h * 0.55f)   // keep the figure's proportions
        val cx = size.width / 2f
        fun p(fx: Float, fy: Float) = Offset(cx + fx * w, fy * h)

        // Right side of the body, from the neck down to the ankle
        val right = listOf(
            p(0.06f, 0.15f),
            p(0.09f, 0.19f),
            p(body.shoulders, 0.24f),
            p(body.bust, 0.36f),
            p(body.waist, 0.49f),
            p(body.hips, 0.62f),
            p(body.hips * 0.82f, 0.74f),
            p(body.hips * 0.50f, 0.86f),
            p(0.10f, 0.97f)
        )
        // Left side is the mirror image, walked from the ankle back up to the neck
        val left = right.map { Offset(2 * cx - it.x, it.y) }.reversed()

        val outline = Path().apply {
            smoothThrough(right, startNewShape = true)
            lineTo(p(0.03f, 0.98f).x, p(0.03f, 0.98f).y)    // inner right ankle
            lineTo(p(0f, 0.68f).x, p(0f, 0.68f).y)          // between the legs
            lineTo(p(-0.03f, 0.98f).x, p(-0.03f, 0.98f).y)  // inner left ankle
            smoothThrough(left, startNewShape = false)
            close()
        }
        drawPath(outline, color)
        drawCircle(color, radius = h * 0.07f, center = p(0f, 0.075f)) // head
    }
}

/** Joins the points with smooth curves (each point bends the line, the midpoints are passed through). */
private fun Path.smoothThrough(points: List<Offset>, startNewShape: Boolean) {
    val first = points.first()
    if (startNewShape) moveTo(first.x, first.y) else lineTo(first.x, first.y)
    var current = first
    for (i in 1 until points.size - 1) {
        val control = points[i]
        val end = (points[i] + points[i + 1]) / 2f
        val c1 = current + (control - current) * (2f / 3f)
        val c2 = end + (control - end) * (2f / 3f)
        cubicTo(c1.x, c1.y, c2.x, c2.y, end.x, end.y)
        current = end
    }
    val last = points.last()
    lineTo(last.x, last.y)
}

@Preview(showBackground = true)
@Composable
private fun UserInfoScreenPreview() {
    Mystiq_FinalTheme {
        UserInfoScreen(
            uiState = UserInfoUiState(
                name = "Sara",
                email = "sara@example.com",
                bodyShape = BodyShape.PEAR
            ),
            onBackClick = {},
            onNameChange = {},
            onEmailChange = {},
            onHeightChange = {},
            onBodyShapeSelect = {},
            onSaveClick = {},
            onSavedMessageShown = {}
        )
    }
}

@Preview(name = "Errors", showBackground = true)
@Composable
private fun UserInfoScreenErrorPreview() {
    Mystiq_FinalTheme {
        UserInfoScreen(
            uiState = UserInfoUiState(
                email = "sara@",
                nameError = "Name is required",
                emailError = "Enter a valid email"
            ),
            onBackClick = {},
            onNameChange = {},
            onEmailChange = {},
            onHeightChange = {},
            onBodyShapeSelect = {},
            onSaveClick = {},
            onSavedMessageShown = {}
        )
    }
}
