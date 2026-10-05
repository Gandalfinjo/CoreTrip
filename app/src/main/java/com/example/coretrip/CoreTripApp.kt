package com.example.coretrip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coretrip.core.navigation.AuthNavHost

/**
 * Root composable for the CoreTrip application.
 *
 * Observes the persisted session state and selects the appropriate
 * application content. Authentication navigation is shown for logged-out
 * users, while authenticated users are routed to the main application.
 *
 * The application-level gradient background is defined here so individual
 * features do not need to know about the global visual shell.
 */
@Composable
fun CoreTripApp(
    appViewModel: AppViewModel = hiltViewModel()
) {
    val sessionState by appViewModel.sessionState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                    )
                )
            )
    ) {
        when (val state = sessionState) {
            SessionState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            SessionState.LoggedOut -> {
                AuthNavHost()
            }

            is SessionState.LoggedIn -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Welcome ${state.session.firstName}"
                    )
                }
            }
        }
    }
}