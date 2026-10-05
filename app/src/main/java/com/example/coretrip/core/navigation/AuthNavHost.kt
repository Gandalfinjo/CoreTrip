package com.example.coretrip.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coretrip.feature.auth.login.LoginRoute
import com.example.coretrip.feature.auth.register.RegisterRoute

/**
 * Navigation graph for unauthenticated users.
 *
 * Contains the login and registration destinations and handles movement
 * between those screens. Authentication success itself is not handled here;
 * the application root reacts to the persisted session instead.
 */
@Composable
fun AuthNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationDestination.Login
    ) {
        composable<NavigationDestination.Login> {
            LoginRoute(
                onRegisterClick = {
                    navController.navigate(NavigationDestination.Register)
                }
            )
        }

        composable<NavigationDestination.Register> {
            RegisterRoute(
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}