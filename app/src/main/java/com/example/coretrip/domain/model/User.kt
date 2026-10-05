package com.example.coretrip.domain.model

/**
 * Represents an authenticated CoreTrip user independently of the storage mechanism.
 */
data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val profilePicturePath: String? = null
)
