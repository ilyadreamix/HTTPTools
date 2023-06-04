package io.github.ilyadreamix.httptools.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import io.github.ilyadreamix.httptools.component.CardBox
import io.github.ilyadreamix.httptools.request.enumeration.RequestMethod
import io.github.ilyadreamix.httptools.request.model.Request
import io.github.ilyadreamix.httptools.utility.divideDp
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.HomeRequestItem(
    request: Request,
    onLongPress: () -> Unit
) {
    val locale = LocalConfiguration.current.locales[0]
    val chips = remember { request.chips() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(request) {
                detectTapGestures(onLongPress = { onLongPress() })
            }
            .animateItemPlacement(),
        shape = RoundedCornerShape(RequestItemCorners)
    ) {
        Column(
            modifier = Modifier.padding(RequestItemInnerPadding),
            verticalArrangement = Arrangement.spacedBy(RequestItemInnerSpacing)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(RequestItemInnerSpacing / 2)
                ) {
                    if (request.favouriteTime != null) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null
                        )
                    }

                    Text(
                        text = request.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.weight(1.0f))
                
                val dateFormat = SimpleDateFormat("MM/dd HH:mm", locale)
                val formattedTime = dateFormat.format(Date(request.createdAt))
                
                Text(
                    text = formattedTime,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(RequestItemInnerSpacing)
            ) {
                RequestMethodItem(method = request.method)
                Text(
                    text = request.url,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            @Suppress("DEPRECATION")
            FlowRow(
                crossAxisSpacing = RequestItemInnerSpacing / 2,
                mainAxisSpacing = RequestItemInnerSpacing / 2
            ) {
                chips.forEach {
                    HomeRequestChipItem(text = it)
                }
            }
        }
    }
}

@Composable
private fun HomeRequestChipItem(
    text: String,
    textSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize
) {
    CardBox(
        shape = RoundedCornerShape(textSize divideDp 2.5),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            text = text.uppercase(),
            modifier = Modifier.padding(
                top = textSize divideDp 6,
                bottom = textSize divideDp 6,
                start = textSize divideDp 3,
                end = textSize divideDp 3
            ),
            fontSize = textSize,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
private fun RequestMethodItem(
    method: RequestMethod,
    textSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize
) {
    CardBox(
        shape = RoundedCornerShape(textSize divideDp 2.5),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = method.name,
            modifier = Modifier.padding(
                top = textSize divideDp 6,
                bottom = textSize divideDp 6,
                start = textSize divideDp 3,
                end = textSize divideDp 3
            ),
            fontSize = textSize,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

private val RequestItemCorners = 16.dp
private val RequestItemInnerPadding = 12.dp
private val RequestItemInnerSpacing = 8.dp
