package io.github.ilyadreamix.httptools.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.ilyadreamix.httptools.home.HomeScreen
import io.github.ilyadreamix.httptools.home.HomeViewModel
import io.github.ilyadreamix.httptools.request.ui.RequestScreen
import org.koin.androidx.compose.koinViewModel

/**
 * Composable that controls [NavHost] for HTTPTools.
 * @param navController Defines [NavHostController].
 */
@Composable
fun HTNavigationHost(
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = koinViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = HTNavigationDestination.HOME.route
    ) {
        composable(route = HTNavigationDestination.HOME.route) {
            HomeScreen(navController)
        }

        composable(route = HTNavigationDestination.REQUEST.route) { backStackEntry ->
            val requestId = backStackEntry.arguments?.getString("id")
            val requestListResult by homeViewModel.requestListResult.collectAsState()
            val request = requestListResult.data?.find { it.id == requestId }

            RequestScreen(navController, request)
        }
    }
}
