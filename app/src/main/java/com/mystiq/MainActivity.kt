
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
import com.mystiq.ui.screens.profile.ProfileRoute
import com.mystiq.ui.screens.bodyshape.BodyShapeRoute
import com.mystiq.ui.navigation.MystiqOnboardingNavigation
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

                var selectedGender by remember {
                    mutableStateOf<String?>(null)
                }

                var selectedBodyShape by remember {
                    mutableStateOf<String?>(null)
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
                            onForgotPasswordClick = {},
                            onSignInClick = {
                                currentScreen = "onboarding"
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

                    "onboarding" -> {
                        MystiqOnboardingNavigation(
                            onFinish = { gender, bodyShape ->
                                selectedGender = gender
                                selectedBodyShape = bodyShape
                                currentScreen = "home"
                            }
                        )
                    }

                    "home" -> {
                        HomeRoute(
                            onClosetClick = {},
                            onVaultClick = {},
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
                        )
                    }

                    "camera" -> {
                        CameraRoute(
                            onBackClick = {
                                currentScreen = "home"
                            },
                            onImageReady = {},
                            onCaptureClick = {}
                        )
                    }

                    "profile" -> {
                        ProfileRoute(
                            onLogoutClick = {
                                username = ""
                                password = ""
                                selectedGender = null
                                selectedBodyShape = null
                                currentScreen = "signin"
                            },

                            onClosetClick = {},

                            onBodyShapeClick = {
                                currentScreen = "bodyshape"
                            },

                            onSettingsClick = {},

                            onPreferencesClick = {},

                            onHomeClick = {
                                currentScreen = "home"
                            },

                            onAddClick = {
                                currentScreen = "camera"
                            },

                            onOutfitsClick = {}
                        )
                    }

                    "bodyshape" -> {
                        BodyShapeRoute(
                            onDone = { shape ->
                                selectedBodyShape = shape
                                currentScreen = "profile"
                            }
                        )
                    }
                }
            }
        }
    }
}
