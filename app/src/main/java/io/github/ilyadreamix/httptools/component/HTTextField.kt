package io.github.ilyadreamix.httptools.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A custom [BasicTextField] component with customizable appearance and behavior.
 * @param value The current value of the [BasicTextField].
 * @param onValueChange The callback triggered when the value of the [BasicTextField] changes.
 * @param modifier The modifier to be applied to the [BasicTextField].
 * @param height The height of the [BasicTextField] container.
 * @param shape The shape of the [BasicTextField] container.
 * @param singleLine Whether the [BasicTextField] should be limited to a single line.
 * @param placeholder The composable function that provides the placeholder content when the [BasicTextField] is empty.
 * @param textStyle The style to be applied to the text displayed in the [BasicTextField].
 * @param containerColor The color of the [BasicTextField] container.
 * @param innerPadding The padding within the [BasicTextField] container.
 */
@Composable
fun HTTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 50.dp,
    shape: Shape = RectangleShape,
    singleLine: Boolean = false,
    placeholder: (@Composable () -> Unit)? = null,
    textStyle: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 16.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    innerPadding: PaddingValues = PaddingValues(
        start = 16.dp,
        end = 16.dp,
        top = 8.dp,
        bottom = 8.dp
    )
) {
    Box(
        modifier = modifier.height(height)
            .clip(shape)
            .background(containerColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = singleLine,
                textStyle = textStyle,
                cursorBrush = SolidColor(textStyle.color)
            )

            if (placeholder != null && value.isEmpty()) {
                CompositionLocalProvider(
                    LocalTextStyle provides textStyle.copy(
                        color = textStyle.color.copy(0.75f)
                    ),
                    content = placeholder
                )
            }
        }
    }
}
