package io.github.ilyadreamix.httptools.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.setBackgroundColor
import io.github.ilyadreamix.httptools.R
import io.github.ilyadreamix.httptools.component.LoadingDialog
import io.github.ilyadreamix.httptools.request.model.Request
import io.github.ilyadreamix.httptools.request.model.stubRequestList
import io.github.ilyadreamix.httptools.viewmodel.enumeration.ViewModelTaskState
import org.koin.androidx.compose.koinViewModel
import io.github.ilyadreamix.httptools.viewmodel.model.ViewModelTaskResult as CommonViewModelTaskResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    val requestListResult by viewModel.requestListResult.collectAsState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeTopApplicationBar(
                scrollBehavior = scrollBehavior,
                onMenuClick = {
                    // ...
                },
                onSearchClick = {
                    // ...
                }
            )
        },
        floatingActionButton = {
            HomeFloatingActionButton(showTip = requestListResult.state == ViewModelTaskState.ERROR) {
                // ...
            }
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        when (requestListResult.state == ViewModelTaskState.LOADING) {
            true -> LoadingDialog()
            else -> HomeContent(innerPadding)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopApplicationBar(
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: HomeViewModel = koinViewModel(),
    onSearchClick: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.app_name))
        },
        actions = {
            IconButton(onSearchClick) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null
                )
            }

            IconButton(
                onClick = {
                    when (viewModel.requestListResult.value.data) {
                        null -> viewModel.saveRequestList(stubRequestList())
                        else -> viewModel.deleteRequestList()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.BugReport,
                    contentDescription = null
                )
            }

            IconButton(onMenuClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun HomeFloatingActionButton(
    showTip: Boolean = false,
    onClick: () -> Unit = {}
) {
    val colorScheme = MaterialTheme.colorScheme
    val balloonBuilder = rememberBalloonBuilder {
        setArrowSize(12)
        setArrowPosition(0.5f)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setMarginBottom(4)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(BalloonSizeSpec.WRAP)
        setPadding(16)
        setMarginHorizontal(16)
        setCornerRadius(16.0f)
        setBackgroundColor(colorScheme.surfaceVariant)
        setBalloonAnimation(BalloonAnimation.FADE)
    }

    Balloon(
        builder = balloonBuilder,
        balloonContent = {
            Text(text = stringResource(R.string.create_tooltip))
        }
    ) { balloonWindow ->
        ExtendedFloatingActionButton(
            onClick = onClick,
            modifier = Modifier.navigationBarsPadding(),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = null
                )
            },
            text = {
                Text(text = stringResource(R.string.create))
            }
        )

        if (showTip) balloonWindow.showAlignTop()
    }
}

internal typealias ViewModelTaskResult = CommonViewModelTaskResult<List<Request>>
