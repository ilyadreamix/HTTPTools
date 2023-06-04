package io.github.ilyadreamix.httptools.utility

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

infix fun TextUnit.divideDp(divisor: Double) = (this.value / divisor).dp

infix fun TextUnit.divideDp(divisor: Int) = (this.value / divisor).dp
