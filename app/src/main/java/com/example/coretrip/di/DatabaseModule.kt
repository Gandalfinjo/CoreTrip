package com.example.coretrip.di

import android.content.Context
import androidx.room.Room
import com.example.coretrip.data.auth.local.UserDao
import com.example.coretrip.data.local.CoreTripDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): CoreTripDatabase =
        Room.databaseBuilder(
            context,
            CoreTripDatabase::class.java,
            "core_trip.db"
        ).build()

    @Provides
    fun provideUserDao(
        database: CoreTripDatabase
    ): UserDao = database.userDao()
}