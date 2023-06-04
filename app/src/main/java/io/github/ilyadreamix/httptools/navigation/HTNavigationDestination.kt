package io.github.ilyadreamix.httptools.navigation

/**
 * [HTNavigationHost] destinations.
 * @param route Defines destination route.
 */
sealed class HTNavigationDestination(val route: String) {
    object Home : HTNavigationDestination("home")
    object Create : HTNavigationDestination("create")
}
