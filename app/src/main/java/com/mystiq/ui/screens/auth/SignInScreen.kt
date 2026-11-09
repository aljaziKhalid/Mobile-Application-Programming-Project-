
package com.mystiq.ui.screens.auth

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mystiq.ui.theme.Mystiq_FinalTheme

private val ScreenPadding = 28.dp
private val ControlShape = RoundedCornerShape(16.dp)

@Composable
fun SignInScreen(
    username: String,
    password: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    isSignInEnabled: Boolean
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val colors = MaterialTheme.colorScheme
    val fieldColors = signInFieldColors()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = ScreenPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(48.dp))

                BrandHeader()

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Sign in to continue your style journey",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = onUsernameChange,
                    label = { Text("Username") },
                    placeholder = { Text("Enter your username") },
                    singleLine = true,
                    shape = ControlShape,
                    colors = fieldColors,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text("Password") },
                    placeholder = { Text("Enter your password") },
                    singleLine = true,
                    shape = ControlShape,
                    colors = fieldColors,
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (isSignInEnabled) onSignInClick()
                        }
                    ),
                    trailingIcon = {
                        TextButton(
                            onClick = {
                                passwordVisible = !passwordVisible
                            }
                        ) {
                            Text(
                                text = if (passwordVisible) "Hide" else "Show",
                                color = colors.primary
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                TextButton(
                    onClick = onForgotPasswordClick,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = "Forgot password?",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ScreenPadding, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onSignInClick,
                    enabled = isSignInEnabled,
                    shape = ControlShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary,
                        contentColor = colors.onPrimary,
                        disabledContainerColor = colors.primary.copy(alpha = 0.35f),
                        disabledContentColor = colors.onPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp)
                ) {
                    Text(
                        text = "Sign in",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.onSurfaceVariant
                    )

                    TextButton(onClick = onSignUpClick) {
                        Text(
                            text = "Sign Up",
                            color = colors.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "YOUR STYLE, YOUR STORY.",
                    style = MaterialTheme.typography.labelSmall,
                    letterSpacing = 2.sp,
                    color = colors.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun BrandHeader() {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .size(72.dp)
            .background(colors.primaryContainer, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "M",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = colors.onPrimaryContainer
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "MYSTIQ",
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Medium,
        letterSpacing = 5.sp,
        color = colors.onBackground
    )
}

@Composable
private fun signInFieldColors(): TextFieldColors {
    val colors = MaterialTheme.colorScheme

    return OutlinedTextFieldDefaults.colors(
        focusedContainerColor = colors.surfaceVariant,
        unfocusedContainerColor = colors.surfaceVariant,
        focusedTextColor = colors.onSurfaceVariant,
        unfocusedTextColor = colors.onSurfaceVariant,
        focusedPlaceholderColor = colors.onSurfaceVariant.copy(alpha = 0.55f),
        unfocusedPlaceholderColor = colors.onSurfaceVariant.copy(alpha = 0.55f),
        focusedBorderColor = colors.primary,
        unfocusedBorderColor = colors.outlineVariant,
        focusedLabelColor = colors.primary,
        unfocusedLabelColor = colors.onSurfaceVariant,
        cursorColor = colors.primary
    )
}

@Preview(showBackground = true)
@Composable
private fun SignInScreenPreview() {
    Mystiq_FinalTheme {
        SignInScreen(
            username = "",
            password = "",
            onUsernameChange = {},
            onPasswordChange = {},
            onForgotPasswordClick = {},
            onSignInClick = {},
            onSignUpClick = {},
            isSignInEnabled = false
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SignInScreenDarkPreview() {
    Mystiq_FinalTheme {
        SignInScreen(
            username = "sara",
            password = "secret123",
            onUsernameChange = {},
            onPasswordChange = {},
            onForgotPasswordClick = {},
            onSignInClick = {},
            onSignUpClick = {},
            isSignInEnabled = true
        )
    }
}
