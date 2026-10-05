package com.example.coretrip.feature.auth.login

/**
 * Represents all UI state required by the login screen.
 */
data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val usernameError: LoginFieldError? = null,
    val passwordError: LoginFieldError? = null,
    val error: LoginError? = null
)

/**
 * Represents errors returned after a login attempt.
 */
sealed interface LoginError {

    /**
     * Credentials did not match a registered account.
     */
    data object InvalidCredentials : LoginError

    /**
     * An unexpected error prevented the login operation from completing.
     */
    data object Unexpected : LoginError
}