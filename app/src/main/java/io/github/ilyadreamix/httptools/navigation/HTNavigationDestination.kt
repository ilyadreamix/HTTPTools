package io.github.ilyadreamix.httptools.navigation

/**
 * [HTNavigationHost] destinations.
 * @param route Defines destination route.
 */
enum class HTNavigationDestination(val route: String) {
    HOME("home"),
    REQUEST("request/{id}")
}
