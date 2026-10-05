package com.example.coretrip.data.auth

import com.example.coretrip.data.auth.local.UserDao
import com.example.coretrip.data.auth.local.UserEntity
import com.example.coretrip.data.auth.security.PasswordHasher
import com.example.coretrip.domain.auth.AuthRepository
import com.example.coretrip.domain.auth.RegistrationResult
import com.example.coretrip.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implements authentication using the local Room database.
 *
 * This class converts between Room entities and domain models and delegates
 * password hashing to [PasswordHasher].
 */
@Singleton
class RoomAuthRepository @Inject constructor(
    private val userDao: UserDao,
    private val passwordHasher: PasswordHasher
) : AuthRepository {

    override suspend fun login(
        username: String,
        password: String
    ): User? {
        val normalizedUsername = username.trim()

        val entity = userDao.findByUsername(normalizedUsername)
            ?: return null

        val validPassword = withContext(Dispatchers.Default) {
            passwordHasher.verify(
                password = password,
                storedHash = entity.passwordHash,
                storedSalt = entity.passwordSalt
            )
        }

        return entity.toDomain()
            .takeIf { validPassword }
    }

    override suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        username: String,
        password: String
    ): RegistrationResult {
        val normalizedUsername = username.trim()
        val normalizedEmail = email.trim().lowercase()

        if (userDao.findByUsername(normalizedUsername) != null) {
            return RegistrationResult.UsernameAlreadyExists
        }

        if (userDao.findByEmail(normalizedEmail) != null) {
            return RegistrationResult.EmailAlreadyExists
        }

        val passwordHash = withContext(Dispatchers.Default) {
            passwordHasher.hash(password)
        }

        val user = UserEntity(
            firstName = firstName.trim(),
            lastName = lastName.trim(),
            email = normalizedEmail,
            username = normalizedUsername,
            passwordHash = passwordHash.hash,
            passwordSalt = passwordHash.salt
        )

        val id = userDao.insert(user)

        if (id == -1L) {
            return when {
                userDao.findByUsername(normalizedUsername) != null ->
                    RegistrationResult.UsernameAlreadyExists

                userDao.findByEmail(normalizedEmail) != null ->
                    RegistrationResult.EmailAlreadyExists

                else ->
                    RegistrationResult.InsertFailed
            }
        }

        return RegistrationResult.Success(
            user = user.copy(id = id.toInt()).toDomain()
        )
    }

    private fun UserEntity.toDomain(): User =
        User(
            id = id,
            firstName = firstName,
            lastName = lastName,
            email = email,
            username = username,
            profilePicturePath = profilePicturePath
        )
}