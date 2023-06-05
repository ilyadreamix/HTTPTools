package io.github.ilyadreamix.httptools.request.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.ilyadreamix.httptools.navigation.HTNavigationDestination
import io.github.ilyadreamix.httptools.request.RequestViewModel
import io.github.ilyadreamix.httptools.request.model.Request
import io.github.ilyadreamix.httptools.utility.generateRandomColor
import io.github.ilyadreamix.httptools.utility.navigateAndRemoveCurrent
import org.koin.compose.koinInject

@Composable
fun RequestScreen(
    navController: NavHostController,
    request: Request? = null,
    viewModel: RequestViewModel = koinInject()
) {
    val lazyColumnState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.updateRequest(request)
    }

    Scaffold(
        topBar = {
            RequestTopBar(
                lazyColumnState = lazyColumnState,
                onMenuClick = {
                    // ...
                },
                onBackClick = {
                    navController.navigateAndRemoveCurrent(HTNavigationDestination.Home.route)
                },
                request = request
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        // region Remove later
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            state = lazyColumnState
        ) {
            items(200, key = { it }) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(46.dp)
                        .background(generateRandomColor())
                )
            }
        }
        // endregion
    }
}
