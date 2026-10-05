package com.example.coretrip.domain.auth.usecase

import com.example.coretrip.domain.auth.AuthRepository
import com.example.coretrip.domain.session.SessionRepository
import com.example.coretrip.domain.session.UserSession
import javax.inject.Inject

/**
 * Authenticates a user and creates the persisted application session.
 *
 * Keeping session creation in the use case means the Login feature does not
 * need to know how authentication results are persisted.
 */
class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository
) {
    /**
     * Attempts to authenticate a user.
     *
     * @param username Username supplied by the user.
     * @param password Password supplied by the user.
     * @return [LoginResult.Success] when authentication succeeds, otherwise
     * [LoginResult.InvalidCredentials].
     */
    suspend operator fun invoke(
        username: String,
        password: String
    ): LoginResult {
        val user = authRepository.login(username, password)
            ?: return LoginResult.InvalidCredentials

        sessionRepository.saveSession(
            UserSession(
                userId = user.id,
                username = user.username,
                firstName = user.firstName,
                lastName = user.lastName,
                profilePicturePath = user.profilePicturePath
            )
        )

        return LoginResult.Success
    }
}

/**
 * Represents the outcome of a login attempt.
 */
sealed interface LoginResult {
    /**
     * Login succeeded and the user session was persisted.
     */
    data object Success : LoginResult

    /**
     * The supplied credentials do not match a registered account.
     */
    data object InvalidCredentials : LoginResult
}