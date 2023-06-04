package io.github.ilyadreamix.httptools.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.ilyadreamix.httptools.R
import io.github.ilyadreamix.httptools.component.DashedBorderStroke
import io.github.ilyadreamix.httptools.component.dashedBorder
import io.github.ilyadreamix.httptools.request.model.Request
import io.github.ilyadreamix.httptools.request.model.sortedByFavouriteTime
import io.github.ilyadreamix.httptools.theme.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeContent(
    innerPadding: PaddingValues,
    lazyColumnState: LazyListState,
    viewModel: HomeViewModel = koinViewModel(),
    onLongItemPress: (Request) -> Unit = {}
) {
    val requestListResult by viewModel.requestListResult.collectAsState()
    val sortedRequestList by remember {
        derivedStateOf {
            requestListResult.data.sortedByFavouriteTime()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        when (sortedRequestList.isEmpty()) {
            true -> HomeEmptyContent()
            else -> HomeNotEmptyContent(sortedRequestList, lazyColumnState, onLongItemPress)
        }
    }
}

@Composable
private fun BoxScope.HomeEmptyContent() {
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .padding(start = DefaultScreenPadding, end = DefaultScreenPadding)
            .clip(RoundedCornerShape(DefaultContainerCorners))
            .dashedBorder(
                stroke = DashedBorderStroke(
                    color = MaterialTheme.colorScheme.onBackground.copy(NoTemplatesContentAlpha)
                ),
                cornerRadius = DefaultContainerCorners
            )
    ) {
        Text(
            text = stringResource(R.string.no_templates),
            modifier = Modifier
                .padding(DefaultScreenPadding)
                .alpha(NoTemplatesContentAlpha),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun HomeNotEmptyContent(
    requestList: List<Request>,
    lazyColumnState: LazyListState,
    onLongPress: (Request) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(start = DefaultContainerOuterPadding, end = DefaultContainerOuterPadding),
        state = lazyColumnState,
        verticalArrangement = Arrangement.spacedBy(DefaultContainerSpacing),
        contentPadding = WindowInsets.navigationBars.asPaddingValues()
    ) {
        items(
            items = requestList,
            key = { it.id },
            contentType = { it.favouriteTime }
        ) { request ->
            HomeRequestItem(
                request = request,
                onLongPress = { onLongPress(request) }
            )
        }
    }
}

private val NoTemplatesIconSize = 64.dp
private val NoTemplatesSpacing = 12.dp
private const val NoTemplatesContentAlpha = 0.75f
