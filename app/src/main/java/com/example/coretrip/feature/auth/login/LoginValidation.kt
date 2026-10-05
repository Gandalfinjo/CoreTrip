package com.example.coretrip.feature.auth.login

/**
 * Represents validation errors that can occur in the login form.
 */
sealed interface LoginFieldError {
    /**
     * Username field was left empty.
     */
    data object UsernameRequired : LoginFieldError

    /**
     * Password field was left empty.
     */
    data object PasswordRequired : LoginFieldError
}

/**
 * Contains the validation result for the login form.
 */
data class LoginValidationResult(
    val usernameError: LoginFieldError? = null,
    val passwordError: LoginFieldError? = null
) {
    /**
     * Indicates whether all login fields passed validation.
     */
    val isValid: Boolean
        get() = usernameError == null && passwordError == null
}

/**
 * Validates user input before a login request is submitted.
 */
object LoginValidator {
    /**
     * Validates username and password input.
     *
     * @return Validation result containing field-level errors, if any.
     */
    fun validate(
        username: String,
        password: String
    ): LoginValidationResult {
        return LoginValidationResult(
            usernameError = if (username.isBlank()) {
                LoginFieldError.UsernameRequired
            } else {
                null
            },
            passwordError = if (password.isBlank()) {
                LoginFieldError.PasswordRequired
            } else {
                null
            }
        )
    }
}