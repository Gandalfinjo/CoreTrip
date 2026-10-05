package com.example.coretrip.domain.session

import kotlinx.coroutines.flow.Flow

/**
 * Provides access to the currently persisted user session.
 */
interface SessionRepository {
    /**
     * Emits the current authenticated session, or null when no user is logged in.
     */
    val session: Flow<UserSession?>

    /**
     * Persists the supplied user session.
     *
     * @param session Session information belonging to the authenticated user.
     */
    suspend fun saveSession(session: UserSession)

    /**
     * Removes the persisted user session.
     */
    suspend fun clearSession()
}