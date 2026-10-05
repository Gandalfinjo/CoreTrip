package com.example.coretrip.domain.session

/**
 * Represents the minimum user information required to maintain an authenticated
 * application session.
 */
data class UserSession(
    val userId: Int,
    val username: String,
    val firstName: String,
    val lastName: String,
    val profilePicturePath: String?
)