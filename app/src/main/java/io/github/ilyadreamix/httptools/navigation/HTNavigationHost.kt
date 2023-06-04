package io.github.ilyadreamix.httptools.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.ilyadreamix.httptools.home.HomeScreen

/**
 * Composable that controls [NavHost] for HTTPTools.
 * @param navController Defines [NavHostController].
 */
@Composable
fun HTNavigationHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = HTNavigationDestination.Home.route
    ) {
        composable(route = HTNavigationDestination.Home.route) {
            HomeScreen()
        }

        composable(route = HTNavigationDestination.Create.route) {
            // ...
        }
    }
}
