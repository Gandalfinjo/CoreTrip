package com.example.coretrip.feature.auth.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * Connects [RegisterViewModel] state and actions to [RegisterScreen].
 *
 * The route owns the ViewModel while the screen remains independent of
 * dependency injection and application infrastructure.
 */
@Composable
fun RegisterRoute(
    onLoginClick: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RegisterScreen(
        uiState = uiState,
        onFirstNameChanged = viewModel::onFirstNameChanged,
        onLastNameChanged = viewModel::onLastNameChanged,
        onEmailChanged = viewModel::onEmailChanged,
        onUsernameChanged = viewModel::onUsernameChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onConfirmPasswordChanged = viewModel::onConfirmPasswordChanged,
        onRegisterClick = viewModel::register,
        onLoginClick = onLoginClick,
        onErrorDismiss = viewModel::clearError
    )
}