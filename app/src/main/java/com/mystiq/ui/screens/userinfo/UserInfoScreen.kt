package com.mystiq.ui.screens.userinfo

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
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// TODO: move these into ui/theme/Color.kt so all screens share them
private val ScreenBackground = Color(0xFFF0F0F0)
private val FieldBackground = Color(0xFFDADADA)
private val ButtonBackground = Color(0xFFCFE0F3)
private val ButtonContent = Color(0xFF5B7A99)
private val SilhouetteColor = Color(0xFF4F5270)

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

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            snackbarHostState.showSnackbar("Changes saved")
            onSavedMessageShown()
        }
    }

    Scaffold(
        modifier = modifier,
        containerColor = ScreenBackground,
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
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "User Info",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
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
                    containerColor = ButtonBackground,
                    contentColor = ButtonContent
                )
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = ButtonContent
                    )
                } else {
                    Text("Save Changes", fontWeight = FontWeight.Bold, fontSize = 18.sp)
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
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
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
                focusedContainerColor = FieldBackground,
                unfocusedContainerColor = FieldBackground,
                errorContainerColor = FieldBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
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
                .background(Color.White)
                .then(if (selected) Modifier.border(2.dp, ButtonContent, pill) else Modifier),
            contentAlignment = Alignment.Center
        ) {
            // TODO: replace with the silhouette images, e.g. painterResource(R.drawable.body_hourglass)
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                tint = SilhouetteColor,
                modifier = Modifier.size(48.dp)
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = shape.label,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserInfoScreenPreview() {
    MaterialTheme {
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
