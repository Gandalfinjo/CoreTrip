package com.example.coretrip.feature.auth.register

/**
 * Represents all UI state required by the registration screen.
 */
data class RegisterUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,

    val firstNameError: RegisterFieldError? = null,
    val lastNameError: RegisterFieldError? = null,
    val emailError: RegisterFieldError? = null,
    val usernameError: RegisterFieldError? = null,
    val passwordError: RegisterFieldError? = null,
    val confirmPasswordError: RegisterFieldError? = null,

    val error: RegisterError? = null
)

/**
 * Represents non-field-specific errors returned by registration.
 */
sealed interface RegisterError {
    /**
     * Registration failed for an unexpected reason.
     */
    data object Unexpected : RegisterError
}