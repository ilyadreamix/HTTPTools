package io.github.ilyadreamix.httptools.component

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Draws a dashed stroke around the content.
 * @param stroke Dashed border stroke.
 * @param cornerRadius Corner radius.
 */
fun Modifier.dashedBorder(
    stroke: DashedBorderStroke,
    cornerRadius: Dp = 0.dp
) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeHeightPx = with(density) { stroke.height.toPx() }
        val cornerRadiusPx = with(density) { cornerRadius.toPx() }

        drawWithCache {
            onDrawBehind {
                drawRoundRect(
                    color = stroke.color,
                    style = Stroke(
                        width = strokeHeightPx,
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(
                                stroke.width.toPx(),
                                stroke.spacing.toPx()
                            ),
                            phase = 0f
                        )
                    ),
                    cornerRadius = CornerRadius(cornerRadiusPx)
                )
            }
        }
    }
)

/**
 * Dashed border stroke.
 * @param color The color of the dashed border.
 * @param height The height of the dashed border.
 * @param width The width of the dashed border.
 * @param spacing The spacing between dashed strokes.
 */
data class DashedBorderStroke(
    val color: Color,
    val height: Dp = StrokeHeight,
    val width: Dp = StrokeWidth,
    val spacing: Dp = StrokeSpacing
)

private val StrokeHeight = 3.dp
private val StrokeWidth = 12.dp
private val StrokeSpacing = 8.dp
