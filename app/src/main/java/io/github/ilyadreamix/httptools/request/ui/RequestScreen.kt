package io.github.ilyadreamix.httptools.request.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.ilyadreamix.httptools.R
import io.github.ilyadreamix.httptools.home.HomeViewModel
import io.github.ilyadreamix.httptools.request.feature.model.Request
import io.github.ilyadreamix.httptools.utility.generateRandomColor
import io.github.ilyadreamix.httptools.utility.removeCurrent
import org.koin.compose.koinInject

@Composable
fun RequestScreen(
    navController: NavHostController,
    request: Request? = null,
    requestViewModel: RequestViewModel = koinInject(),
    homeViewModel: HomeViewModel = koinInject()
) {
    val context = LocalContext.current
    val lazyColumnState = rememberLazyListState()

    val viewModelRequest by requestViewModel.request.collectAsState()

    LaunchedEffect(Unit) {
        requestViewModel.updateRequest(request)
    }

    Scaffold(
        topBar = {
            RequestTopBar(
                lazyColumnState = lazyColumnState,
                onMenuClick = {
                    // ...
                },
                onBackClick = {
                    navController.removeCurrent()
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    if (viewModelRequest != null) {
                        homeViewModel.updateRequest(viewModelRequest!!)
                    }
                    Toast.makeText(context, R.string.saved, Toast.LENGTH_LONG).show()
                },
                modifier = Modifier.navigationBarsPadding(),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = null
                    )
                },
                text = {
                    Text(text = stringResource(R.string.save))
                }
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .background(generateRandomColor())
                )
            }
        }
        // endregion
    }
}
