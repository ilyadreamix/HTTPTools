package io.github.ilyadreamix.httptools.request.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.ilyadreamix.httptools.R
import io.github.ilyadreamix.httptools.component.HTTextField
import io.github.ilyadreamix.httptools.request.feature.enumeration.RequestMethod
import io.github.ilyadreamix.httptools.request.feature.model.Request
import io.github.ilyadreamix.httptools.theme.*
import io.github.ilyadreamix.httptools.utility.isScrolledDown
import io.github.ilyadreamix.httptools.utility.isScrolledUp
import org.koin.compose.koinInject

@Composable
fun RequestTopBar(
    lazyColumnState: LazyListState,
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit,
    requestViewModel: RequestViewModel = koinInject()
) {
    val viewModelRequest by requestViewModel.request.collectAsState()

    val url by remember(viewModelRequest?.url) {
        mutableStateOf(viewModelRequest?.url ?: "https://")
    }
    val method by remember(viewModelRequest?.method) {
        mutableStateOf(viewModelRequest?.method ?: RequestMethod.GET)
    }

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    var tabRowPadding by remember { mutableStateOf(8.dp) }
    val tabRowPaddingAnimation = animateDpAsState(
        targetValue = tabRowPadding,
        label = TAB_ROW_PADDING_ANIMATION
    )

    tabRowPadding =
        if (lazyColumnState.isScrolledDown())
            WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
        else ItemSpacing

    val localConfiguration = LocalConfiguration.current

    val unnamedString = stringResource(R.string.unnamed)

    Column {
        AnimatedVisibility(
            visible = lazyColumnState.isScrolledUp(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(
                        top = DefaultScreenPadding,
                        start = DefaultScreenPadding,
                        end = DefaultScreenPadding
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(DefaultScreenPadding)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(MenuButtonSize)
                        )
                    }

                    Text(
                        text = viewModelRequest?.name ?: unnamedString,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .width(localConfiguration.screenWidthDp.dp - DefaultScreenPadding - MenuButtonSize - ItemSpacing - 4.dp),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    )

                    IconButton(
                        onClick = onMenuClick,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null,
                            modifier = Modifier.size(MenuButtonSize)
                        )
                    }
                }

                Row {
                    RequestMethodButton(
                        method = method,
                        onMethodSelected = { selectedMethod ->
                            val newRequest = viewModelRequest?.copy(
                                method = selectedMethod
                            ) ?: Request(name = unnamedString, method = selectedMethod)

                            requestViewModel.updateRequest(newRequest)
                        }
                    )
                    RequestUrlTextField(
                        value = url,
                        onValueChange = { changedUrl ->
                            val newRequest = viewModelRequest?.copy(
                                url = changedUrl
                            ) ?: Request(name = unnamedString, url = changedUrl)

                            requestViewModel.updateRequest(newRequest)
                        }
                    )
                }
            }
        }

        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.padding(top = tabRowPaddingAnimation.value),
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(4.dp)
                        .padding(horizontal = DefaultScreenPadding)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(DefaultScreenPadding)
                        )
                )
            },
            divider = {}
        ) {
            val tabShape = RoundedCornerShape(
                topStart = DefaultContainerCorners,
                topEnd = DefaultContainerCorners
            )

            Tab(
                selected = selectedTabIndex == 0,
                text = { Text(text = stringResource(R.string.payload)) },
                onClick = { selectedTabIndex = 0 },
                modifier = Modifier.clip(tabShape)
            )

            Tab(
                selected = selectedTabIndex == 1,
                text = { Text(text = stringResource(R.string.headers)) },
                onClick = { selectedTabIndex = 1 },
                modifier = Modifier.clip(tabShape)
            )

            Tab(
                selected = selectedTabIndex == 2,
                text = { Text(text = stringResource(R.string.query)) },
                onClick = { selectedTabIndex = 2 },
                modifier = Modifier.clip(tabShape)
            )
        }
    }
}

@Composable
private fun RowScope.RequestUrlTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    HTTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.weight(1.0f),
        shape = RoundedCornerShape(
            topEnd = DefaultContainerCorners,
            bottomEnd = DefaultContainerCorners
        ),
        singleLine = true,
        placeholder = { Text(text = stringResource(R.string.url)) }
    )
}

@Composable
private fun RequestMethodButton(
    method: RequestMethod,
    onMethodSelected: (RequestMethod) -> Unit
) {
    var menuExpanded by remember {
        mutableStateOf(false)
    }

    Box {
        Button(
            onClick = { menuExpanded = true },
            modifier = Modifier.height(TextFieldHeight),
            shape = RoundedCornerShape(
                topStart = DefaultContainerCorners,
                bottomStart = DefaultContainerCorners
            )
        ) {
            AnimatedContent(
                targetState = method,
                label = METHOD_CHANGE_ANIMATION
            ) { targetMethod ->
                Text(
                    text = targetMethod.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false }
        ) {
            RequestMethod.values().forEach {
                DropdownMenuItem(
                    text = { Text(text = it.name) },
                    onClick = {
                        menuExpanded = false
                        onMethodSelected(it)
                    }
                )
            }
        }
    }
}

private val TextFieldHeight = 50.dp
private val ItemSpacing = 8.dp
private val MenuButtonSize = 28.dp

private const val METHOD_CHANGE_ANIMATION = "RequestScreen.RequestTopBar.methodChangeAnimation"
private const val TAB_ROW_PADDING_ANIMATION = "RequestScreen.RequestTopBar.tabRowPaddingAnimation"
