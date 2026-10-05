package com.example.coretrip.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coretrip.data.auth.local.UserDao
import com.example.coretrip.data.auth.local.UserEntity

/**
 * Room database used by CoreTrip.
 *
 * The schema is intentionally introduced incrementally as features are
 * migrated to the new application.
 */
@Database(
    entities = [
        UserEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class CoreTripDatabase : RoomDatabase() {
    /**
     * Provides access to persisted user data.
     */
    abstract fun userDao(): UserDao
}