package com.example.coretrip.domain.auth

import com.example.coretrip.domain.model.User

/**
 * Provides authentication-related operations without exposing the underlying
 * storage or authentication mechanism.
 */
interface AuthRepository {
    /**
     * Authenticates a user with username and password.
     *
     * @param username Username supplied by the user.
     * @param password Plaintext password supplied by the user.
     * @return Authenticated user when credentials are valid, otherwise null.
     */
    suspend fun login(
        username: String,
        password: String
    ): User?

    /**
     * Registers a new user account.
     *
     * @return Result describing whether registration succeeded or which
     * uniqueness constraint prevented it.
     */
    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        username: String,
        password: String
    ): RegistrationResult
}

/**
 * Represents the possible outcomes of user registration.
 */
sealed interface RegistrationResult {
    /**
     * Registration completed successfully.
     *
     * @property user Newly created user.
     */
    data class Success(
        val user: User
    ) : RegistrationResult

    /**
     * The requested username is already registered.
     */
    data object UsernameAlreadyExists : RegistrationResult

    /**
     * The requested email address is already registered.
     */
    data object EmailAlreadyExists : RegistrationResult

    /**
     * Registration could not be completed for an unexpected reason.
     */
    data object InsertFailed : RegistrationResult
}