package com.example.coretrip.data.session

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.coretrip.domain.session.SessionRepository
import com.example.coretrip.domain.session.UserSession
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.sessionDataStore by preferencesDataStore(
    name = "session"
)

/**
 * Stores and observes the authenticated user session using Preferences DataStore.
 *
 * DataStore-specific details are kept inside this class so the rest of the
 * application depends only on [SessionRepository].
 */
@Singleton
class DataStoreSessionRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : SessionRepository {

    private object Keys {
        val USER_ID = intPreferencesKey("user_id")
        val USERNAME = stringPreferencesKey("username")
        val FIRST_NAME = stringPreferencesKey("first_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val PROFILE_PICTURE = stringPreferencesKey("profile_picture_path")
    }

    override val session: Flow<UserSession?> =
        context.sessionDataStore.data.map { preferences ->
            preferences.toUserSession()
        }

    override suspend fun saveSession(session: UserSession) {
        context.sessionDataStore.edit { preferences ->
            preferences[Keys.USER_ID] = session.userId
            preferences[Keys.USERNAME] = session.username
            preferences[Keys.FIRST_NAME] = session.firstName
            preferences[Keys.LAST_NAME] = session.lastName

            session.profilePicturePath?.let {
                preferences[Keys.PROFILE_PICTURE] = it
            } ?: preferences.remove(Keys.PROFILE_PICTURE)
        }
    }

    override suspend fun clearSession() {
        context.sessionDataStore.edit { preferences ->
            preferences.clear()
        }
    }

    private fun Preferences.toUserSession(): UserSession? {
        val userId = this[Keys.USER_ID] ?: return null
        val username = this[Keys.USERNAME] ?: return null
        val firstName = this[Keys.FIRST_NAME] ?: return null
        val lastName = this[Keys.LAST_NAME] ?: return null

        return UserSession(
            userId = userId,
            username = username,
            firstName = firstName,
            lastName = lastName,
            profilePicturePath = this[Keys.PROFILE_PICTURE]
        )
    }
}