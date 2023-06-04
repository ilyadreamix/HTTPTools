package io.github.ilyadreamix.httptools.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

/**
 * Material Design filled card with [Box] inside of it.
 * @param modifier The [Modifier] to be applied to this card.
 * @param boxModifier The [Modifier] to be applied to the [Box].
 * @param shape Defines the shape of this card's container.
 * @param colors [CardColors] that will be used to resolve the colors used for this card.
 * @param elevation [CardElevation] used to resolve the elevation for this card.
 * @param border The border to draw around the container of this card .
 * @param contentAlignment [Alignment] of the content inside of [Box].
 * @param content The content inside of [Box].
 */
@Composable
fun CardBox(
    modifier: Modifier = Modifier,
    boxModifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(),
    border: BorderStroke? = null,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit
) {
    Card(modifier, shape, colors, elevation, border) {
        Box(
            modifier = boxModifier,
            contentAlignment = contentAlignment,
            content = content
        )
    }
}
