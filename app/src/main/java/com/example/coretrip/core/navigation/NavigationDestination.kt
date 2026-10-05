package com.example.coretrip.core.navigation

import kotlinx.serialization.Serializable

/**
 * Defines destinations used by the application's navigation graphs.
 *
 * Destinations are type-safe and serializable so navigation arguments can
 * be represented by Kotlin types rather than manually constructed routes.
 */
sealed interface NavigationDestination {
    /**
     * Login destination in the authentication graph.
     */
    @Serializable
    data object Login : NavigationDestination


    /**
     * Registration destination in the authentication graph.
     */
    @Serializable
    data object Register : NavigationDestination
}