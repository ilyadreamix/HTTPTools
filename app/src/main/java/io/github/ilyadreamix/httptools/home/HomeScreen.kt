package io.github.ilyadreamix.httptools.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
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
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.setBackgroundColor
import io.github.ilyadreamix.httptools.R
import io.github.ilyadreamix.httptools.component.LoadingDialog
import io.github.ilyadreamix.httptools.navigation.HTNavigationDestination
import io.github.ilyadreamix.httptools.request.model.Request
import io.github.ilyadreamix.httptools.request.model.stubRequestList
import io.github.ilyadreamix.httptools.utility.ifNull
import io.github.ilyadreamix.httptools.utility.isScrolledDown
import io.github.ilyadreamix.httptools.viewmodel.enumeration.HTViewModelTaskState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {
    var topBarState by remember { mutableStateOf(TopAppBarState(0.0f, 0.0f, 0.0f)) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)

    val lazyColumnState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val requestListResult by viewModel.requestListResult.collectAsState()
    var longPressedItemRequest by remember { mutableStateOf<Request?>(null) }

    if (longPressedItemRequest != null) {
        HomeItemDialog(
            request = longPressedItemRequest!!,
            onDismissRequest = { longPressedItemRequest = null },
            onDeleteRequest = {
                val newList = requestListResult.data!!.filterNot { it == longPressedItemRequest }
                viewModel.updateRequestList(newList)

                longPressedItemRequest = null
            },
            onFavouriteRequest = { favoriteTime ->
                viewModel.updateFavouriteTime(
                    updatedRequest = longPressedItemRequest!!,
                    favoriteTime = System.currentTimeMillis() ifNull favoriteTime
                )

                longPressedItemRequest = null
            },
            onEditRequest = {
                val route = HTNavigationDestination.Request.route.replace("{id}", longPressedItemRequest!!.id)
                navController.navigate(route)

                longPressedItemRequest = null
            }
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeTopBar(
                scrollBehavior = scrollBehavior,
                onMenuClick = {
                    // TODO: Settings, help, etc.
                },
                onSearchClick = {
                    // TODO: Request search
                }
            )
        },
        floatingActionButton = {
            HomeFloatingActionButton(
                showTip = requestListResult.state == HTViewModelTaskState.ERROR,
                showScrollToTopButton = lazyColumnState.isScrolledDown(),
                onClick = {
                    // TODO: Open RequestScreen
                },
                onScrollToTopRequest = {
                    coroutineScope.launch {
                        lazyColumnState.animateScrollToItem(0)
                        topBarState = TopAppBarState(0.0f, 0.0f, 0.0f)
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        when (requestListResult.state == HTViewModelTaskState.LOADING) {
            true -> LoadingDialog()
            else -> HomeContent(innerPadding, lazyColumnState) { request ->
                longPressedItemRequest = request
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(
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

            // region Will be deleted after release
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
            // endregion

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
    showScrollToTopButton: Boolean = false,
    onClick: () -> Unit = {},
    onScrollToTopRequest: () -> Unit = {}
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
        AnimatedContent(
            targetState = showScrollToTopButton,
            label = SCROLL_TO_TOP_ANIMATION_LABEL
        ) { animatedShowScrollToTopButton ->
            when (animatedShowScrollToTopButton) {
                true -> ExtendedFloatingActionButton(
                    onClick = onScrollToTopRequest,
                    modifier = Modifier.navigationBarsPadding(),
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowUpward,
                            contentDescription = null
                        )
                    },
                    text = {
                        Text(text = stringResource(R.string.scroll_to_the_top))
                    }
                )
                else -> ExtendedFloatingActionButton(
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
            }
        }

        if (showTip) balloonWindow.showAlignTop()
    }
}

private const val SCROLL_TO_TOP_ANIMATION_LABEL = "HomeScreen.HomeFloatingActionButton.scrollToTopAnimation"
