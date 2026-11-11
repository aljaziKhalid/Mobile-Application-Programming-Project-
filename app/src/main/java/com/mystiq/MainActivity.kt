
package com.mystiq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mystiq.ui.screens.camera.CameraRoute
import com.mystiq.ui.theme.Mystiq_FinalTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Mystiq_FinalTheme {
                CameraRoute(
                    onBackClick = {
                        finish()
                    },
                    onImageReady = { },
                    onCaptureClick = { }
                )
            }
        }
    }
}
