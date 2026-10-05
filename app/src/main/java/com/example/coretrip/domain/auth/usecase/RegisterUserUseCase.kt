package com.example.coretrip.domain.auth.usecase

import com.example.coretrip.domain.auth.AuthRepository
import com.example.coretrip.domain.auth.RegistrationResult
import com.example.coretrip.domain.session.SessionRepository
import com.example.coretrip.domain.session.UserSession
import javax.inject.Inject

/**
 * Registers a new user and creates their application session.
 *
 * Successful registration automatically authenticates the newly created user,
 * matching the application's intended registration flow.
 */
class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository
) {
    /**
     * Attempts to register a new user.
     *
     * @return Registration result describing success or the reason registration
     * could not be completed.
     */
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        username: String,
        password: String
    ): RegisterResult {
        return when (
            val result = authRepository.register(
                firstName = firstName,
                lastName = lastName,
                email = email,
                username = username,
                password = password
            )
        ) {
            is RegistrationResult.Success -> {
                val user = result.user

                sessionRepository.saveSession(
                    UserSession(
                        userId = user.id,
                        username = user.username,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        profilePicturePath = user.profilePicturePath
                    )
                )

                RegisterResult.Success
            }

            RegistrationResult.UsernameAlreadyExists ->
                RegisterResult.UsernameAlreadyExists

            RegistrationResult.EmailAlreadyExists ->
                RegisterResult.EmailAlreadyExists

            RegistrationResult.InsertFailed ->
                RegisterResult.InsertFailed
        }
    }
}

/**
 * Represents the outcome of a registration attempt.
 */
sealed interface RegisterResult {
    /**
     * Registration and automatic authentication succeeded.
     */
    data object Success : RegisterResult

    /**
     * The requested username is already registered.
     */
    data object UsernameAlreadyExists : RegisterResult

    /**
     * The requested email address is already registered.
     */
    data object EmailAlreadyExists : RegisterResult

    /**
     * Registration failed for an unexpected reason.
     */
    data object InsertFailed : RegisterResult
}