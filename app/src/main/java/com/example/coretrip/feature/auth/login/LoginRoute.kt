package com.example.coretrip.feature.auth.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * Connects [LoginViewModel] state and actions to [LoginScreen].
 *
 * This composable is the dependency-aware entry point for the login feature,
 * while [LoginScreen] remains independently previewable and testable.
 */
@Composable
fun LoginRoute(
    onRegisterClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        uiState = uiState,
        onUsernameChanged = viewModel::onUsernameChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onLoginClick = viewModel::login,
        onRegisterClick = onRegisterClick,
        onErrorDismiss = viewModel::clearError
    )
}