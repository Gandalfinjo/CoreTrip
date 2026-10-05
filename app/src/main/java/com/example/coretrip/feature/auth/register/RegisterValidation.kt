package com.example.coretrip.feature.auth.register

/**
 * Represents validation errors associated with individual registration fields
 * or account uniqueness checks.
 */
sealed interface RegisterFieldError {
    data object FirstNameRequired : RegisterFieldError
    data object LastNameRequired : RegisterFieldError
    data object EmailRequired : RegisterFieldError
    data object InvalidEmail : RegisterFieldError
    data object UsernameRequired : RegisterFieldError
    data object PasswordRequired : RegisterFieldError
    data object PasswordTooShort : RegisterFieldError
    data object ConfirmPasswordRequired : RegisterFieldError
    data object PasswordsDoNotMatch : RegisterFieldError

    data object UsernameAlreadyExists : RegisterFieldError
    data object EmailAlreadyExists : RegisterFieldError
}

/**
 * Contains the field-level validation result for the registration form.
 */
data class RegisterValidationResult(
    val firstNameError: RegisterFieldError? = null,
    val lastNameError: RegisterFieldError? = null,
    val emailError: RegisterFieldError? = null,
    val usernameError: RegisterFieldError? = null,
    val passwordError: RegisterFieldError? = null,
    val confirmPasswordError: RegisterFieldError? = null
) {
    /**
     * Indicates whether every registration field passed validation.
     */
    val isValid: Boolean
        get() = firstNameError == null &&
                lastNameError == null &&
                emailError == null &&
                usernameError == null &&
                passwordError == null &&
                confirmPasswordError == null
}

/**
 * Validates registration form input before an account is created.
 */
object RegisterValidator {

    private const val MIN_PASSWORD_LENGTH = 6

    private val emailPattern = Regex(
        pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    )

    /**
     * Validates all registration fields.
     *
     * This validator checks only form-input rules. Account uniqueness is
     * handled by the authentication data layer.
     *
     * @return Validation result containing any field-level errors.
     */
    fun validate(
        firstName: String,
        lastName: String,
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): RegisterValidationResult {
        return RegisterValidationResult(
            firstNameError = if (firstName.isBlank()) {
                RegisterFieldError.FirstNameRequired
            } else {
                null
            },

            lastNameError = if (lastName.isBlank()) {
                RegisterFieldError.LastNameRequired
            } else {
                null
            },

            emailError = when {
                email.isBlank() ->
                    RegisterFieldError.EmailRequired

                !emailPattern.matches(email.trim()) ->
                    RegisterFieldError.InvalidEmail

                else ->
                    null
            },

            usernameError = if (username.isBlank()) {
                RegisterFieldError.UsernameRequired
            } else {
                null
            },

            passwordError = when {
                password.isBlank() ->
                    RegisterFieldError.PasswordRequired

                password.length < MIN_PASSWORD_LENGTH ->
                    RegisterFieldError.PasswordTooShort

                else ->
                    null
            },

            confirmPasswordError = when {
                confirmPassword.isBlank() ->
                    RegisterFieldError.ConfirmPasswordRequired

                confirmPassword != password ->
                    RegisterFieldError.PasswordsDoNotMatch

                else ->
                    null
            }
        )
    }
}