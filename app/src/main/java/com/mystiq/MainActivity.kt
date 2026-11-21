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
import com.mystiq.ui.screens.profile.ProfileRoute

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Mystiq_FinalTheme {

                var currentScreen by remember {
                    mutableStateOf("home")
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
                                // TODO: connect when a Closet route exists in the app module
                            },
                            onVaultClick = {
                                // TODO: connect when a Vault route exists in the app module
                            },
                            onArchiveClick = {
                                currentScreen = "archive"
                            },
                            onCameraClick = {
                                currentScreen = "camera"
                            },
                            onProfileClick = {
                                currentScreen = "profile"
                            }
                        )
                    }

                    "archive" -> {
                        ArchiveRoute(
                            onBackClick = {
                                currentScreen = "home"
                            },
                            onHomeClick = {
                                currentScreen = "home"
                            },
                            onAddClick = {
                                currentScreen = "camera"
                            },
                            onProfileClick = {
                                currentScreen = "profile"
                            }
                            // Closet and Outfits keep their default no-op until
                            // their routes exist (same as Profile).
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

                    "profile" -> {
                        ProfileRoute(
                            onLogoutClick = {
                                username = ""
                                password = ""
                                currentScreen = "signin"
                            },
                            onClosetClick = {
                                // Connect Closet when its route is ready
                            },
                            onHomeClick = {
                                currentScreen = "home"
                            },
                            onAddClick = {
                                currentScreen = "camera"
                            },
                            onOutfitsClick = {
                                // Connect Outfits when its route is ready
                            }
                        )
                    }

                }
            }
        }
    }
}