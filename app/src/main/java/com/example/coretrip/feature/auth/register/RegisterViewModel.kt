package com.example.coretrip.feature.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coretrip.domain.auth.usecase.RegisterResult
import com.example.coretrip.domain.auth.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Manages registration form state and user actions.
 *
 * The ViewModel performs feature-level validation and delegates account
 * creation to [RegisterUserUseCase]. It does not access Room, DataStore,
 * or navigation directly.
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())

    /**
     * Current state rendered by the registration screen.
     */
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    /**
     * Updates the first name field.
     *
     * @param value New first name value.
     */
    fun onFirstNameChanged(value: String) {
        _uiState.update {
            it.copy(
                firstName = value,
                firstNameError = null,
                error = null
            )
        }
    }

    /**
     * Updates the last name field.
     *
     * @param value New last name value.
     */
    fun onLastNameChanged(value: String) {
        _uiState.update {
            it.copy(
                lastName = value,
                lastNameError = null,
                error = null
            )
        }
    }

    /**
     * Updates the email field.
     *
     * @param value New email value.
     */
    fun onEmailChanged(value: String) {
        _uiState.update {
            it.copy(
                email = value,
                emailError = null,
                error = null
            )
        }
    }

    /**
     * Updates the username field.
     *
     * @param value New username value.
     */
    fun onUsernameChanged(value: String) {
        _uiState.update {
            it.copy(
                username = value,
                usernameError = null,
                error = null
            )
        }
    }

    /**
     * Updates the password field and clears stale password-related errors.
     *
     * @param value New password value.
     */
    fun onPasswordChanged(value: String) {
        _uiState.update { state ->
            state.copy(
                password = value,
                passwordError = null,
                confirmPasswordError =
                    if (state.confirmPassword.isNotEmpty() &&
                        state.confirmPassword != value
                    ) {
                        RegisterFieldError.PasswordsDoNotMatch
                    } else {
                        null
                    },
                error = null
            )
        }
    }

    /**
     * Updates the password confirmation field.
     *
     * @param value New confirmation value.
     */
    fun onConfirmPasswordChanged(value: String) {
        _uiState.update { state ->
            state.copy(
                confirmPassword = value,
                confirmPasswordError = null,
                error = null
            )
        }
    }

    /**
     * Validates the registration form and attempts to create the account.
     */
    fun register() {
        val state = _uiState.value

        val validation = RegisterValidator.validate(
            firstName = state.firstName,
            lastName = state.lastName,
            email = state.email,
            username = state.username,
            password = state.password,
            confirmPassword = state.confirmPassword
        )

        if (!validation.isValid) {
            _uiState.update {
                it.copy(
                    firstNameError = validation.firstNameError,
                    lastNameError = validation.lastNameError,
                    emailError = validation.emailError,
                    usernameError = validation.usernameError,
                    passwordError = validation.passwordError,
                    confirmPasswordError = validation.confirmPasswordError
                )
            }

            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                when (
                    registerUserUseCase(
                        firstName = state.firstName,
                        lastName = state.lastName,
                        email = state.email,
                        username = state.username,
                        password = state.password
                    )
                ) {
                    RegisterResult.Success -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    RegisterResult.UsernameAlreadyExists -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                usernameError =
                                    RegisterFieldError.UsernameAlreadyExists
                            )
                        }
                    }

                    RegisterResult.EmailAlreadyExists -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                emailError =
                                    RegisterFieldError.EmailAlreadyExists
                            )
                        }
                    }

                    RegisterResult.InsertFailed -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = RegisterError.Unexpected
                            )
                        }
                    }
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = RegisterError.Unexpected
                    )
                }
            }
        }
    }

    /**
     * Clears the currently displayed registration error.
     */
    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}