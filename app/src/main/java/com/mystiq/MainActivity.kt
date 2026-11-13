package com.mystiq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*

import com.mystiq.ui.screens.auth.SignInScreen
import com.mystiq.ui.screens.auth.SignUpRoute
import com.mystiq.ui.screens.home.HomeRoute
import com.mystiq.ui.screens.archive.ArchiveRoute
import com.mystiq.ui.screens.camera.CameraRoute
import com.mystiq.ui.theme.Mystiq_FinalTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Mystiq_FinalTheme {

                var currentScreen by remember {
                    mutableStateOf("signin")
                }

                var username by remember {
                    mutableStateOf("")
                }

                var password by remember {
                    mutableStateOf("")
                }

                when (currentScreen) {

                    "signin" -> {
                        SignInScreen(
                            username = username,
                            password = password,
                            onUsernameChange = {
                                username = it
                            },
                            onPasswordChange = {
                                password = it
                            },
                            onForgotPasswordClick = {
                                // TODO
                            },
                            onSignInClick = {
                                currentScreen = "home"
                            },
                            onSignUpClick = {
                                currentScreen = "signup"
                            },
                            isSignInEnabled =
                                username.isNotBlank() &&
                                password.isNotBlank()
                        )
                    }

                    "signup" -> {
                        SignUpRoute(
                            onSignInClick = {
                                currentScreen = "signin"
                            },
                            onSignUpSuccess = {
                                currentScreen = "signin"
                            }
                        )
                    }

                    "home" -> {
                        HomeRoute(
                            onClosetClick = {
                                // TODO
                            },
                            onVaultClick = {
                                // TODO
                            },
                            onArchiveClick = {
                                currentScreen = "archive"
                            },
                            onCameraClick = {
                                currentScreen = "camera"
                            },
                            onProfileClick = {
                                // TODO
                            }
                        )
                    }

                    "archive" -> {
                        ArchiveRoute(
                            onBackClick = {
                                currentScreen = "home"
                            }
                        )
                    }

                    "camera" -> {
                        CameraRoute(
                            onBackClick = {
                                currentScreen = "home"
                            },
                            onImageReady = { },
                            onCaptureClick = { }
                        )
                    }
                }
            }
        }
    }
}