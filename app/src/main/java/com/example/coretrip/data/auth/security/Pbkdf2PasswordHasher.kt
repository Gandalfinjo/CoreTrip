package com.example.coretrip.data.auth.security

import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.inject.Inject


/**
 * Password hasher based on PBKDF2 with HMAC-SHA256.
 *
 * A unique random salt is generated for every password, and the relatively
 * expensive key-derivation operation makes offline password guessing more
 * difficult than using a general-purpose hash such as SHA-256 directly.
 */
class Pbkdf2PasswordHasher @Inject constructor() : PasswordHasher {

    companion object {
        private const val ITERATIONS = 600_000
        private const val KEY_LENGTH = 256
        private const val SALT_LENGTH = 16
    }

    override fun hash(password: String): PasswordHash {
        val salt = ByteArray(SALT_LENGTH)
        SecureRandom().nextBytes(salt)

        val hash = deriveKey(password, salt)

        return PasswordHash(
            hash = Base64.encodeToString(hash, Base64.NO_WRAP),
            salt = Base64.encodeToString(salt, Base64.NO_WRAP)
        )
    }

    override fun verify(
        password: String,
        storedHash: String,
        storedSalt: String
    ): Boolean {
        val salt = Base64.decode(storedSalt, Base64.NO_WRAP)
        val expectedHash = Base64.decode(storedHash, Base64.NO_WRAP)
        val actualHash = deriveKey(password, salt)

        return MessageDigest.isEqual(actualHash, expectedHash)
    }

    private fun deriveKey(
        password: String,
        salt: ByteArray
    ): ByteArray {
        val spec = PBEKeySpec(
            password.toCharArray(),
            salt,
            ITERATIONS,
            KEY_LENGTH
        )

        return try {
            SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA256")
                .generateSecret(spec)
                .encoded
        } finally {
            spec.clearPassword()
        }
    }
}