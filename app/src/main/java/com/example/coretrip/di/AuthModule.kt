package com.example.coretrip.di

import com.example.coretrip.data.auth.RoomAuthRepository
import com.example.coretrip.data.auth.security.PasswordHasher
import com.example.coretrip.data.auth.security.Pbkdf2PasswordHasher
import com.example.coretrip.data.session.DataStoreSessionRepository
import com.example.coretrip.domain.auth.AuthRepository
import com.example.coretrip.domain.session.SessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        repository: RoomAuthRepository
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindSessionRepository(
        repository: DataStoreSessionRepository
    ): SessionRepository

    @Binds
    @Singleton
    abstract fun bindPasswordHasher(
        hasher: Pbkdf2PasswordHasher
    ): PasswordHasher
}