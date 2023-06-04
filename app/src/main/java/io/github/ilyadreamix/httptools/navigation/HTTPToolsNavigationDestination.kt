package io.github.ilyadreamix.httptools.navigation

/**
 * [HTTPToolsNavigationHost] destinations.
 * @param route Defines destinationr route.
 */
sealed class HTTPToolsNavigationDestination(val route: String) {
    object Home : HTTPToolsNavigationDestination("home")
    object Create : HTTPToolsNavigationDestination("create")
}
