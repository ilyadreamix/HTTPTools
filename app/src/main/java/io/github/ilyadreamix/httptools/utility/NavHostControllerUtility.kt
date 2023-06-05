package io.github.ilyadreamix.httptools.utility

import androidx.navigation.NavHostController

fun NavHostController.navigateAndRemoveCurrent(route: String) {
    popBackStack(route, inclusive = true)
}
