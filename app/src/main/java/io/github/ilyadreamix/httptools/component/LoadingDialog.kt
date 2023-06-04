package io.github.ilyadreamix.httptools.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

/** A dialog showing the unknown state of some process. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
    size: Dp = DialogSize,
    corners: Dp = DialogCorners
) {
    AlertDialog(
        onDismissRequest = {},
        modifier = modifier.size(size),
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        CardBox(
            modifier = Modifier.fillMaxSize(),
            boxModifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(corners)
        ) {
            CircularProgressIndicator()
        }
    }
}

private val DialogSize = 82.dp
private val DialogCorners = 16.dp
