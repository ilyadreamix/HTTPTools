package io.github.ilyadreamix.httptools.utility

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember

@Composable
fun LazyListState.isScrolledDown(): Boolean {
    val offset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) { derivedStateOf { (firstVisibleItemScrollOffset - offset) > 0 } }.value
}

@Composable
fun LazyListState.isScrolledUp() = !isScrolledDown()
