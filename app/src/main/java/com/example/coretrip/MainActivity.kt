package com.example.coretrip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.coretrip.core.ui.theme.CoreTripTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Android activity that hosts the Compose application.
 *
 * Authentication state and navigation are handled by [CoreTripApp],
 * while this activity is responsible only for initializing the Compose
 * content and application theme.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoreTripTheme {
                CoreTripApp()
            }
        }
    }
}