package io.github.ilyadreamix.httptools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.unit.dp
import io.github.ilyadreamix.httptools.navigation.HTTPToolsNavigationHost
import io.github.ilyadreamix.httptools.theme.HTTPToolsTheme

/** Main HTTPTools activity. */
class HTTPToolsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HTTPToolsTheme {
                HTTPToolsNavigationHost()
            }
        }
    }
}

val ScreenPadding = 16.dp
