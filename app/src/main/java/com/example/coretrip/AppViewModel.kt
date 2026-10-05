package com.example.coretrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coretrip.domain.session.SessionRepository
import com.example.coretrip.domain.session.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Provides application-level authentication state.
 *
 * The ViewModel observes [SessionRepository] and exposes a UI-friendly
 * session state to the application root. This keeps authentication-driven
 * navigation outside individual authentication screens.
 */
@HiltViewModel
class AppViewModel @Inject constructor(
    sessionRepository: SessionRepository
) : ViewModel() {
    /**
     * Current authentication state of the application.
     */
    val sessionState: StateFlow<SessionState> =
        sessionRepository.session
            .map { session ->
                if (session == null) {
                    SessionState.LoggedOut
                } else {
                    SessionState.LoggedIn(session)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SessionState.Loading
            )
}

/**
 * Represents the authentication state observed by the application root.
 */
sealed interface SessionState {
    /**
     * Session state is still being restored from persistent storage.
     */
    data object Loading : SessionState

    /**
     * No authenticated user session exists.
     */
    data object LoggedOut : SessionState

    /**
     * An authenticated user session exists.
     *
     * @property session Persisted information about the authenticated user.
     */
    data class LoggedIn(
        val session: UserSession
    ) : SessionState
}