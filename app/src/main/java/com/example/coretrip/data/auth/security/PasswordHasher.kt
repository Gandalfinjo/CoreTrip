package com.example.coretrip.data.auth.security

/**
 * Defines password hashing and verification behavior.
 *
 * The rest of the authentication data layer depends on this abstraction
 * rather than a specific cryptographic implementation.
 */
data class PasswordHash(
    val hash: String,
    val salt: String
)

/**
 * Provides secure password hashing and verification.
 *
 * Implementations are responsible for generating a salt when hashing and
 * verifying a plaintext password against the stored hash and salt.
 */
interface PasswordHasher {
    /**
     * Hashes a plaintext password using a newly generated salt.
     *
     * @param password Plaintext password supplied by the user.
     * @return Generated password hash and salt.
     */
    fun hash(password: String): PasswordHash

    /**
     * Verifies a plaintext password against stored credentials.
     *
     * @param password Plaintext password to verify.
     * @param storedHash Previously generated password hash.
     * @param storedSalt Salt used to generate the stored hash.
     * @return True when the password matches the stored credentials.
     */
    fun verify(
        password: String,
        storedHash: String,
        storedSalt: String
    ): Boolean
}