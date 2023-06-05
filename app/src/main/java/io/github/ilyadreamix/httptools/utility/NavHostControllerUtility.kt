package io.github.ilyadreamix.httptools.utility

import androidx.navigation.NavHostController
import io.github.ilyadreamix.httptools.navigation.HTNavigationDestination

fun NavHostController.removeCurrent(route: String = currentHtDestination.route) {
    popBackStack(route, inclusive = true)
}

val NavHostController.currentHtDestination: HTNavigationDestination get() {
    val currentRoute = currentDestination?.route ?: HTNavigationDestination.HOME
    return HTNavigationDestination.values().find { currentRoute == it.route } ?: HTNavigationDestination.HOME
}
