package com.example.coretrip.data.auth.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Provides database operations for user accounts.
 */
@Dao
interface UserDao {
    /**
     * Inserts a user account.
     *
     * If a username or email violates a unique constraint, the insert is
     * ignored and the method returns -1.
     *
     * @param user User entity to persist.
     * @return Generated database ID, or -1 when the insert is ignored.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity): Long

    /**
     * Finds a user by username.
     *
     * @param username Username to search for.
     * @return Matching user, or null when no user exists.
     */
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun findByUsername(username: String): UserEntity?

    /**
     * Finds a user by email address.
     *
     * @param email Email address to search for.
     * @return Matching user, or null when no user exists.
     */
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): UserEntity?

    /**
     * Finds a user by database ID.
     *
     * @param userId Database ID of the user.
     * @return Matching user, or null when no user exists.
     */
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun findById(userId: Int): UserEntity?
}