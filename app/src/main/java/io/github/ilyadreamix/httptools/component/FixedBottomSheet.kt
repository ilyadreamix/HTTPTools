package io.github.ilyadreamix.httptools.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Composable function that creates a [ModalBottomSheet] with fixed navigation bar padding.
 * @param onDismiss Executes when the user clicks outside of the bottom sheet.
 * @param modifier Optional Modifier for the bottom sheet.
 * @param content The content to be displayed inside the bottom sheet.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixedBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val navigationBarInsets = WindowInsets.navigationBars
    val navigationBarHeight = navigationBarInsets.asPaddingValues().calculateBottomPadding()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier.offset(y = navigationBarHeight),
        scrimColor = Color.Transparent
    ) {
        content()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(navigationBarHeight)
        )
    }
}
