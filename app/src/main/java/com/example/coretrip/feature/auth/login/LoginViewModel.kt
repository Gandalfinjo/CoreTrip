package com.example.coretrip.feature.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coretrip.domain.auth.usecase.LoginResult
import com.example.coretrip.domain.auth.usecase.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Manages state and user actions for the login feature.
 *
 * The ViewModel performs feature-level validation and delegates authentication
 * to [LoginUserUseCase]. It does not perform navigation or access persistence
 * directly.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())

    /**
     * Current state rendered by the login screen.
     */
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * Updates the username entered by the user.
     *
     * @param username New username value.
     */
    fun onUsernameChanged(username: String) {
        _uiState.update {
            it.copy(
                username = username,
                usernameError = null,
                error = null
            )
        }
    }

    /**
     * Updates the password entered by the user.
     *
     * @param password New password value.
     */
    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                passwordError = null,
                error = null
            )
        }
    }

    /**
     * Validates the login form and attempts authentication.
     */
    fun login() {
        val currentState = _uiState.value

        val validation = LoginValidator.validate(
            username = currentState.username,
            password = currentState.password
        )

        if (!validation.isValid) {
            _uiState.update {
                it.copy(
                    usernameError = validation.usernameError,
                    passwordError = validation.passwordError
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
                    loginUserUseCase(
                        username = currentState.username,
                        password = currentState.password
                    )
                ) {
                    LoginResult.Success -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    LoginResult.InvalidCredentials -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = LoginError.InvalidCredentials
                            )
                        }
                    }
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = LoginError.Unexpected
                    )
                }
            }
        }
    }

    /**
     * Clears the currently displayed login error.
     */
    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}