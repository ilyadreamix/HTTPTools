// This shit just had me go 🤡

package io.github.ilyadreamix.httptools.home

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import io.github.ilyadreamix.httptools.R
import io.github.ilyadreamix.httptools.component.CardBox
import io.github.ilyadreamix.httptools.request.model.Request
import io.github.ilyadreamix.httptools.theme.DefaultBigAlertButtonPadding
import io.github.ilyadreamix.httptools.theme.DefaultBigAlertButtonSpacing
import io.github.ilyadreamix.httptools.theme.DefaultBigAlertCorners
import io.github.ilyadreamix.httptools.theme.DefaultBigAlertInnerPadding
import io.github.ilyadreamix.httptools.theme.DefaultBigAlertSpacing

@Composable
fun HomeItemDialog(
    request: Request,
    onDismissRequest: () -> Unit = {},
    onEditRequest: () -> Unit = {},
    onDeleteRequest: () -> Unit = {},
    onFavouriteRequest: (Long?) -> Unit = {}
) {
    Dialog(onDismissRequest) {
        CardBox(
            boxModifier = Modifier
                .fillMaxWidth()
                .padding(DefaultBigAlertInnerPadding),
            shape = RoundedCornerShape(DefaultBigAlertCorners),
            colors = CardDefaults.cardColors(containerColor = AlertDialogDefaults.containerColor),
            elevation = CardDefaults.cardElevation(defaultElevation = AlertDialogDefaults.TonalElevation),
            contentAlignment = Alignment.TopStart
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(DefaultBigAlertSpacing)) {
                Text(
                    text = request.name,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )

                @Composable
                fun clickableItem(
                    @StringRes titleRes: Int,
                    imageVector: ImageVector,
                    onClick: () -> Unit
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(DefaultBigAlertCorners / 2))
                            .clickable(onClick = onClick)
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                start = DefaultBigAlertButtonPadding / 1.5f,
                                top = DefaultBigAlertButtonPadding,
                                end = DefaultBigAlertButtonPadding,
                                bottom = DefaultBigAlertButtonPadding
                            ),
                            horizontalArrangement = Arrangement.spacedBy(DefaultBigAlertButtonSpacing),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = imageVector,
                                contentDescription = null
                            )
                            Text(
                                text = stringResource(titleRes),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }

                clickableItem(
                    titleRes = R.string.edit,
                    imageVector = Icons.Filled.Edit,
                    onClick = onEditRequest
                )

                clickableItem(
                    titleRes = R.string.delete,
                    imageVector = Icons.Filled.Delete,
                    onClick = onDeleteRequest
                )

                val favouriteTime = request.favouriteTime

                val favouriteTitleRes =
                    if (favouriteTime == null) R.string.add_to_favourite
                    else R.string.remove_from_favourite

                val favouriteIcon =
                    if (favouriteTime == null) Icons.Filled.StarBorder
                    else Icons.Filled.Star

                clickableItem(
                    titleRes = favouriteTitleRes,
                    imageVector = favouriteIcon,
                    onClick = { onFavouriteRequest(favouriteTime) }
                )

                Row {
                    Spacer(modifier = Modifier.weight(1.0f))

                    TextButton(
                        onClick = onDismissRequest,
                        shape = RoundedCornerShape(DefaultBigAlertCorners / 2)
                    ) {
                        Text(text = stringResource(android.R.string.cancel))
                    }
                }
            }
        }
    }
}
